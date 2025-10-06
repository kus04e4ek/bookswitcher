package ru.culab.bookswitcher.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import ru.culab.bookswitcher.model.LoginDetailsDto;
import ru.culab.bookswitcher.model.RegisterDetailsDto;
import ru.culab.bookswitcher.model.TokenDto;
import ru.culab.bookswitcher.model.UserDto;
import ru.culab.bookswitcher.service.UserService;

@RestController
@Tag(name = "Users", description = "Users API")
@CrossOrigin("${FRONTEND_URL:http://localhost:3000}")
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    
    @Operation(summary = "Получить пользователя по id")
    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable long id) {
        return userService.getUserById(id);
    }
    
    @Operation(summary = "Получить пользователя по токену")
    @GetMapping("/token")
    public UserDto getUserByToken(@RequestHeader("Authorization") String token) {
        return userService.getUserByToken(new TokenDto(token));
    }
    
    @Operation(summary = "Получить токен")
    @PostMapping("/token")
    public TokenDto getToken(@RequestBody @Valid LoginDetailsDto details) {
        return userService.getToken(details);
    }
    
    @Operation(summary = "Создать пользователя")
    @PostMapping
    public TokenDto createUser(@RequestBody @Valid RegisterDetailsDto details) {
        return userService.createUser(details);
    }
}
