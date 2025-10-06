package ru.culab.bookswitcher.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BookDtoTests {

    private BookDto book;

    @BeforeEach
    public void createBook() {
        book = new BookDto(null, null, null, "Title", "Author", null, null);
    }

    @Test
    public void testGetId() {
        Long[] ids = new Long[] { 0l, 100l, null };

        BookDto[] books = new BookDto[] {
            new BookDto(ids[0], null, null, "Title", "Author", null, null),
            new BookDto(ids[1], null, null, "Title", "Author", null, null),
            new BookDto(ids[2], null, null, "Title", "Author", null, null),
        };

        for (int i = 0; i < books.length; i++) {
            assertEquals(ids[i], books[i].getId());
        }
    }

    @Test
    public void testSetId() {
        Long[] ids = new Long[] { 0l, 100l, null };

        for (int i = 0; i < ids.length; i++) {
            book.setId(ids[i]);
            assertEquals(ids[i], book.getId());
        }
    }

    @Test
    public void testGetUserId() {
        Long[] userIds = new Long[] { 0l, 100l, null };

        BookDto[] books = new BookDto[] {
            new BookDto(null, userIds[0], null, "Title", "Author", null, null),
            new BookDto(null, userIds[1], null, "Title", "Author", null, null),
            new BookDto(null, userIds[2], null, "Title", "Author", null, null),
        };

        for (int i = 0; i < books.length; i++) {
            assertEquals(userIds[i], books[i].getUserId());
        }
    }

    @Test
    public void testSetUserId() {
        Long[] userIds = new Long[] { 0l, 100l, null };

        for (int i = 0; i < userIds.length; i++) {
            book.setUserId(userIds[i]);
            assertEquals(userIds[i], book.getUserId());
        }
    }

    @Test
    public void testGetHolderId() {
        Long[] holderIds = new Long[] { 0l, 100l, null };

        BookDto[] books = new BookDto[] {
            new BookDto(null, null, holderIds[0], "Title", "Author", null, null),
            new BookDto(null, null, holderIds[1], "Title", "Author", null, null),
            new BookDto(null, null, holderIds[2], "Title", "Author", null, null),
        };

        for (int i = 0; i < books.length; i++) {
            assertEquals(holderIds[i], books[i].getHolderId());
        }
    }

    @Test
    public void testSetHolderId() {
        Long[] holderIds = new Long[] { 0l, 100l, null };

        for (int i = 0; i < holderIds.length; i++) {
            book.setHolderId(holderIds[i]);
            assertEquals(holderIds[i], book.getHolderId());
        }
    }

    @Test
    public void testGetTitle() {
        String[] titles = new String[] { "Title 0", "Title 1" };

        BookDto[] books = new BookDto[] {
            new BookDto(null, null, null, titles[0], "Author", null, null),
            new BookDto(null, null, null, titles[1], "Author", null, null),
        };

        for (int i = 0; i < books.length; i++) {
            assertEquals(titles[i], books[i].getTitle());
        }
    }

    @Test
    public void testSetTitle() {
        String[] titles = new String[] { "Title 0", "Title 1" };

        for (int i = 0; i < titles.length; i++) {
            book.setTitle(titles[i]);
            assertEquals(titles[i], book.getTitle());
        }
    }

    @Test
    public void testGetAuthor() {
        String[] authors = new String[] { "Author 0", "Author 1" };

        BookDto[] books = new BookDto[] {
            new BookDto(null, null, null, "Title", authors[0], null, null),
            new BookDto(null, null, null, "Title", authors[1], null, null),
        };

        for (int i = 0; i < books.length; i++) {
            assertEquals(authors[i], books[i].getAuthor());
        }
    }

    @Test
    public void testSetAuthor() {
        String[] authors = new String[] { "Author 0", "Author 1" };

        for (int i = 0; i < authors.length; i++) {
            book.setAuthor(authors[i]);
            assertEquals(authors[i], book.getAuthor());
        }
    }

    @Test
    public void testGetReviews() {
        List<ReviewDto>[] reviews = new List[] {
            null,
            new ArrayList<ReviewDto>(),
        };

        BookDto[] books = new BookDto[] {
            new BookDto(null, null, null, "Title", "Author", reviews[0], null),
            new BookDto(null, null, null, "Title", "Author", reviews[1], null),
        };

        for (int i = 0; i < books.length; i++) {
            assertEquals(reviews[i], books[i].getReviews());
        }
    }

    @Test
    public void testSetReviews() {
        List<ReviewDto>[] reviews = new List[] {
            null,
            new ArrayList<ReviewDto>(),
        };

        for (int i = 0; i < reviews.length; i++) {
            book.setReviews(reviews[i]);
            assertEquals(reviews[i], book.getReviews());
        }
    }

    @Test
    public void testGetUser() {
        UserDto[] users = new UserDto[] {
            null,
            new UserDto(),
        };

        BookDto[] books = new BookDto[] {
            new BookDto(null, null, null, "Title", "Author", null, users[0]),
            new BookDto(null, null, null, "Title", "Author", null, users[1]),
        };

        for (int i = 0; i < books.length; i++) {
            assertEquals(users[i], books[i].getUser());
        }
    }

    @Test
    public void testSetUser() {
        UserDto[] users = new UserDto[] {
            null,
            new UserDto(),
        };

        for (int i = 0; i < users.length; i++) {
            book.setUser(users[i]);
            assertEquals(users[i], book.getUser());
        }
    }
}
