package ru.culab.bookswitcher.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import lombok.RequiredArgsConstructor;

import ru.culab.bookswitcher.exceptions.HttpStatusException;
import ru.culab.bookswitcher.model.RequestDto;
import ru.culab.bookswitcher.model.TokenDto;
import ru.culab.bookswitcher.model.UserDto;
import ru.culab.bookswitcher.service.RequestService;
import ru.culab.bookswitcher.service.UserService;

@RestController
@Tag(name = "Requests", description = "Requests API")
@CrossOrigin("${FRONTEND_URL:http://localhost:3000}")
@RequestMapping("/requests")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;
    private final UserService userService;

    @Operation(summary = "Получить все запросы")
    @GetMapping
    public List<RequestDto> getRequests(@RequestHeader("Authorization") String token) {
        UserDto user = userService.getUserByToken(new TokenDto(token));
        if (!user.isAdmin()) {
            throw new HttpStatusException(HttpStatus.UNAUTHORIZED, "Not admin");
        }
        return requestService.getRequests();
    }

    @Operation(summary = "Запросить книгу")
    @PostMapping("/book/{bookId}")
    public RequestDto requestBook(@PathVariable long bookId, @RequestHeader("Authorization") String token) {
        UserDto user = userService.getUserByToken(new TokenDto(token));
        return requestService.requestBook(user.getId(), bookId);
    }

    @Operation(summary = "Отдать книгу")
    @PatchMapping("/{id}/give")
    public RequestDto giveBook(@PathVariable long id, @RequestHeader("Authorization") String token) {
        UserDto user = userService.getUserByToken(new TokenDto(token));
        return requestService.giveBook(id, user.getId());
    }

    @Operation(summary = "Отказаться отдавать книгу")
    @PatchMapping("/{id}/reject")
    public RequestDto reject(@PathVariable long id, @RequestHeader("Authorization") String token) {
        UserDto user = userService.getUserByToken(new TokenDto(token));
        return requestService.reject(id, user.getId());
    }

    @Operation(summary = "Вернуть книгу")
    @PatchMapping("/{id}/return")
    public RequestDto returnBook(@PathVariable long id, @RequestHeader("Authorization") String token) {
        UserDto user = userService.getUserByToken(new TokenDto(token));
        return requestService.returnBook(id, user.getId());
    }
}
