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
public class LoginDetailsDto {
    
    @NotEmpty
    String username;

    @NotEmpty
    String password;
}
