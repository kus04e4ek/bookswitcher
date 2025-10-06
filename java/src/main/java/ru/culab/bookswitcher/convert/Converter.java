package ru.culab.bookswitcher.convert;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

import ru.culab.bookswitcher.entity.BookEntity;
import ru.culab.bookswitcher.entity.RequestEntity;
import ru.culab.bookswitcher.entity.ReviewEntity;
import ru.culab.bookswitcher.model.BookDto;
import ru.culab.bookswitcher.model.LoginDetailsDto;
import ru.culab.bookswitcher.model.RegisterDetailsDto;
import ru.culab.bookswitcher.model.RequestDto;
import ru.culab.bookswitcher.model.ReviewDto;
import ru.culab.bookswitcher.model.TokenDto;
import ru.culab.bookswitcher.model.UserDto;
import ru.culab.bookswitcher.proto.Token;
import ru.culab.bookswitcher.proto.User;
import ru.culab.bookswitcher.repository.BookRepository;
import ru.culab.bookswitcher.repository.RequestRepository;
import ru.culab.bookswitcher.repository.ReviewRepository;
import ru.culab.bookswitcher.service.UserService;
import ru.culab.bookswitcher.proto.LoginDetails;
import ru.culab.bookswitcher.proto.RegisterDetails;

@Component
@RequiredArgsConstructor
public class Converter {

    private final ReviewRepository reviewRepository;
    private final RequestRepository requestRepository;
    private final BookRepository bookRepository;

    private UserService userService;


    @Autowired
    public void setUserService(@Lazy UserService userService) {
        this.userService = userService;
    }


    public static BookEntity convertToEntity(BookDto dto) {
        return new BookEntity(dto.getId(), dto.getUserId(), dto.getHolderId(), dto.getTitle(), dto.getAuthor(), new HashSet<>());
    }

    public BookDto convertToDto(BookEntity entity) {
        return new BookDto(entity.getId(), entity.getUserId(), entity.getHolderId(), entity.getTitle(), entity.getAuthor(), reviewConvertToDtoWithUser(reviewRepository.findByBookId(entity.getId())), userService.getUserSummary(entity.getUserId()));
    }

    public BookDto convertToDtoSummary(BookEntity entity) {
        return new BookDto(entity.getId(), entity.getUserId(), entity.getHolderId(), entity.getTitle(), entity.getAuthor(), null, null);
    }

    public BookDto convertToDtoWithUser(BookEntity entity) {
        return new BookDto(entity.getId(), entity.getUserId(), entity.getHolderId(), entity.getTitle(), entity.getAuthor(), null, userService.getUserSummary(entity.getUserId()));
    }

    public List<BookDto> bookConvertToDto(List<BookEntity> entities) {
        if (entities == null) {
            return null;
        }
        List<BookDto> dtos = new ArrayList<>();
        for (BookEntity entity : entities) {
            dtos.add(convertToDto(entity));
        }
        return dtos;
    }

    public List<BookDto> bookConvertToDtoSummary(List<BookEntity> entities) {
        if (entities == null) {
            return null;
        }
        List<BookDto> dtos = new ArrayList<>();
        for (BookEntity entity : entities) {
            dtos.add(convertToDtoSummary(entity));
        }
        return dtos;
    }

    public List<BookDto> bookConvertToDtoWithUser(List<BookEntity> entities) {
        if (entities == null) {
            return null;
        }
        List<BookDto> dtos = new ArrayList<>();
        for (BookEntity entity : entities) {
            dtos.add(convertToDtoWithUser(entity));
        }
        return dtos;
    }


    public ReviewDto convertToDto(ReviewEntity entity) {
        return new ReviewDto(entity.getId(), entity.getUserId(), entity.getBookId(), entity.getReview(), userService.getUserSummary(entity.getUserId()), convertToDtoWithUser(bookRepository.findById(entity.getBookId()).get()));
    }

    public ReviewDto convertToDtoWithUser(ReviewEntity entity) {
        return new ReviewDto(entity.getId(), entity.getUserId(), entity.getBookId(), entity.getReview(), userService.getUserSummary(entity.getUserId()), null);
    }

    public static ReviewEntity convertToEntity(ReviewDto dto) {
        return new ReviewEntity(dto.getId(), dto.getUserId(), dto.getBookId(), dto.getReview());
    }

    public List<ReviewDto> reviewConvertToDto(List<ReviewEntity> entities) {
        if (entities == null) {
            return null;
        }
        List<ReviewDto> dtos = new ArrayList<>();
        for (ReviewEntity entity : entities) {
            dtos.add(convertToDto(entity));
        }
        return dtos;
    }

    public List<ReviewDto> reviewConvertToDtoWithUser(List<ReviewEntity> entities) {
        if (entities == null) {
            return null;
        }
        List<ReviewDto> dtos = new ArrayList<>();
        for (ReviewEntity entity : entities) {
            dtos.add(convertToDtoWithUser(entity));
        }
        return dtos;
    }


    public RequestDto convertToDto(RequestEntity entity) {
        return new RequestDto(entity.getId(), entity.getUserId(), entity.getBookId(), entity.getStatus(), userService.getUserSummary(entity.getUserId()), convertToDto(bookRepository.findById(entity.getBookId()).get()));
    }
    
    public List<RequestDto> requestConvertToDto(List<RequestEntity> entities) {
        if (entities == null) {
            return null;
        }
        List<RequestDto> dtos = new ArrayList<>();
        for (RequestEntity entity : entities) {
            dtos.add(convertToDto(entity));
        }
        return dtos;
    }


    public static LoginDetails convertToProto(LoginDetailsDto dto) {
        return LoginDetails.newBuilder()
                    .setUsername(dto.getUsername())
                    .setPassword(dto.getPassword())
                    .build();
    }


    public static RegisterDetails convertToProto(RegisterDetailsDto dto) {
        return RegisterDetails.newBuilder()
                    .setDetails(
                        LoginDetails.newBuilder()
                            .setUsername(dto.getUsername())
                            .setPassword(dto.getPassword())
                            .build()
                    )
                    .setCity(dto.getCity())
                    .build();
    }


    public static TokenDto convertToDto(Token proto) {
        return new TokenDto(proto.getToken());
    }

    public static Token convertToProto(TokenDto dto) {
        return Token.newBuilder()
                    .setToken(dto.getToken())
                    .build();
    }


    public UserDto convertToDtoSummary(User proto) {
        return convertToDto(proto, null, null, null, null);
    }

    public UserDto convertToDtoDetailed(User proto) {
        return convertToDto(proto, bookRepository.findByUserId(proto.getId()), null, null, reviewRepository.findByUserId(proto.getId()));
    }

    public UserDto convertToDtoLoggedIn(User proto) {
        List<BookEntity> books = bookRepository.findByUserId(proto.getId());

        List<RequestEntity> requestEntities = new ArrayList<>();
        for (BookEntity book : books) {
            requestEntities.addAll(requestRepository.findByBookId(book.getId()));
        }

        return convertToDto(proto, books, requestEntities, requestRepository.findByUserId(proto.getId()), reviewRepository.findByUserId(proto.getId()));
    }

    private UserDto convertToDto(User proto, List<BookEntity> books, List<RequestEntity> requestsGot, List<RequestEntity> requestsSent, List<ReviewEntity> reviews) {
        return new UserDto(proto.getId(), proto.getUsername(), proto.getCity(), proto.getIsAdmin(), bookConvertToDto(books), requestConvertToDto(requestsGot), requestConvertToDto(requestsSent), reviewConvertToDto(reviews));
    }
}
