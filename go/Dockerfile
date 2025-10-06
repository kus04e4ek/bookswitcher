FROM golang:1.24 AS build

WORKDIR /app

COPY ./go .
RUN rm -rf ./proto
COPY ./proto ./proto

RUN apt update && apt install protobuf-compiler -y

RUN go install google.golang.org/protobuf/cmd/protoc-gen-go@latest
RUN go install google.golang.org/grpc/cmd/protoc-gen-go-grpc@latest
RUN go generate ./...

RUN go build -o bookswitcher ./cmd/server

# Вместо scratch теперь используем alpine
FROM alpine:latest
# Добавляем пакет tzdata, файлы которого нужны для 
# корректной работы функции time.LoadLocation
RUN apk update && apk add gcompat
# Копируем из образа с именем build файл в наш образ
COPY --from=build /app/bookswitcher /bookswitcher

EXPOSE ${AUTH_PORT}

CMD [ "/bookswitcher" ]
