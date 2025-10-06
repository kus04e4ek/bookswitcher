package ru.culab.bookswitcher.model;

import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ReviewDto {

    private Long id;

    private Long userId;

    private long bookId;

    @NotEmpty
    private String review;

    private UserDto user;

    private BookDto book;
}
