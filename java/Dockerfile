# Используем образ Gradle для сборки приложения. Даём ему имя "build"
FROM gradle:8.12.1-jdk17 AS build

# Копируем исходный код проекта в директорию /app
WORKDIR /app

COPY ./java .
RUN rm -rf ./src/main/proto
COPY ./proto ./src/main/proto

RUN apt update && apt install protobuf-compiler -y

# Собираем проект с помощью gradle 
RUN gradle build --no-daemon -x test

# Используем образ JRE для выполнения нашего кода
FROM eclipse-temurin:17-jre-jammy

# Копируем в него собранную ранее JAR-ку из образа "build"
WORKDIR /app
COPY --from=build /app/build/libs/bookswitcher-0.0.1-SNAPSHOT.jar app.jar

# Указываем команду для запуска приложения
CMD ["java", "-jar", "app.jar"]
