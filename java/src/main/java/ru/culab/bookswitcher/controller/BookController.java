package ru.culab.bookswitcher.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import ru.culab.bookswitcher.exceptions.HttpStatusException;
import ru.culab.bookswitcher.model.BookDto;
import ru.culab.bookswitcher.model.TokenDto;
import ru.culab.bookswitcher.model.UserDto;
import ru.culab.bookswitcher.service.BookService;
import ru.culab.bookswitcher.service.UserService;

@RestController
@Tag(name = "Books", description = "Books API")
@CrossOrigin("${FRONTEND_URL:http://localhost:3000}")
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final UserService userService;

    @Operation(summary = "Получить все киниги")
    @GetMapping
    public List<BookDto> getBooks(@RequestParam(required = false) String title, @RequestParam(required = false) String author, @RequestHeader("Authorization") String token) {
        UserDto user = userService.getUserByToken(new TokenDto(token));
        if (!user.isAdmin()) {
            throw new HttpStatusException(HttpStatus.UNAUTHORIZED, "Not admin");
        }
        return bookService.getBooks(title, author);
    }

    @Operation(summary = "Получить доступные киниги")
    @GetMapping("/available")
    public List<BookDto> getAvailableBooks(@RequestParam(required = false) String title, @RequestParam(required = false) String author, @RequestParam(required = false) Boolean summary) {
        if (summary != null && summary == true) {
            return bookService.getAvailableBooksSummary(title, author);
        }
        return bookService.getAvailableBooks(title, author);
    }

    @Operation(summary = "Добавить книгу")
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public BookDto createBook(@RequestBody @Valid BookDto book, @RequestHeader("Authorization") String token) {
        UserDto user = userService.getUserByToken(new TokenDto(token));
        book.setUserId(user.getId());
        return bookService.createBook(book);
    }
}
