import os
import uuid
from typing import Optional

import httpx

from db_app.Database.db import SessionDep, init

from fastapi import FastAPI, File, HTTPException, Request, UploadFile
from fastapi.exceptions import RequestValidationError
from db_app.Models.Habit import Habit, HabitDTO
from db_app.Models.User import User, UserDTO
from db_app.Models.Chat import ChatMessage, ChatRequest, ChatResponse
from datetime import datetime as dt

app = FastAPI(title="MindMinesDB", version="0.1.0")


@app.on_event("startup")
def on_startup():
    init()


@app.get("/health")
def health():
    return {"status": "ok"}


# @app.exception_handler(HTTPException)
# async def http_exception_handler(request: Request, exc: HTTPException):
#     return HTTPException(status_code=exc.status_code, detail="HTTP error")
#
#
# @app.exception_handler(RequestValidationError)
# async def validation_exception_handler(request: Request, exc: RequestValidationError):
#     return HTTPException(status_code=422, detail="HTTP Error")


@app.get("/users")
def get_users(session: SessionDep):
    # Admin view
    return [x.to_json() for x in session.query(User).all()]


@app.get("/users/{user_id}")
def get_user(user_id: int, session: SessionDep):
    # Profile view
    res = session.get(User, user_id)
    if res is None:
        raise HTTPException(status_code=404, detail="Item not found")
    return res.to_json()


@app.post("/users")
def post_user(user_dto: UserDTO, session: SessionDep):
    try:
        res = User(name=user_dto.name, email=user_dto.email, password=user_dto.password)
        print(res)

        session.add(res)
        session.commit()
        session.refresh(res)

        return res.to_json()
    except Exception as e:
        raise HTTPException(status_code=400, detail=e)


@app.get("/habits")
def get_tasks(session: SessionDep):
    # Admin view
    return [x.to_json() for x in session.query(Habit).all()]


@app.get("/habits/{habit_id}")
def get_task(habit_id: int, session: SessionDep):
    res = session.get(Habit, habit_id)
    if res is None:
        raise HTTPException(status_code=404, detail="Item not found")
    return res.to_json()


@app.post("/habits")
def post_task(habit_dto: HabitDTO, session: SessionDep):
    try:
        res = Habit(
            user_id=habit_dto.user_id,
            title=habit_dto.title,
            description=habit_dto.description,
            creation_date=dt.now(),
            checking_frequency=habit_dto.checking_frequency,
            priority=habit_dto.priority,
            difficulty=habit_dto.difficulty,
            type=habit_dto.type
        )

        session.add(res)
        session.commit()
        session.refresh(res)

        return res.to_json()
    except Exception:
        raise HTTPException(status_code=400, detail="Bad request")


# @app.put("/habits/{habit_id}")
# def put_task(habit_id: int, habit_dto: HabitDTO, session: SessionDep):
#     habit = session.get(Habit, habit_id)
#     if habit is None:
#         raise HTTPException(status_code=404, detail="Item not found")
#
#     try:
#         habit.title = habit_dto.title
#         habit.description = habit_dto.description
#         habit.type = habit_dto.type
#         habit.status = habit_dto.status
#         habit.priority = habit_dto.priority
#         habit.tag = habit_dto.tag
#         habit.due_at = habit_dto.due_at
#         habit.started_at = habit_dto.started_at
#
#         session.add(habit)
#         session.commit()
#         session.refresh(habit)
#
#         return habit.to_json()
#     except Exception as e:
#         print(e)
#         raise HTTPException(status_code=400, detail="Bad request")


@app.delete("/habits/{habit_id}")
def delete_task(habit_id: int, session: SessionDep):
    res = session.get(Habit, habit_id)
    if res is None:
        raise HTTPException(status_code=404, detail="Item not found")

    session.delete(res)
    session.commit()

    return {"result": "success"}



# Конфигурация Ollama – хост задаётся переменной окружения или по умолчанию localhost
OLLAMA_HOST = os.getenv("OLLAMA_HOST", "http://localhost:11434")
OLLAMA_CHAT_URL = f"{OLLAMA_HOST}/api/chat"
MODEL_NAME = "gemma3:1b"  # можно вынести в env при желании

# ----- Модели для чата -----

# ---------------------------


# Новый эндпоинт чата
@app.post("/api/chat", response_model=ChatResponse)
async def chat_endpoint(req: ChatRequest):
    # Системный промпт, задающий роль ассистента
    system_prompt = "Ты – дружелюбный ассистент по формированию полезных привычек. Отвечай кратко, поддерживающе и по делу. Если вопрос не относится к привычкам, вежливо напоминай о своей специализации."

    # Формируем полный список сообщений для Ollama
    ollama_messages = [
        {"role": "system", "content": system_prompt}
    ]
    # Добавляем историю диалога, переданную клиентом
    for msg in req.messages:
        if msg.role in ("user", "assistant"):
            ollama_messages.append({"role": msg.role, "content": msg.content})

    if not any(m["role"] == "user" for m in ollama_messages):
        raise HTTPException(status_code=400, detail="Нет сообщения пользователя")

    async with httpx.AsyncClient(timeout=30.0) as client:
        try:
            resp = await client.post(
                OLLAMA_CHAT_URL,
                json={
                    "model": MODEL_NAME,
                    "messages": ollama_messages,
                    "stream": False,
                    "options": {
                        "temperature": 0.7,
                        "num_predict": 512  # ограничиваем длину ответа
                    }
                }
            )
            resp.raise_for_status()
            data = resp.json()
            answer = data["message"]["content"]
            return ChatResponse(reply=answer)
        except httpx.HTTPStatusError as e:
            raise HTTPException(status_code=502, detail=f"Ошибка модели: {e.response.text}")
        except httpx.RequestError as e:
            raise HTTPException(status_code=503, detail=f"Сервис Ollama недоступен: {str(e)}")


if __name__ == "__main__":
    import uvicorn

    uvicorn.run(app, host="0.0.0.0", port=8000)
