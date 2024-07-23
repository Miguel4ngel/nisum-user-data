package com.ey.user_data.domain.exception;

/**
 * @author Miguel Angel
 * @since v1.0.0
 */

public class UserCreationException extends RuntimeException {

    public UserCreationException(String message) {
        super(message);
    }

    public UserCreationException(String message, Throwable cause) {
        super(message, cause);
    }

    public static void thrown(String message) {
        throw new UserCreationException(message);
    }

}
