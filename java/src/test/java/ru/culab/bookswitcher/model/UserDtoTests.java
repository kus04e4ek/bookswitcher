package ru.culab.bookswitcher.model;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserDtoTests {

    private UserDto user;

    @BeforeEach
    public void createUser() {
        user = new UserDto(0l, "username", "city", false, null, null, null, null);
    }

    @Test
    public void testGetId() {
        long[] ids = new long[] { 0, 100 };

        UserDto[] users = new UserDto[] {
            new UserDto(ids[0], "username", "city", false, null, null, null, null),
            new UserDto(ids[1], "username", "city", false, null, null, null, null),
        };

        for (int i = 0; i < users.length; i++) {
            assertEquals(ids[i], users[i].getId());
        }
    }

    @Test
    public void testSetId() {
        long[] ids = new long[] { 0, 100 };

        for (int i = 0; i < ids.length; i++) {
            user.setId(ids[i]);
            assertEquals(ids[i], user.getId());
        }
    }

    @Test
    public void testGetUsername() {
        String[] usernames = new String[] { "User 0", "User 1" };

        UserDto[] users = new UserDto[] {
            new UserDto(0l, usernames[0], "city", false, null, null, null, null),
            new UserDto(0l, usernames[1], "city", false, null, null, null, null),
        };

        for (int i = 0; i < users.length; i++) {
            assertEquals(usernames[i], users[i].getUsername());
        }
    }

    @Test
    public void testSetUsername() {
        String[] usernames = new String[] { "User 0", "User 1" };

        for (int i = 0; i < usernames.length; i++) {
            user.setUsername(usernames[i]);
            assertEquals(usernames[i], user.getUsername());
        }
    }

    @Test
    public void testGetCity() {
        String[] cities = new String[] { "City 0", "City 1" };

        UserDto[] users = new UserDto[] {
            new UserDto(0l, "username", cities[0], false, null, null, null, null),
            new UserDto(0l, "username", cities[1], false, null, null, null, null),
        };

        for (int i = 0; i < users.length; i++) {
            assertEquals(cities[i], users[i].getCity());
        }
    }

    @Test
    public void testSetCity() {
        String[] cities = new String[] { "City 0", "City 1" };

        for (int i = 0; i < cities.length; i++) {
            user.setCity(cities[i]);
            assertEquals(cities[i], user.getCity());
        }
    }

    @Test
    public void testIsAdmin() {
        boolean[] adminStatuses = new boolean[] { false, true };

        UserDto[] users = new UserDto[] {
            new UserDto(0l, "username", "city", adminStatuses[0], null, null, null, null),
            new UserDto(0l, "username", "city", adminStatuses[1], null, null, null, null),
        };

        for (int i = 0; i < users.length; i++) {
            assertEquals(adminStatuses[i], users[i].isAdmin());
        }
    }

    @Test
    public void testSetAdmin() {
        boolean[] adminStatuses = new boolean[] { false, true };

        for (int i = 0; i < adminStatuses.length; i++) {
            user.setAdmin(adminStatuses[i]);
            assertEquals(adminStatuses[i], user.isAdmin());
        }
    }

    @Test
    public void testGetBooks() {
        List<BookDto>[] booksLists = new List[] {
            null,
            new ArrayList<BookDto>(),
        };

        UserDto[] users = new UserDto[] {
            new UserDto(0l, "username", "city", false, booksLists[0], null, null, null),
            new UserDto(0l, "username", "city", false, booksLists[1], null, null, null),
        };

        for (int i = 0; i < users.length; i++) {
            assertEquals(booksLists[i], users[i].getBooks());
        }
    }

    @Test
    public void testSetBooks() {
        List<BookDto>[] booksLists = new List[] {
            null,
            new ArrayList<BookDto>(),
        };

        for (int i = 0; i < booksLists.length; i++) {
            user.setBooks(booksLists[i]);
            assertEquals(booksLists[i], user.getBooks());
        }
    }

    @Test
    public void testGetRequestsGot() {
        List<RequestDto>[] requestsLists = new List[] {
            null,
            new ArrayList<RequestDto>(),
        };

        UserDto[] users = new UserDto[] {
            new UserDto(0l, "username", "city", false, null, requestsLists[0], null, null),
            new UserDto(0l, "username", "city", false, null, requestsLists[1], null, null),
        };

        for (int i = 0; i < users.length; i++) {
            assertEquals(requestsLists[i], users[i].getRequestsGot());
        }
    }

    @Test
    public void testSetRequestsGot() {
        List<RequestDto>[] requestsLists = new List[] {
            null,
            new ArrayList<RequestDto>(),
        };

        for (int i = 0; i < requestsLists.length; i++) {
            user.setRequestsGot(requestsLists[i]);
            assertEquals(requestsLists[i], user.getRequestsGot());
        }
    }

    @Test
    public void testGetRequestsSent() {
        List<RequestDto>[] requestsLists = new List[] {
            null,
            new ArrayList<RequestDto>(),
        };

        UserDto[] users = new UserDto[] {
            new UserDto(0l, "username", "city", false, null, null, requestsLists[0], null),
            new UserDto(0l, "username", "city", false, null, null, requestsLists[1], null),
        };

        for (int i = 0; i < users.length; i++) {
            assertEquals(requestsLists[i], users[i].getRequestsSent());
        }
    }

    @Test
    public void testSetRequestsSent() {
        List<RequestDto>[] requestsLists = new List[] {
            null,
            new ArrayList<RequestDto>(),
        };

        for (int i = 0; i < requestsLists.length; i++) {
            user.setRequestsSent(requestsLists[i]);
            assertEquals(requestsLists[i], user.getRequestsSent());
        }
    }

    @Test
    public void testGetReviews() {
        List<ReviewDto>[] reviewsLists = new List[] {
            null,
            new ArrayList<ReviewDto>(),
        };

        UserDto[] users = new UserDto[] {
            new UserDto(0l, "username", "city", false, null, null, null, reviewsLists[0]),
            new UserDto(0l, "username", "city", false, null, null, null, reviewsLists[1]),
        };

        for (int i = 0; i < users.length; i++) {
            assertEquals(reviewsLists[i], users[i].getReviews());
        }
    }

    @Test
    public void testSetReviews() {
        List<ReviewDto>[] reviewsLists = new List[] {
            null,
            new ArrayList<ReviewDto>(),
        };

        for (int i = 0; i < reviewsLists.length; i++) {
            user.setReviews(reviewsLists[i]);
            assertEquals(reviewsLists[i], user.getReviews());
        }
    }
}
