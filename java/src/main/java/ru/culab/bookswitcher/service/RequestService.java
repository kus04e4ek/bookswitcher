package ru.culab.bookswitcher.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

import ru.culab.bookswitcher.convert.Converter;
import ru.culab.bookswitcher.entity.BookEntity;
import ru.culab.bookswitcher.entity.RequestEntity;
import ru.culab.bookswitcher.exceptions.HttpStatusException;
import ru.culab.bookswitcher.model.RequestDto;
import ru.culab.bookswitcher.repository.BookRepository;
import ru.culab.bookswitcher.repository.RequestRepository;

@Service
@RequiredArgsConstructor
public class RequestService {
    
    private final Converter converter;
    private final RequestRepository requestRepository;
    private final BookRepository bookRepository;

    public List<RequestDto> getRequests() {
        return converter.requestConvertToDto(requestRepository.findAll());
    }

    public RequestDto requestBook(long userId, long bookId) {
        RequestEntity entity = new RequestEntity(null, userId, bookId, RequestEntity.Status.SENT);
        if (!bookRepository.existsById(bookId)) {
            throw new HttpStatusException(HttpStatus.NOT_FOUND, "Book not found");
        }
        if (requestRepository.existsByUserIdAndBookId(userId, bookId)) {
            throw new HttpStatusException(HttpStatus.CONFLICT, "Book already requested");
        }
        return converter.convertToDto(requestRepository.save(entity));
    }

    public RequestDto giveBook(long id, long userId) {
        RequestEntity request = requestRepository.findById(id).orElseThrow(() -> new HttpStatusException(HttpStatus.NOT_FOUND, "Request not found"));
        BookEntity book = bookRepository.findById(request.getBookId()).orElseThrow(() -> new HttpStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        if (request.getStatus() != RequestEntity.Status.SENT) {
            throw new HttpStatusException(HttpStatus.CONFLICT, "Request doesn't have status \"SENT\"");
        }

        if (book.getHolderId() != book.getUserId()) {
            throw new HttpStatusException(HttpStatus.CONFLICT, "Book is not available");
        }
    
        if (book.getHolderId() != userId) {
            throw new HttpStatusException(HttpStatus.UNAUTHORIZED, "Not the holder");
        }

        book.setHolderId(request.getUserId());
        request.setStatus(RequestEntity.Status.GAVE);

        bookRepository.save(book);
        return converter.convertToDto(requestRepository.save(request));
    }

    public RequestDto reject(long id, long userId) {
        RequestEntity request = requestRepository.findById(id).orElseThrow(() -> new HttpStatusException(HttpStatus.NOT_FOUND, "Request not found"));
        BookEntity book = bookRepository.findById(request.getBookId()).orElseThrow(() -> new HttpStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        if (request.getStatus() != RequestEntity.Status.SENT) {
            throw new HttpStatusException(HttpStatus.CONFLICT, "Request doesn't have status \"SENT\"");
        }

        if (book.getHolderId() != book.getUserId()) {
            throw new HttpStatusException(HttpStatus.CONFLICT, "Book is not available");
        }
    
        if (book.getHolderId() != userId) {
            throw new HttpStatusException(HttpStatus.UNAUTHORIZED, "Not the holder");
        }

        request.setStatus(RequestEntity.Status.REJECTED);
        return converter.convertToDto(requestRepository.save(request));
    }

    public RequestDto returnBook(long id, long userId) {
        RequestEntity request = requestRepository.findById(id).orElseThrow(() -> new HttpStatusException(HttpStatus.NOT_FOUND, "Request not found"));
        BookEntity book = bookRepository.findById(request.getBookId()).orElseThrow(() -> new HttpStatusException(HttpStatus.NOT_FOUND, "Book not found"));

        if (request.getStatus() != RequestEntity.Status.GAVE) {
            throw new HttpStatusException(HttpStatus.CONFLICT, "Request doesn't have status \"GAVE\"");
        }

        if (request.getUserId() != userId) {
            throw new HttpStatusException(HttpStatus.UNAUTHORIZED, "Not the user who requested the book");
        }

        if (book.getHolderId() == book.getUserId()) {
            throw new HttpStatusException(HttpStatus.CONFLICT, "Book is already returned");
        }
    
        if (book.getHolderId() != userId) {
            throw new HttpStatusException(HttpStatus.UNAUTHORIZED, "Not the holder");
        }

        book.setHolderId(book.getUserId());
        request.setStatus(RequestEntity.Status.RETURNED);

        bookRepository.save(book);
        return converter.convertToDto(requestRepository.save(request));
    }
}
