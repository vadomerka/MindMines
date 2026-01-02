from datetime import date

from pydantic import BaseModel, constr
from sqlalchemy import Column, String, Integer, ForeignKey, DateTime, Float, BigInteger
from sqlalchemy.ext.declarative import declarative_base
from db_app.core.base import Base


class UserDTO(BaseModel):
    name: constr(min_length=1, max_length=64)
    email: constr(min_length=1, max_length=64)
    password: constr(max_length=128)


class User(Base):
    __tablename__ = 'users'

    user_id = Column(Integer, primary_key=True)
    name = Column(String(64), default="", nullable=False)
    email = Column(String(64), nullable=False, unique=True)
    password = Column(String(128), nullable=False)
    experience = Column(BigInteger, default=0, nullable=False)
    level = Column(Integer, default=0, nullable=False)
    expeditions = Column(Integer, default=0, nullable=False)
    prestige = Column(Integer, default=0, nullable=False)

    def to_json(self):
        return {
            "id": self.user_id,
            "name": self.name,
            "email": self.email,
        }
