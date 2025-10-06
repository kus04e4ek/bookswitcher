package ru.culab.bookswitcher.service;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;

import jakarta.annotation.PreDestroy;

import ru.culab.bookswitcher.convert.Converter;
import ru.culab.bookswitcher.exceptions.HttpStatusException;
import ru.culab.bookswitcher.model.LoginDetailsDto;
import ru.culab.bookswitcher.model.RegisterDetailsDto;
import ru.culab.bookswitcher.model.TokenDto;
import ru.culab.bookswitcher.model.UserDto;
import ru.culab.bookswitcher.proto.LoginDetails;
import ru.culab.bookswitcher.proto.RegisterDetails;
import ru.culab.bookswitcher.proto.Token;
import ru.culab.bookswitcher.proto.User;
import ru.culab.bookswitcher.proto.UserId;
import ru.culab.bookswitcher.proto.UserServiceGrpc;
import ru.culab.bookswitcher.proto.UserServiceGrpc.UserServiceBlockingStub;

@Service
public class UserService {
    
    private final Converter converter;

    ManagedChannel channel;
    UserServiceBlockingStub stub;

    UserService(Converter converter) {
        this.converter = converter;

        String host = System.getenv("AUTH_HOST");
        if (host == null) {
            host = "localhost";
        }

        String port = System.getenv("AUTH_PORT");
        if (port == null) {
            port = "4000";
        }

        channel = ManagedChannelBuilder.forAddress(host, Integer.parseInt(port))
                .usePlaintext()
                .build();

        stub = UserServiceGrpc.newBlockingStub(channel);
    }
    
    @PreDestroy
    public void preDestroy() {
        channel.shutdown();
    }

    public TokenDto getToken(LoginDetailsDto details) {
        LoginDetails proto = Converter.convertToProto(details);
        Token token;
        try {
            token = stub.getToken(proto);
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.INVALID_ARGUMENT) {
                throw new HttpStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
            }
            throw e;
        }
        return Converter.convertToDto(token);
    }

    public UserDto getUserSummary(long id) {
        UserId proto = UserId.newBuilder().setId(id).build();
        User user;
        try {
            user = stub.getUserById(proto);
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.NOT_FOUND) {
                throw new HttpStatusException(HttpStatus.NOT_FOUND, e.getMessage());
            }
            throw e;
        }
        return converter.convertToDtoSummary(user);
    }

    public UserDto getUserById(long id) {
        UserId proto = UserId.newBuilder().setId(id).build();
        User user;
        try {
            user = stub.getUserById(proto);
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.NOT_FOUND) {
                throw new HttpStatusException(HttpStatus.NOT_FOUND, e.getMessage());
            }
            throw e;
        }
        return converter.convertToDtoDetailed(user);
    }

    public UserDto getUserByToken(TokenDto token) {
        Token proto = Converter.convertToProto(token);
        User user;
        try {
            user = stub.getUser(proto);
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.UNAUTHENTICATED) {
                throw new HttpStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
            }
            throw e;
        }
        return converter.convertToDtoLoggedIn(user);
    }

    public TokenDto createUser(RegisterDetailsDto details) {
        RegisterDetails proto = Converter.convertToProto(details);
        Token token;
        try {
            token = stub.createUser(proto);
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode() == Status.Code.ALREADY_EXISTS) {
                throw new HttpStatusException(HttpStatus.CONFLICT, e.getMessage());
            }
            throw e;
        }
        return Converter.convertToDto(token);
    }
}
