import os
import uuid
from typing import Optional

from fastapi import FastAPI, File, HTTPException, Request, UploadFile
from fastapi.exceptions import RequestValidationError
from fastapi.responses import JSONResponse
from sqlmodel import select

from .Database.db import SessionDep, init
from .Models.Task import Task, TaskDTO
from .Models.User import User, UserDTO

app = FastAPI(title="MindMinesDB", version="0.1.0")


@app.on_event("startup")
def on_startup():
    init()


@app.get("/health")
def health():
    return {"status": "ok"}


@app.exception_handler(HTTPException)
async def http_exception_handler(request: Request, exc: HTTPException):
    return HTTPException(status_code=exc.status_code, detail="HTTP error")


@app.exception_handler(RequestValidationError)
async def validation_exception_handler(request: Request, exc: RequestValidationError):
    return HTTPException(status_code=422, detail="HTTP Error")


@app.get("/users")
def get_users(session: SessionDep):
    # Admin view
    return [x.to_json() for x in session.exec(select(User)).all()]


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

        return res.to_json()
    except Exception:
        raise HTTPException(status_code=400, detail="Bad request")


@app.get("/tasks")
def get_tasks(session: SessionDep):
    # Admin view
    return [x.to_json() for x in session.exec(select(Task)).all()]


@app.get("/tasks/{task_id}")
def get_task(task_id: int, session: SessionDep):
    res = session.get(Task, task_id)
    if res is None:
        raise HTTPException(status_code=404, detail="Item not found")
    return res.to_json()


@app.post("/tasks")
def post_task(task_dto: TaskDTO, session: SessionDep):
    try:
        res = Task(
            title=task_dto.title,
            description=task_dto.description,
            type=task_dto.type,
            status=task_dto.status,
            priority=task_dto.priority,
            tag=task_dto.tag,
            due_at=task_dto.due_at,
            started_at=task_dto.started_at,
        )

        session.add(res)
        session.commit()
        session.refresh(res)

        return res.to_json()
    except Exception:
        raise HTTPException(status_code=400, detail="Bad request")


@app.put("/tasks/{task_id}")
def put_task(task_id: int, task_dto: TaskDTO, session: SessionDep):
    task = session.get(Task, task_id)
    if task is None:
        raise HTTPException(status_code=404, detail="Item not found")

    try:
        task.title = task_dto.title
        task.description = task_dto.description
        task.type = task_dto.type
        task.status = task_dto.status
        task.priority = task_dto.priority
        task.tag = task_dto.tag
        task.due_at = task_dto.due_at
        task.started_at = task_dto.started_at

        session.add(task)
        session.commit()
        session.refresh(task)

        return task.to_json()
    except Exception as e:
        print(e)
        raise HTTPException(status_code=400, detail="Bad request")


@app.delete("/tasks/{task_id}")
def delete_task(task_id: int, session: SessionDep):
    res = session.get(Task, task_id)
    if res is None:
        raise HTTPException(status_code=404, detail="Item not found")

    session.delete(res)
    session.commit()

    return {"result": "success"}


if __name__ == "__main__":
    import uvicorn

    uvicorn.run(app, host="0.0.0.0", port=8000)
