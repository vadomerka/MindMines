from datetime import date

from pydantic import BaseModel, constr
from sqlalchemy import Column, String, Integer, ForeignKey, DateTime, Float
from sqlalchemy.ext.declarative import declarative_base
from db_app.core.base import Base


class HabitDTO(BaseModel):
    user_id: int
    title: constr(min_length=1, max_length=64)
    description: constr(max_length=512)
    checking_frequency: float
    priority: int
    difficulty: int
    type: str


class Habit(Base):
    __tablename__ = 'habits'

    habit_id = Column(Integer, primary_key=True)
    user_id = Column(Integer, ForeignKey('users.user_id', ondelete='CASCADE'), nullable=False)
    title = Column(String(64), default="New habit!", nullable=False)
    description = Column(String(512), default="", nullable=True)
    creation_date = Column(DateTime, default=date.min, nullable=False)
    checking_frequency = Column(Float, default=1, nullable=False)
    priority = Column(Integer, default=1, nullable=False)
    difficulty = Column(Integer, default=1, nullable=False)
    type = Column(String(64), default="", nullable=False)

    last_checked = Column(DateTime, default=date.min, nullable=False)
    penalty_number = Column(Integer, default=0, nullable=False)
    streak_number = Column(Integer, default=0, nullable=False)

    def to_json(self):
        return {
            "id": self.user_id,
        }
