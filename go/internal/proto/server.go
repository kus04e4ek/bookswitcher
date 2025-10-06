package proto

import (
	"bookswitcher/internal/auth"
	"bookswitcher/internal/db"
	"context"
	"errors"
	"log"
	"net"
	"time"

	grpc "google.golang.org/grpc"
	codes "google.golang.org/grpc/codes"
	status "google.golang.org/grpc/status"
)

type userServer struct {
	UnimplementedUserServiceServer
}

func (s *userServer) GetToken(ctx context.Context, details *LoginDetails) (*Token, error) {
	user, err := db.SelectUserByLoginDetails(ctx, LoginDetailsToDB(details))
	if err != nil {
		if errors.Is(err, db.ErrIncorrectDetails) {
			return nil, err
		}
		return nil, status.Error(codes.Internal, "unknown error")
	}

	token, err := auth.GetToken(DBUserToAuth(user))
	if err != nil {
		return nil, status.Error(codes.Internal, "unknown error")
	}
	return &Token{Token: token}, nil
}

func (s *userServer) GetUser(ctx context.Context, token *Token) (*User, error) {
	claims, err := auth.GetUser(token.Token)
	if err != nil {
		return nil, status.Error(codes.Unauthenticated, "invalid token")
	}
	return AuthUserToProto(claims), nil
}

func (s *userServer) GetUserById(ctx context.Context, id *UserId) (*User, error) {
	user, err := db.SelectUserById(ctx, id.Id)
	if err != nil {
		if errors.Is(err, db.ErrIncorrectDetails) {
			return nil, err
		}
		return nil, status.Error(codes.Internal, "unknown error")
	}
	return DBUserToProto(user), nil
}

func (s *userServer) CreateUser(ctx context.Context, details *RegisterDetails) (*Token, error) {
	user, err := db.InsertUser(ctx, RegisterDetailsToDB(details))
	if err != nil {
		if errors.Is(err, db.ErrIncorrectDetails) {
			return nil, err
		}
		return nil, status.Error(codes.Internal, "unknown error")
	}

	token, err := auth.GetToken(DBUserToAuth(user))
	if err != nil {
		return nil, status.Error(codes.Internal, "unknown error")
	}
	return &Token{Token: token}, nil
}

// Простой unary interceptor для логирования
func loggingInterceptor(
	ctx context.Context,
	req interface{},
	info *grpc.UnaryServerInfo,
	handler grpc.UnaryHandler,
) (interface{}, error) {
	log.Printf("-> Unary call: %s", info.FullMethod)
	start := time.Now()
	// Выполняем реальный обработчик
	resp, err := handler(ctx, req)
	dur := time.Since(start)
	if err != nil {
		log.Printf("<- Completed with error: %v (method %s, %v)", err, info.FullMethod, dur)
	} else {
		log.Printf("<- Completed: method %s, duration=%v", info.FullMethod, dur)
	}
	return resp, err
}

func userServerInit(address string) (*net.Listener, *grpc.Server, error) {
	lis, err := net.Listen("tcp", address)
	if err != nil {
		return nil, nil, err
	}

	server := userServer{}

	grpcServer := grpc.NewServer(grpc.UnaryInterceptor(loggingInterceptor))
	RegisterUserServiceServer(grpcServer, &server)

	return &lis, grpcServer, nil
}

func userServerServe(lis *net.Listener, grpcServer *grpc.Server) error {
	if err := grpcServer.Serve(*lis); err != nil {
		return err
	}
	return nil
}

func UserServerListen(address string) error {
	lis, grpcServer, err := userServerInit(address)
	if err != nil {
		return err
	}

	return userServerServe(lis, grpcServer)
}
