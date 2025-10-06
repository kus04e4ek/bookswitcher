package ru.culab.bookswitcher.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class LoginDetailsDtoTests {

    private LoginDetailsDto loginDetails;

    @BeforeEach
    public void createLoginDetails() {
        loginDetails = new LoginDetailsDto("username", "password");
    }

    @Test
    public void testGetUsername() {
        String[] usernames = new String[] { "User 0", "User 1" };

        LoginDetailsDto[] logins = new LoginDetailsDto[] {
            new LoginDetailsDto(usernames[0], "password"),
            new LoginDetailsDto(usernames[1], "password"),
        };

        for (int i = 0; i < logins.length; i++) {
            assertEquals(usernames[i], logins[i].getUsername());
        }
    }

    @Test
    public void testSetUsername() {
        String[] usernames = new String[] { "User 0", "User 1" };

        for (int i = 0; i < usernames.length; i++) {
            loginDetails.setUsername(usernames[i]);
            assertEquals(usernames[i], loginDetails.getUsername());
        }
    }

    @Test
    public void testGetPassword() {
        String[] passwords = new String[] { "Password 0", "Password 1" };

        LoginDetailsDto[] logins = new LoginDetailsDto[] {
            new LoginDetailsDto("username", passwords[0]),
            new LoginDetailsDto("username", passwords[1]),
        };

        for (int i = 0; i < logins.length; i++) {
            assertEquals(passwords[i], logins[i].getPassword());
        }
    }

    @Test
    public void testSetPassword() {
        String[] passwords = new String[] { "Password 0", "Password 1" };

        for (int i = 0; i < passwords.length; i++) {
            loginDetails.setPassword(passwords[i]);
            assertEquals(passwords[i], loginDetails.getPassword());
        }
    }
}
