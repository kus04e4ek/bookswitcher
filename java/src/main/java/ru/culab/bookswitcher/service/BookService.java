package ru.culab.bookswitcher.service;

import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

import lombok.RequiredArgsConstructor;

import ru.culab.bookswitcher.convert.Converter;
import ru.culab.bookswitcher.entity.BookEntity;
import ru.culab.bookswitcher.model.BookDto;
import ru.culab.bookswitcher.repository.BookRepository;

@Service
@RequiredArgsConstructor
public class BookService {
    
    private final Converter converter;
    private final BookRepository bookRepository;

    public BookDto createBook(@Valid @NotNull BookDto book) {
        book.setHolderId(book.getUserId());
        BookEntity entity = Converter.convertToEntity(book);
        entity.setId(null);
        return converter.convertToDto(bookRepository.save(entity));
    }

    public List<BookDto> getBooks(String title, String author) {
        return converter.bookConvertToDto(bookRepository.findByFilters(title, author));
    }

    public List<BookDto> getAvailableBooks(String title, String author) {
        return converter.bookConvertToDto(bookRepository.findAvailableByFilters(title, author));
    }

    public List<BookDto> getAvailableBooksSummary(String title, String author) {
        return converter.bookConvertToDtoSummary(bookRepository.findAvailableByFilters(title, author));
    }
}
