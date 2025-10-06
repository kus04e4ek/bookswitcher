package ru.culab.bookswitcher.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TokenDtoTests {

    private TokenDto token;

    @BeforeEach
    public void createTokenDto() {
        token = new TokenDto("Token");
    }

    @Test
    public void testGetToken() {
        String[] tokenStrings = new String[] { "Token 0", "Token 1" };

        TokenDto[] tokens = new TokenDto[] {
            new TokenDto(tokenStrings[0]),
            new TokenDto(tokenStrings[1]),
        };

        for (int i = 0; i < tokens.length; i++) {
            assertEquals(tokenStrings[i], tokens[i].getToken());
        }
    }

    @Test
    public void testSetToken() {
        String[] tokenStrings = new String[] { "Token 0", "Token 1" };

        for (int i = 0; i < tokenStrings.length; i++) {
            token.setToken(tokenStrings[i]);
            assertEquals(tokenStrings[i], token.getToken());
        }
    }
}
