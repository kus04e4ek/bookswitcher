package ru.culab.bookswitcher.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import ru.culab.bookswitcher.entity.BookEntity;
import ru.culab.bookswitcher.model.BookDto;
import ru.culab.bookswitcher.repository.BookRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@Testcontainers
public class BookControllerIntegrationTests {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer =
            new PostgreSQLContainer<>("postgres:14.1")
                    .withDatabaseName("testdb")
                    .withUsername("testuser")
                    .withPassword("testpass");

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Autowired
    private TestRestTemplate testRestTemplate;

    private static BookEntity[] books;

    public boolean contains(BookDto[] array, BookEntity find) {
        for (BookDto elem : array) {
            if (elem.getUserId() == find.getUserId() && elem.getHolderId() == find.getHolderId() && elem.getTitle().equals(find.getTitle()) && elem.getAuthor().equals(find.getAuthor())) {
                return true;
            }
        }
        return false;
    }

    @BeforeAll
    public static void initBooks(@Autowired BookRepository bookRepository) {
        books = new BookEntity[] {
            new BookEntity(null, 1l, 1l, "Book 0", "Author 0", null),
            new BookEntity(null, 2l, 2l, "Book 1", "Author 1", null),
            new BookEntity(null, 3l, 3l, "Book 2", "Author 0", null),
            new BookEntity(null, 4l, 4l, "Something else", "Author 1", null)
        };

        for (BookEntity book : books) {
            bookRepository.save(book);
        }
    }

    @Test
    public void testGetAvailableBooks() {
        ResponseEntity<BookDto[]> response = testRestTemplate.getForEntity("/books/available?summary=true", BookDto[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        BookDto[] responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(books.length, responseBody.length);

        for (BookEntity book : books) {
            assertTrue(contains(responseBody, book));
        }
    }

    @Test
    public void testGetAvailableBooksFilteredByTitle() {
        ResponseEntity<BookDto[]> response = testRestTemplate.getForEntity("/books/available?title=Book&summary=true", BookDto[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        BookDto[] responseBody = response.getBody();
        assertNotNull(responseBody);

        int numberOfBooks = 0;
        for (BookEntity book : books) {
            if (book.getTitle().contains("Book")) {
                numberOfBooks++;
                assertTrue(contains(responseBody, book));
            } else {
                assertFalse(contains(responseBody, book));
            }
        }

        assertEquals(numberOfBooks, responseBody.length);
    }

    @Test
    public void testGetAvailableBooksFilteredByAuthor() {
        ResponseEntity<BookDto[]> response = testRestTemplate.getForEntity("/books/available?author=Author 1&summary=true", BookDto[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        BookDto[] responseBody = response.getBody();
        assertNotNull(responseBody);

        int numberOfBooks = 0;
        for (BookEntity book : books) {
            if (book.getAuthor().contains("Author 1")) {
                numberOfBooks++;
                assertTrue(contains(responseBody, book));
            } else {
                assertFalse(contains(responseBody, book));
            }
        }

        assertEquals(numberOfBooks, responseBody.length);
    }

    @Test
    public void testGetAvailableBooksFilteredByTitleAndAuthor() {
        ResponseEntity<BookDto[]> response = testRestTemplate.getForEntity("/books/available?title=Book&author=Author 1&summary=true", BookDto[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());

        BookDto[] responseBody = response.getBody();
        assertNotNull(responseBody);

        int numberOfBooks = 0;
        for (BookEntity book : books) {
            if (book.getTitle().contains("Book") && book.getAuthor().contains("Author 1")) {
                numberOfBooks++;
                assertTrue(contains(responseBody, book));
            } else {
                assertFalse(contains(responseBody, book));
            }
        }

        assertEquals(numberOfBooks, responseBody.length);
    }

    @Test
    public void testGetAvailableBooksWithNoMatches() {
        ResponseEntity<BookDto[]> response = testRestTemplate.getForEntity("/books/available?title=asdasdasdasd&author=asdasdsaasdasdasd&summary=true", BookDto[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        
        BookDto[] responseBody = response.getBody();
        assertNotNull(responseBody);

        assertEquals(0, responseBody.length);
    }
}
