FROM python:3.10-slim

WORKDIR /db_app

COPY . /db_app

COPY requirements.txt /db_app
COPY requirements-dev.txt /db_app

RUN pip install --no-cache-dir -r requirements.txt

CMD ["uvicorn", "main:app", "--host", "0.0.0.0", "--port", "8000"]