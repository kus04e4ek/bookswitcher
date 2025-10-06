package proto

import (
	"bookswitcher/internal/auth"
	"bookswitcher/internal/db"
	"testing"
)

func TestDBUserToAuth(t *testing.T) {
	tests := []struct {
		id       int64
		username string
		city     string
		isAdmin  bool
	}{
		{0, "", "", false},
		{1, "User 0", "Password 0", false},
		{2, "User 1", "Password 1", true},
	}

	for _, test := range tests {
		t.Run("", func(t *testing.T) {
			user := &db.User{Id: test.id, Username: test.username, City: test.city, IsAdmin: test.isAdmin}
			expected := auth.User{Id: test.id, Username: test.username, City: test.city, IsAdmin: test.isAdmin}

			result := DBUserToAuth(user)
			if result != expected {
				t.Errorf("DBUserToAuth(%v) = %v; expected %v", user, result, expected)
			}
		})
	}
}

func TestAuthUserToProto(t *testing.T) {
	tests := []struct {
		id       int64
		username string
		city     string
		isAdmin  bool
	}{
		{0, "", "", false},
		{1, "User 0", "Password 0", false},
		{2, "User 1", "Password 1", true},
	}

	for _, test := range tests {
		t.Run("", func(t *testing.T) {
			user := &auth.User{Id: test.id, Username: test.username, City: test.city, IsAdmin: test.isAdmin}
			expected := &User{Id: test.id, Username: test.username, City: test.city, IsAdmin: test.isAdmin}

			result := AuthUserToProto(user)
			if result.Id != expected.Id || result.Username != expected.Username || result.City != expected.City || result.IsAdmin != expected.IsAdmin {
				t.Errorf("AuthUserToProto(%v) = %v; expected %v", user, result, expected)
			}
		})
	}
}

func TestDBUserToProto(t *testing.T) {
	tests := []struct {
		id       int64
		username string
		city     string
		isAdmin  bool
	}{
		{0, "", "", false},
		{1, "User 0", "Password 0", false},
		{2, "User 1", "Password 1", true},
	}

	for _, test := range tests {
		t.Run("", func(t *testing.T) {
			user := &db.User{Id: test.id, Username: test.username, City: test.city, IsAdmin: test.isAdmin}
			expected := &User{Id: test.id, Username: test.username, City: test.city, IsAdmin: test.isAdmin}

			result := DBUserToProto(user)
			if result.Id != expected.Id || result.Username != expected.Username || result.City != expected.City || result.IsAdmin != expected.IsAdmin {
				t.Errorf("DBUserToProto(%v) = %v; expected %v", user, result, expected)
			}
		})
	}
}

func TestLoginDetailsToDB(t *testing.T) {
	tests := []struct {
		username string
		password string
	}{
		{"", ""},
		{"User 0", "Password 0"},
		{"User 1", "Password 1"},
	}

	for _, test := range tests {
		t.Run("", func(t *testing.T) {
			details := &LoginDetails{Username: test.username, Password: test.password}
			expected := db.LoginDetails{Username: test.username, Password: test.password}

			result := LoginDetailsToDB(details)
			if result != expected {
				t.Errorf("LoginDetailsToDB(%v) = %v; expected %v", details, result, expected)
			}
		})
	}
}

func TestRegisterDetailsToDB(t *testing.T) {
	tests := []struct {
		username string
		password string
		city     string
	}{
		{"", "", ""},
		{"User 0", "Password 0", "City 0"},
		{"User 1", "Password 1", "City 1"},
	}

	for _, test := range tests {
		t.Run("", func(t *testing.T) {
			details := &RegisterDetails{Details: &LoginDetails{Username: test.username, Password: test.password}, City: test.city}
			expected := db.RegisterDetails{LoginDetails: db.LoginDetails{Username: test.username, Password: test.password}, City: test.city}

			result := RegisterDetailsToDB(details)
			if result != expected {
				t.Errorf("RegisterDetailsToDB(%v) = %v; expected %v", details, result, expected)
			}
		})
	}
}
