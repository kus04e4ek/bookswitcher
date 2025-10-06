package ru.culab.bookswitcher.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReviewDtoTests {

    private ReviewDto review;

    @BeforeEach
    public void createReview() {
        review = new ReviewDto(null, null, 0, "Review", null, null);
    }

    @Test
    public void testGetId() {
        Long[] ids = new Long[] { 0l, 100l, null };

        ReviewDto[] reviews = new ReviewDto[] {
            new ReviewDto(ids[0], null, 0, "Review", null, null),
            new ReviewDto(ids[1], null, 0, "Review", null, null),
            new ReviewDto(ids[2], null, 0, "Review", null, null),
        };

        for (int i = 0; i < reviews.length; i++) {
            assertEquals(ids[i], reviews[i].getId());
        }
    }

    @Test
    public void testSetId() {
        Long[] ids = new Long[] { 0l, 100l, null };

        for (int i = 0; i < ids.length; i++) {
            review.setId(ids[i]);
            assertEquals(ids[i], review.getId());
        }
    }

    @Test
    public void testGetUserId() {
        Long[] userIds = new Long[] { 0l, 100l, null };

        ReviewDto[] reviews = new ReviewDto[] {
            new ReviewDto(null, userIds[0], 0, "Review", null, null),
            new ReviewDto(null, userIds[1], 0, "Review", null, null),
            new ReviewDto(null, userIds[2], 0, "Review", null, null),
        };

        for (int i = 0; i < reviews.length; i++) {
            assertEquals(userIds[i], reviews[i].getUserId());
        }
    }

    @Test
    public void testSetUserId() {
        Long[] userIds = new Long[] { 0l, 100l, null };

        for (int i = 0; i < userIds.length; i++) {
            review.setUserId(userIds[i]);
            assertEquals(userIds[i], review.getUserId());
        }
    }

    @Test
    public void testGetBookId() {
        long[] bookIds = new long[] { 0, 100 };

        ReviewDto[] reviews = new ReviewDto[] {
            new ReviewDto(null, null, bookIds[0], "Review", null, null),
            new ReviewDto(null, null, bookIds[1], "Review", null, null),
        };

        for (int i = 0; i < reviews.length; i++) {
            assertEquals(bookIds[i], reviews[i].getBookId());
        }
    }

    @Test
    public void testSetBookId() {
        long[] bookIds = new long[] { 0, 100 };

        for (int i = 0; i < bookIds.length; i++) {
            review.setBookId(bookIds[i]);
            assertEquals(bookIds[i], review.getBookId());
        }
    }

    @Test
    public void testGetReview() {
        String[] reviewTexts = new String[] { "Review 0", "Review 1" };

        ReviewDto[] reviews = new ReviewDto[] {
            new ReviewDto(null, null, 0, reviewTexts[0], null, null),
            new ReviewDto(null, null, 0, reviewTexts[1], null, null),
        };

        for (int i = 0; i < reviews.length; i++) {
            assertEquals(reviewTexts[i], reviews[i].getReview());
        }
    }

    @Test
    public void testSetReview() {
        String[] reviewTexts = new String[] { "Review 0", "Review 1" };

        for (int i = 0; i < reviewTexts.length; i++) {
            review.setReview(reviewTexts[i]);
            assertEquals(reviewTexts[i], review.getReview());
        }
    }

    @Test
    public void testGetUser() {
        UserDto[] users = new UserDto[] {
            null,
            new UserDto(),
        };

        ReviewDto[] reviews = new ReviewDto[] {
            new ReviewDto(null, null, 0, "Review", users[0], null),
            new ReviewDto(null, null, 0, "Review", users[1], null),
        };

        for (int i = 0; i < reviews.length; i++) {
            assertEquals(users[i], reviews[i].getUser());
        }
    }

    @Test
    public void testSetUser() {
        UserDto[] users = new UserDto[] {
            null,
            new UserDto(),
        };

        for (int i = 0; i < users.length; i++) {
            review.setUser(users[i]);
            assertEquals(users[i], review.getUser());
        }
    }

    @Test
    public void testGetBook() {
        BookDto[] books = new BookDto[] {
            null,
            new BookDto(),
        };

        ReviewDto[] reviews = new ReviewDto[] {
            new ReviewDto(null, null, 0, "Review", null, books[0]),
            new ReviewDto(null, null, 0, "Review", null, books[1]),
        };

        for (int i = 0; i < reviews.length; i++) {
            assertEquals(books[i], reviews[i].getBook());
        }
    }

    @Test
    public void testSetBook() {
        BookDto[] books = new BookDto[] {
            null,
            new BookDto(),
        };

        for (int i = 0; i < books.length; i++) {
            review.setBook(books[i]);
            assertEquals(books[i], review.getBook());
        }
    }
}
