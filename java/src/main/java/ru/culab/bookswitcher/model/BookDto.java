package ru.culab.bookswitcher.model;

import java.util.List;

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
public class BookDto {

    private Long id;

    private Long userId;

    private Long holderId;

    @NotEmpty
    private String title;

    @NotEmpty
    private String author;

    private List<ReviewDto> reviews;

    private UserDto user;
}
