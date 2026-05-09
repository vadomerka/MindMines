import os
from typing import Annotated

from fastapi import Depends
from sqlalchemy import create_engine
from sqlalchemy.orm import sessionmaker
from db_app.core.base import Base

database_url = os.getenv("DATABASE_URL", "sqlite:///./test.db")
engine = create_engine(database_url)
Session = sessionmaker(engine)


def init():
    Base.metadata.create_all(engine)


def get_session():
    with Session() as session:
        yield session


SessionDep = Annotated[Session, Depends(get_session)]
