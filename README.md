# 📦 User & Subscription Management Project

Проект на Spring Boot для управления пользователями и подписками. Сборка осуществляется через Gradle и Docker Compose.

---

## 🔧 Технологии

- Java 17+
- Spring Boot 3
- Gradle
- PostgreSQL
- Docker & Docker Compose

---

## 🚀 Запуск проекта

### 1. Клонируйте репозиторий

```bash
  git clone https://github.com/Christina1407/subscription-service.git
```

### 2. Соберите проект
Linux / macOS:
```bash
  ./gradlew build
```
Windows:
```bash
  gradle.bat build
```
Это сгенерирует .jar файл в папке build/libs


### 3. Запуск с помощью Docker Compose
```bash
  docker-compose up --build
```
После запуска приложение будет доступно по адресу: http://localhost:8080