package ru.culab.bookswitcher.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import lombok.RequiredArgsConstructor;

import ru.culab.bookswitcher.convert.Converter;
import ru.culab.bookswitcher.entity.ReviewEntity;
import ru.culab.bookswitcher.exceptions.HttpStatusException;
import ru.culab.bookswitcher.model.ReviewDto;
import ru.culab.bookswitcher.repository.BookRepository;
import ru.culab.bookswitcher.repository.ReviewRepository;

@Service
@RequiredArgsConstructor
public class ReviewService {
    
    private final Converter converter;
    
    private final ReviewRepository reviewRepository;
    private final BookRepository bookRepository;

    public ReviewDto createReview(@Valid @NotNull ReviewDto review) {
        ReviewEntity entity = Converter.convertToEntity(review);
        entity.setId(null);

        if (!bookRepository.existsById(entity.getBookId())) {
            throw new HttpStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }

        if (reviewRepository.existsByUserIdAndBookId(entity.getUserId(), entity.getBookId())) {
            throw new HttpStatusException(HttpStatus.CONFLICT, "Review already posted");
        }

        return converter.convertToDto(reviewRepository.save(entity));
    }
}
