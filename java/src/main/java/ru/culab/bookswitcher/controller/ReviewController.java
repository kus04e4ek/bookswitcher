package ru.culab.bookswitcher.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import ru.culab.bookswitcher.model.ReviewDto;
import ru.culab.bookswitcher.model.TokenDto;
import ru.culab.bookswitcher.model.UserDto;
import ru.culab.bookswitcher.service.ReviewService;
import ru.culab.bookswitcher.service.UserService;

@RestController
@Tag(name = "Reviews", description = "Reviews API")
@CrossOrigin("${FRONTEND_URL:http://localhost:3000}")
@RequestMapping("/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final UserService userService;
    
    @Operation(summary = "Добавить отзыв")
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ReviewDto createReview(@RequestBody @Valid ReviewDto review, @RequestHeader("Authorization") String token) {
        UserDto user = userService.getUserByToken(new TokenDto(token));
        review.setUserId(user.getId());
        return reviewService.createReview(review);
    }
}
