package proto

import (
	"bookswitcher/internal/auth"
	"context"
	"log"
	"testing"

	grpc "google.golang.org/grpc"
	"google.golang.org/grpc/credentials/insecure"
)

var serverStartError error = nil
var serverStarted bool = false

var testAddress string = ":1234"

func startServerOnce() error {
	if serverStartError != nil {
		return serverStartError
	}

	if serverStarted {
		return nil
	}

	lis, grpcServer, err := userServerInit(testAddress)
	if err != nil {
		serverStartError = err
		return serverStartError
	}

	go func() {
		userServerServe(lis, grpcServer)
	}()

	serverStarted = true
	return nil
}

func TestGetUser(t *testing.T) {
	err := startServerOnce()
	if err != nil {
		t.Errorf("server couldn't be initialized: %v", serverStartError)
		return
	}

	keys := []struct {
		key string
	}{
		{"alpha"},
		{"12345"},
		{"saddas(%)^,mmm./,al,a;l,'paw"},
	}

	users := []struct {
		user auth.User
	}{
		{auth.User{}},
		{auth.User{Id: 120, Username: "User 0", City: "City 0", IsAdmin: false}},
		{auth.User{Id: 1312332, Username: "123321132", City: "asdsadadsasd", IsAdmin: true}},
	}

	// Устанавливаем соединение с gRPC-сервером
	conn, err := grpc.NewClient(testAddress, grpc.WithTransportCredentials(insecure.NewCredentials()))
	if err != nil {
		log.Fatalf("Не удалось подключиться: %v", err)
	}
	defer conn.Close()
	// Создаём клиент
	client := NewUserServiceClient(conn)

	for _, key := range keys {
		for _, user := range users {
			t.Run("", func(t *testing.T) {
				t.Setenv("SECRET_KEY", key.key)
				token, err1 := auth.GetToken(user.user)

				result, err2 := client.GetUser(context.Background(), &Token{Token: token})
				if result.Id != user.user.Id || result.Username != user.user.Username || result.City != user.user.City || result.IsAdmin != user.user.IsAdmin || err1 != nil || err2 != nil {
					t.Errorf("SECRET_KEY=%s\nGetToken(%v) = %s, %v\nclient.GetUser(%s) = %v, %v; expected %v", key.key, user.user, token, err1, token, err2, result, user.user)
				}
			})
		}
	}
}
