package com.ey.user_data.domain.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }

    public static void thrown(String message) {
        throw new UserNotFoundException(message);
    }
}
