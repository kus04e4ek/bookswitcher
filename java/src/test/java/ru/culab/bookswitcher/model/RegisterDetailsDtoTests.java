package ru.culab.bookswitcher.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RegisterDetailsDtoTests {

    private RegisterDetailsDto registerDetails;

    @BeforeEach
    public void createRegisterDetails() {
        registerDetails = new RegisterDetailsDto("username", "password", "city");
    }

    @Test
    public void testGetUsername() {
        String[] usernames = new String[] { "User 0", "User 1" };

        RegisterDetailsDto[] registrations = new RegisterDetailsDto[] {
            new RegisterDetailsDto(usernames[0], "password", "city"),
            new RegisterDetailsDto(usernames[1], "password", "city"),
        };

        for (int i = 0; i < registrations.length; i++) {
            assertEquals(usernames[i], registrations[i].getUsername());
        }
    }

    @Test
    public void testSetUsername() {
        String[] usernames = new String[] { "User 0", "User 1" };

        for (int i = 0; i < usernames.length; i++) {
            registerDetails.setUsername(usernames[i]);
            assertEquals(usernames[i], registerDetails.getUsername());
        }
    }

    @Test
    public void testGetPassword() {
        String[] passwords = new String[] { "Password 0", "Password 1" };

        RegisterDetailsDto[] registrations = new RegisterDetailsDto[] {
            new RegisterDetailsDto("username", passwords[0], "city"),
            new RegisterDetailsDto("username", passwords[1], "city"),
        };

        for (int i = 0; i < registrations.length; i++) {
            assertEquals(passwords[i], registrations[i].getPassword());
        }
    }

    @Test
    public void testSetPassword() {
        String[] passwords = new String[] { "Password 0", "Password 1" };

        for (int i = 0; i < passwords.length; i++) {
            registerDetails.setPassword(passwords[i]);
            assertEquals(passwords[i], registerDetails.getPassword());
        }
    }

    @Test
    public void testGetCity() {
        String[] cities = new String[] { "City 0", "City 1" };

        RegisterDetailsDto[] registrations = new RegisterDetailsDto[] {
            new RegisterDetailsDto("username", "password", cities[0]),
            new RegisterDetailsDto("username", "password", cities[1]),
        };

        for (int i = 0; i < registrations.length; i++) {
            assertEquals(cities[i], registrations[i].getCity());
        }
    }

    @Test
    public void testSetCity() {
        String[] cities = new String[] { "City 0", "City 1" };

        for (int i = 0; i < cities.length; i++) {
            registerDetails.setCity(cities[i]);
            assertEquals(cities[i], registerDetails.getCity());
        }
    }
}
