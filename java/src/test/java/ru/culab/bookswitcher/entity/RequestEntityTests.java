package ru.culab.bookswitcher.entity;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RequestEntityTests {

    private RequestEntity request;

    @BeforeEach
    public void createRequestDto() {
        request = new RequestEntity(null, 0, 0, RequestEntity.Status.SENT);
    }

    @Test
    public void testGetId() {
        Long[] ids = new Long[] { 0l, 100l, null };

        RequestEntity[] requests = new RequestEntity[] {
            new RequestEntity(ids[0], 0, 0, RequestEntity.Status.SENT),
            new RequestEntity(ids[1], 0, 0, RequestEntity.Status.SENT),
        };

        for (int i = 0; i < requests.length; i++) {
            assertEquals(ids[i], requests[i].getId());
        }
    }

    @Test
    public void testSetId() {
        Long[] ids = new Long[] { 0l, 100l, null };

        for (int i = 0; i < ids.length; i++) {
            request.setId(ids[i]);
            assertEquals(ids[i], request.getId());
        }
    }

    @Test
    public void testGetUserId() {
        long[] userIds = new long[] { 0, 100 };

        RequestEntity[] requests = new RequestEntity[] {
            new RequestEntity(null, userIds[0], 0, RequestEntity.Status.SENT),
            new RequestEntity(null, userIds[1], 0, RequestEntity.Status.SENT),
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

        RequestEntity[] requests = new RequestEntity[] {
            new RequestEntity(null, 0, bookIds[0], RequestEntity.Status.SENT),
            new RequestEntity(null, 0, bookIds[1], RequestEntity.Status.SENT),
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

        RequestEntity[] requests = new RequestEntity[statuses.length];
        for (int i = 0; i < statuses.length; i++) {
            requests[i] = new RequestEntity(null, 0, 0, statuses[i]);
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
}
