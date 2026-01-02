FROM python:3.10-slim

WORKDIR /app

COPY . .

COPY requirements.txt /app
COPY requirements-dev.txt /app

RUN pip install -r requirements.txt

CMD ["uvicorn", "db_app.main:app", "--host", "0.0.0.0", "--port", "8000"]