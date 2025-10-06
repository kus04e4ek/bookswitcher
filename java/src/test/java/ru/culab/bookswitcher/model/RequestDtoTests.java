package ru.culab.bookswitcher.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import ru.culab.bookswitcher.entity.RequestEntity;

public class RequestDtoTests {

    private RequestDto request;

    @BeforeEach
    public void createRequestDto() {
        request = new RequestDto(0, 0, 0, RequestEntity.Status.SENT, null, null);
    }

    @Test
    public void testGetId() {
        long[] ids = new long[] { 0, 100 };

        RequestDto[] requests = new RequestDto[] {
            new RequestDto(ids[0], 0, 0, RequestEntity.Status.SENT, null, null),
            new RequestDto(ids[1], 0, 0, RequestEntity.Status.SENT, null, null),
        };

        for (int i = 0; i < requests.length; i++) {
            assertEquals(ids[i], requests[i].getId());
        }
    }

    @Test
    public void testSetId() {
        long[] ids = new long[] { 0, 100 };

        for (int i = 0; i < ids.length; i++) {
            request.setId(ids[i]);
            assertEquals(ids[i], request.getId());
        }
    }

    @Test
    public void testGetUserId() {
        long[] userIds = new long[] { 0, 100 };

        RequestDto[] requests = new RequestDto[] {
            new RequestDto(0, userIds[0], 0, RequestEntity.Status.SENT, null, null),
            new RequestDto(0, userIds[1], 0, RequestEntity.Status.SENT, null, null),
        };

        for (int i = 0; i < requests.length; i++) {
            assertEquals(userIds[i], requests[i].getUserId());
        }
    }

    @Test
    public void testSetUserId() {
        long[] userIds = new long[] { 0, 100 };

        for (int i = 0; i < userIds.length; i++) {
            request.setUserId(userIds[i]);
            assertEquals(userIds[i], request.getUserId());
        }
    }

    @Test
    public void testGetBookId() {
        long[] bookIds = new long[] { 0, 100 };

        RequestDto[] requests = new RequestDto[] {
            new RequestDto(0, 0, bookIds[0], RequestEntity.Status.SENT, null, null),
            new RequestDto(0, 0, bookIds[1], RequestEntity.Status.SENT, null, null),
        };

        for (int i = 0; i < requests.length; i++) {
            assertEquals(bookIds[i], requests[i].getBookId());
        }
    }

    @Test
    public void testSetBookId() {
        long[] bookIds = new long[] { 0, 100 };

        for (int i = 0; i < bookIds.length; i++) {
            request.setBookId(bookIds[i]);
            assertEquals(bookIds[i], request.getBookId());
        }
    }

    @Test
    public void testGetStatus() {
        RequestEntity.Status[] statuses = RequestEntity.Status.values();

        RequestDto[] requests = new RequestDto[statuses.length];
        for (int i = 0; i < statuses.length; i++) {
            requests[i] = new RequestDto(0, 0, 0, statuses[i], null, null);
        }

        for (int i = 0; i < requests.length; i++) {
            assertEquals(statuses[i], requests[i].getStatus());
        }
    }

    @Test
    public void testSetStatus() {
        RequestEntity.Status[] statuses = RequestEntity.Status.values();

        for (int i = 0; i < statuses.length; i++) {
            request.setStatus(statuses[i]);
            assertEquals(statuses[i], request.getStatus());
        }
    }

    @Test
    public void testGetUser() {
        UserDto[] users = new UserDto[] {
            null,
            new UserDto(),
        };

        RequestDto[] requests = new RequestDto[] {
            new RequestDto(0, 0, 0, RequestEntity.Status.SENT, users[0], null),
            new RequestDto(0, 0, 0, RequestEntity.Status.SENT, users[1], null),
        };

        for (int i = 0; i < requests.length; i++) {
            assertEquals(users[i], requests[i].getUser());
        }
    }

    @Test
    public void testSetUser() {
        UserDto[] users = new UserDto[] {
            null,
            new UserDto(),
        };

        for (int i = 0; i < users.length; i++) {
            request.setUser(users[i]);
            assertEquals(users[i], request.getUser());
        }
    }

    @Test
    public void testGetBook() {
        BookDto[] books = new BookDto[] {
            null,
            new BookDto(),
        };

        RequestDto[] requests = new RequestDto[] {
            new RequestDto(0, 0, 0, RequestEntity.Status.SENT, null, books[0]),
            new RequestDto(0, 0, 0, RequestEntity.Status.SENT, null, books[1]),
        };

        for (int i = 0; i < requests.length; i++) {
            assertEquals(books[i], requests[i].getBook());
        }
    }

    @Test
    public void testSetBook() {
        BookDto[] books = new BookDto[] {
            null,
            new BookDto(),
        };

        for (int i = 0; i < books.length; i++) {
            request.setBook(books[i]);
            assertEquals(books[i], request.getBook());
        }
    }
}
