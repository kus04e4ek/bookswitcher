package auth

import (
	"os"
	"time"

	"github.com/golang-jwt/jwt/v4"
)

type User struct {
	Id       int64  `json:"id"`
	Username string `json:"username"`
	City     string `json:"city"`
	IsAdmin  bool   `json:"is_admin"`
}

type claims struct {
	jwt.RegisteredClaims
	User
}

func getKey(*jwt.Token) (any, error) {
	return []byte(os.Getenv("SECRET_KEY")), nil
}

func GetToken(user User) (string, error) {
	claims := claims{User: user, RegisteredClaims: jwt.RegisteredClaims{ExpiresAt: &jwt.NumericDate{Time: time.Now().Add(24 * time.Hour)}}}
	token := jwt.NewWithClaims(jwt.SigningMethodHS256, claims)

	key, err := getKey(token)
	if err != nil {
		return "", err
	}

	return token.SignedString(key)
}

func GetUser(tokenString string) (*User, error) {
	token, err := jwt.ParseWithClaims(tokenString, &claims{}, getKey)
	if err != nil {
		return nil, err
	}
	return &token.Claims.(*claims).User, nil
}
