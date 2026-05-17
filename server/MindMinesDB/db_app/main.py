import os
import httpx

from db_app.Database.db import SessionDep, init

from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from db_app.Models.Habit import Habit, HabitDTO
from db_app.Models.User import User, UserDTO
from db_app.Models.Chat import ChatRequest, ChatResponse, ChatHistoryMessage
from datetime import datetime as dt

app = FastAPI(title="MindMinesDB", version="0.1.0")


class AuthRequest(BaseModel):
    email: str
    password: str


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


@app.get("/users/friends")
def get_friends(session: SessionDep):
    users = session.query(User).order_by(User.user_id.asc()).all()
    return [
        {
            "name": user.name,
            "level": user.level
        }
        for user in users
    ]


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

        session.add(res)
        session.commit()
        session.refresh(res)

        payload = res.to_json()
        payload["user_token"] = generate_user_token(res.email, res.password)
        return payload
    except Exception as e:
        raise HTTPException(status_code=400, detail=e)

@app.delete("/users/{user_token}")
def delete_user(user_token: str, session: SessionDep):
    for db_user in session.query(User).all():
        if generate_user_token(db_user.email, db_user.password) == user_token:
            session.delete(db_user)
            session.commit()
            return {"result": "success"}

    raise HTTPException(status_code=404, detail="Item not found")


def generate_user_token(user_email: str, user_password: str):
    return user_email + "_token"

@app.get("/users/{user_email}/{user_password}")
def get_user_token(user_email: str, user_password: str, session: SessionDep):
    user = (
        session.query(User)
        .filter(User.email == user_email, User.password == user_password)
        .first()
    )
    if user is None:
        raise HTTPException(status_code=404, detail="Пользователь не найден")
    return {"user_token": generate_user_token(user_email, user_password)}


@app.post("/users/register")
def register_user(auth_dto: AuthRequest, session: SessionDep):
    if not auth_dto.email:
        raise HTTPException(status_code=400, detail="Email обязателен")

    existing = session.query(User).filter(User.email == auth_dto.email).first()
    if existing is not None:
        raise HTTPException(status_code=409, detail=generate_user_token(existing.email, existing.password))

    user = User(name=auth_dto.email, email=auth_dto.email, password=auth_dto.password)
    session.add(user)
    session.commit()
    session.refresh(user)

    return {"user_token": generate_user_token(user.email, user.password)}


@app.post("/users/login")
def login_user(auth_dto: AuthRequest, session: SessionDep):
    user = (
        session.query(User)
        .filter(User.email == auth_dto.email, User.password == auth_dto.password)
        .first()
    )
    if user is None:
        raise HTTPException(status_code=404, detail="Пользователь не найден")

    return {"user_token": generate_user_token(user.email, user.password)}


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


@app.delete("/habits/{habit_id}")
def delete_task(habit_id: int, session: SessionDep):
    res = session.get(Habit, habit_id)
    if res is None:
        raise HTTPException(status_code=404, detail="Item not found")

    session.delete(res)
    session.commit()

    return {"result": "success"}


OLLAMA_HOST = os.getenv("OLLAMA_HOST", "http://localhost:11434")
OLLAMA_CHAT_URL = f"{OLLAMA_HOST}/api/chat"
MODEL_NAME = "gemma3:1b"

@app.post("/api/chat", response_model=ChatResponse)
async def chat_endpoint(req: ChatRequest, session: SessionDep):
    system_prompt = ("Ты – дружелюбный ассистент по формированию полезных привычек. "
                     "Отвечай кратко, поддерживая и по делу. "
                     "Если вопрос не относится к привычкам, вежливо напоминай о своей специализации.")

    incoming_messages = [msg for msg in req.messages if msg.role in ("user", "assistant")]
    if not incoming_messages:
        raise HTTPException(status_code=400, detail="Нет сообщений для обработки")

    latest_user_message = next((msg for msg in reversed(incoming_messages) if msg.role == "user"), None)
    if latest_user_message is None:
        raise HTTPException(status_code=400, detail="Нет сообщения пользователя")
    user_token = latest_user_message.userToken
    if not user_token:
        raise HTTPException(status_code=400, detail="Не указан userToken")

    user = next(
        (
            db_user
            for db_user in session.query(User).all()
            if generate_user_token(db_user.email, db_user.password) == user_token
        ),
        None
    )
    if user is None:
        raise HTTPException(status_code=404, detail="Пользователь не найден")

    chat_id = latest_user_message.chatId or "default"

    history_rows = (
        session.query(ChatHistoryMessage)
        .filter(
            ChatHistoryMessage.user_id == user.user_id,
            ChatHistoryMessage.chat_id == chat_id
        )
        .order_by(ChatHistoryMessage.created_at.asc())
        .all()
    )

    ollama_messages = [
        {"role": "system", "content": system_prompt}
    ]
    for history_message in history_rows:
        if history_message.role in ("user", "assistant"):
            ollama_messages.append({"role": history_message.role, "content": history_message.content})

    for msg in incoming_messages:
        ollama_messages.append({"role": msg.role, "content": msg.content})

    for msg in incoming_messages:
        session.add(
            ChatHistoryMessage(
                user_id=user.user_id,
                chat_id=chat_id,
                role=msg.role,
                content=msg.content
            )
        )

    session.commit()

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
                        "num_predict": 512
                    }
                }
            )
            resp.raise_for_status()
            data = resp.json()
            answer = data["message"]["content"]

            session.add(
                ChatHistoryMessage(
                    user_id=user.user_id,
                    chat_id=chat_id,
                    role="assistant",
                    content=answer
                )
            )
            session.commit()

            return ChatResponse(reply=answer)
        except httpx.HTTPStatusError as e:
            raise HTTPException(status_code=502, detail=f"Ошибка модели: {e.response.text}")
        except httpx.RequestError as e:
            raise HTTPException(status_code=503, detail=f"Сервис Ollama недоступен: {str(e)}")


if __name__ == "__main__":
    import uvicorn

    uvicorn.run(app, host="0.0.0.0", port=8000)
