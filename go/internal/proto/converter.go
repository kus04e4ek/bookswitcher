package proto

import (
	"bookswitcher/internal/auth"
	"bookswitcher/internal/db"
)

func DBUserToAuth(user *db.User) auth.User {
	return auth.User{Id: user.Id, Username: user.Username, City: user.City, IsAdmin: user.IsAdmin}
}

func AuthUserToProto(user *auth.User) *User {
	return &User{Id: user.Id, Username: user.Username, City: user.City, IsAdmin: user.IsAdmin}
}

func DBUserToProto(user *db.User) *User {
	return &User{Id: user.Id, Username: user.Username, City: user.City, IsAdmin: user.IsAdmin}
}

func LoginDetailsToDB(details *LoginDetails) db.LoginDetails {
	return db.LoginDetails{Username: details.Username, Password: details.Password}
}

func RegisterDetailsToDB(details *RegisterDetails) db.RegisterDetails {
	return db.RegisterDetails{LoginDetails: LoginDetailsToDB(details.Details), City: details.City}
}
