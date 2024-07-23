package com.ey.user_data.domain.exception;

/**
 * @author Miguel Angel
 * @since v1.0.0
 */

public class AuthenticationFailedException extends RuntimeException {

    public AuthenticationFailedException(String message) {
        super(message);
    }

    public AuthenticationFailedException(String message, Throwable cause) {
        super(message, cause);
    }

}
