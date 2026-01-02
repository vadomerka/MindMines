from datetime import date

from pydantic import BaseModel, constr
from sqlalchemy import Column, String, Integer, ForeignKey, DateTime, Float
from sqlalchemy.ext.declarative import declarative_base
from db_app.core.base import Base


class MiniTaskDTO(BaseModel):
    title: constr(min_length=1, max_length=64)
    description: constr(max_length=256)
    habit_id: Integer
    checking_time: DateTime
    weight: Float


class MiniTask(Base):
    __tablename__ = 'mini_tasks'

    mini_task_id = Column(Integer, primary_key=True)
    habit_id = Column(Integer, ForeignKey('habits.habit_id', ondelete='CASCADE'), nullable=False)
    title = Column(String(64), default="", nullable=False)
    description = Column(String(256), default="", nullable=True)
    checking_time = Column(DateTime, default=date.min, nullable=False)
    last_checked = Column(DateTime, default=date.min, nullable=False)
    weight = Column(Float, default=1, nullable=False)

    def to_json(self):
        return {
            "id": self.mini_task_id,
        }
