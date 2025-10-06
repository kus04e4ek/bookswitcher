package ru.culab.bookswitcher.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import ru.culab.bookswitcher.entity.RequestEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestDto {

    private long id;

    private long userId;

    private long bookId;

    private RequestEntity.Status status;

    private UserDto user;

    private BookDto book;
}
