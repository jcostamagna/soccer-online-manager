package com.jcostamagna.socceronlinemanager.adapters.controller;

import com.jcostamagna.socceronlinemanager.adapters.controller.exceptions.BadRequestException;
import com.jcostamagna.socceronlinemanager.adapters.domain.response.ResponseError;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    private final Logger logger = LogManager.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<ResponseError> handleRestClientException(BadRequestException exception) {
        logger.error(exception);

        ResponseError response = buildResponseError("Bad Request", exception.getMessage(), HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ResponseError> handleAccessDeniedException(BadRequestException exception) {
        logger.error(exception);

        ResponseError response = buildResponseError("Unauthorized", exception.getMessage(), HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(BadCredentialsException.class)
    protected ResponseEntity<ResponseError> handleAccessDeniedException(BadCredentialsException exception) {
        logger.error(exception);

        ResponseError response = buildResponseError("Unauthorized", exception.getMessage(), HttpStatus.BAD_REQUEST.value());

        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity handleException(Exception exception) {
        logger.error(exception);

        ResponseError response = buildResponseError("Internal Server Error", exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());

        return ResponseEntity.internalServerError().body(response);
    }

    private ResponseError buildResponseError(String error, String message, int status) {
        return ResponseError.builder()
                    .error(error)
                    .message(message)
                    .status(status)
                    .build();
    }
}
