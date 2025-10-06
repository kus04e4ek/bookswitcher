package ru.culab.bookswitcher.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReviewEntityTests {

    private ReviewEntity review;

    @BeforeEach
    public void createReview() {
        review = new ReviewEntity(null, 0, 0, "Review");
    }

    @Test
    public void testGetId() {
        Long[] ids = new Long[] { 0l, 100l, null };

        ReviewEntity[] reviews = new ReviewEntity[] {
            new ReviewEntity(ids[0], 0, 0, "Review"),
            new ReviewEntity(ids[1], 0, 0, "Review"),
            new ReviewEntity(ids[2], 0, 0, "Review"),
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
        long[] userIds = new long[] { 0, 100 };

        ReviewEntity[] reviews = new ReviewEntity[] {
            new ReviewEntity(null, userIds[0], 0, "Review"),
            new ReviewEntity(null, userIds[1], 0, "Review"),
        };

        for (int i = 0; i < reviews.length; i++) {
            assertEquals(userIds[i], reviews[i].getUserId());
        }
    }

    @Test
    public void testSetUserId() {
        long[] userIds = new long[] { 0, 100 };

        for (int i = 0; i < userIds.length; i++) {
            review.setUserId(userIds[i]);
            assertEquals(userIds[i], review.getUserId());
        }
    }

    @Test
    public void testGetBookId() {
        long[] bookIds = new long[] { 0, 100 };

        ReviewEntity[] reviews = new ReviewEntity[] {
            new ReviewEntity(null, 0, bookIds[0], "Review"),
            new ReviewEntity(null, 0, bookIds[1], "Review"),
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

        ReviewEntity[] reviews = new ReviewEntity[] {
            new ReviewEntity(null, 0, 0, reviewTexts[0]),
            new ReviewEntity(null, 0, 0, reviewTexts[1]),
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
}
