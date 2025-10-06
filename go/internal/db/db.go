package db

import (
	"context"
	"database/sql"
	"errors"

	"github.com/jackc/pgx/v5/pgconn"
	_ "github.com/jackc/pgx/v5/stdlib"
	"github.com/jmoiron/sqlx"
	"google.golang.org/grpc/codes"
	"google.golang.org/grpc/status"
)

type User struct {
	Id       int64  `db:"id"`
	Username string `db:"username"`
	City     string `db:"city"`
	IsAdmin  bool   `db:"is_admin"`
}

type LoginDetails struct {
	Username string `db:"username"`
	Password string `db:"password"`
}

type RegisterDetails struct {
	LoginDetails
	City string `db:"city"`
}

var dbx *sqlx.DB

var ErrUsernameExists error = status.Error(codes.AlreadyExists, "username already exists")
var ErrIncorrectDetails error = status.Error(codes.InvalidArgument, "incorrect login details")
var ErrNotFound error = status.Error(codes.NotFound, "user not found")

func InitDB(ctx context.Context, dataSourceName string) error {
	var err error
	dbx, err = sqlx.ConnectContext(ctx, "pgx", dataSourceName)
	return err
}

func InitTables(ctx context.Context) error {
	_, err := dbx.ExecContext(ctx,
		`
		CREATE TABLE IF NOT EXISTS users (
	    	id SERIAL PRIMARY KEY,
	    	username TEXT UNIQUE,
			password TEXT,
			city TEXT,
			is_admin BOOL
		);
		`)
	return err
}

func SelectUserByLoginDetails(ctx context.Context, details LoginDetails) (*User, error) {
	stmt, err := dbx.PrepareNamedContext(ctx, `SELECT id, username, city, is_admin FROM users WHERE username=:username AND password=:password;`)
	if err != nil {
		return nil, err
	}
	defer stmt.Close()

	var user User
	err = stmt.GetContext(ctx, &user, &details)
	if err != nil {
		if errors.Is(err, sql.ErrNoRows) {
			return nil, ErrIncorrectDetails
		}
		return nil, err
	}
	return &user, nil
}

func SelectUserById(ctx context.Context, id int64) (*User, error) {
	stmt, err := dbx.PreparexContext(ctx, `SELECT id, username, city, is_admin FROM users WHERE id=$1;`)
	if err != nil {
		return nil, err
	}
	defer stmt.Close()

	var user User
	err = stmt.GetContext(ctx, &user, id)
	if err != nil {
		if errors.Is(err, sql.ErrNoRows) {
			return nil, ErrNotFound
		}
		return nil, err
	}
	return &user, nil
}

func InsertUser(ctx context.Context, details RegisterDetails) (*User, error) {
	stmt, err := dbx.PrepareNamedContext(ctx, `INSERT INTO users(username, password, city, is_admin) VALUES (:username, :password, :city, FALSE) RETURNING id, username, city, is_admin;`)
	if err != nil {
		return nil, err
	}
	defer stmt.Close()

	var user User
	err = stmt.QueryRowxContext(ctx, &details).StructScan(&user)
	if err != nil {
		var pgErr *pgconn.PgError
		if errors.As(err, &pgErr) && pgErr.Code == "23505" {
			return nil, ErrUsernameExists
		}
		return nil, err
	}
	return &user, nil
}
