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
public class UserDto {

    private long id;

    @NotEmpty
    private String username;

    @NotEmpty
    private String city;

    private boolean isAdmin;

    private List<BookDto> books;

    private List<RequestDto> requestsGot;
    private List<RequestDto> requestsSent;

    private List<ReviewDto> reviews;
}
