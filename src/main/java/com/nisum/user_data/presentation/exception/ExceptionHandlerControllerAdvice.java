package com.nisum.user_data.presentation.exception;

import com.nisum.user_data.domain.exception.AuthenticationFailedException;
import com.nisum.user_data.domain.exception.UserCreationException;
import com.nisum.user_data.presentation.dto.error.ErrorDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

/**
 * @author Miguel Angel
 * @since v1.0.0
 */

@Component
@org.springframework.web.bind.annotation.ControllerAdvice
@Log4j2
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler({UserCreationException.class})
    public ResponseEntity<ErrorDTO> handleUserCreationException(UserCreationException exception){
        log.info("Handling UserCreationException");
        ErrorDTO errorResponse = ErrorDTO.builder().message(exception.getMessage()).timestamp(LocalDateTime.now()).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({AuthenticationFailedException.class})
    public ResponseEntity<ErrorDTO> handleAuthenticationFailedException(AuthenticationFailedException exception){
        log.info("Handling AuthenticationFailedException");
        ErrorDTO errorResponse = ErrorDTO.builder().message(exception.getMessage()).timestamp(LocalDateTime.now()).build();
        return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
    }
}
