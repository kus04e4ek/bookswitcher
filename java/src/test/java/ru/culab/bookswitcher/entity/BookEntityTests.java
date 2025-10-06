package ru.culab.bookswitcher.entity;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BookEntityTests {

    private BookEntity book;

    @BeforeEach
    public void createBook() {
        book = new BookEntity(null, 0, 0, "Title", "Author", null);
    }

    @Test
    public void testGetId() {
        Long[] ids = new Long[] { 0l, 100l, null };

        BookEntity[] books = new BookEntity[] {
            new BookEntity(ids[0], 0, 0, "Title", "Author", null),
            new BookEntity(ids[1], 0, 0, "Title", "Author", null),
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
        long[] userIds = new long[] { 0, 100 };

        BookEntity[] books = new BookEntity[] {
            new BookEntity(null, userIds[0], 0, "Title", "Author", null),
            new BookEntity(null, userIds[1], 0, "Title", "Author", null),
        };

        for (int i = 0; i < books.length; i++) {
            assertEquals(userIds[i], books[i].getUserId());
        }
    }

    @Test
    public void testSetUserId() {
        long[] userIds = new long[] { 0, 100 };

        for (int i = 0; i < userIds.length; i++) {
            book.setUserId(userIds[i]);
            assertEquals(userIds[i], book.getUserId());
        }
    }

    @Test
    public void testGetHolderId() {
        long[] holderIds = new long[] { 0, 100 };

        BookEntity[] books = new BookEntity[] {
            new BookEntity(null, 0, holderIds[0], "Title", "Author", null),
            new BookEntity(null, 0, holderIds[1], "Title", "Author", null),
        };

        for (int i = 0; i < books.length; i++) {
            assertEquals(holderIds[i], books[i].getHolderId());
        }
    }

    @Test
    public void testSetHolderId() {
        long[] holderIds = new long[] { 0, 100 };

        for (int i = 0; i < holderIds.length; i++) {
            book.setHolderId(holderIds[i]);
            assertEquals(holderIds[i], book.getHolderId());
        }
    }

    @Test
    public void testGetTitle() {
        String[] titles = new String[] { "Title 0", "Title 1" };

        BookEntity[] books = new BookEntity[] {
            new BookEntity(null, 0, 0, titles[0], "Author", null),
            new BookEntity(null, 0, 0, titles[1], "Author", null),
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

        BookEntity[] books = new BookEntity[] {
            new BookEntity(null, 0, 0, "Title", authors[0], null),
            new BookEntity(null, 0, 0, "Title", authors[1], null),
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
        Set<Long>[] requests = new Set[] {
            null,
            new HashSet<Long>(),
        };

        BookEntity[] books = new BookEntity[] {
            new BookEntity(null, 0, 0, "Title", "Author", requests[0]),
            new BookEntity(null, 0, 0, "Title", "Author", requests[1]),
        };

        for (int i = 0; i < books.length; i++) {
            assertEquals(requests[i], books[i].getRequestUsers());
        }
    }

    @Test
    public void testSetReviews() {
        Set<Long>[] requests = new Set[] {
            null,
            new HashSet<Long>(),
        };

        for (int i = 0; i < requests.length; i++) {
            book.setRequestUsers(requests[i]);
            assertEquals(requests[i], book.getRequestUsers());
        }
    }
}
