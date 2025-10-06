package ru.culab.bookswitcher.exceptions;

import lombok.Getter;

import org.springframework.http.HttpStatus;

@Getter
public class HttpStatusException extends RuntimeException {

    // Храним HTTP-статус, который будем отдавать пользователям
    private final HttpStatus httpStatus;

    public HttpStatusException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
}