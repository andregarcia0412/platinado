package me.andregarcia0412.pipeline.shared.exception;

import me.andregarcia0412.pipeline.shared.exception.dto.ErrorMessage;
import me.andregarcia0412.pipeline.shared.exception.exceptions.BadRequestException;
import me.andregarcia0412.pipeline.shared.exception.exceptions.ConflictException;
import me.andregarcia0412.pipeline.shared.exception.exceptions.NotFoundException;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.naming.ServiceUnavailableException;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<ErrorMessage> notFoundHandler(NotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(exception.getMessage(), HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(ConflictException.class)
    private ResponseEntity<ErrorMessage> conflictHandler(ConflictException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorMessage(exception.getMessage(), HttpStatus.CONFLICT));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(
                        exception.getBindingResult().getFieldErrors().getFirst().getDefaultMessage(),
                        HttpStatus.BAD_REQUEST
                )
        );
    }

    @ExceptionHandler(ServiceUnavailableException.class)
    private ResponseEntity<ErrorMessage> serviceUnavailableHandler(ServiceUnavailableException exception) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(new ErrorMessage(exception.getMessage(), HttpStatus.SERVICE_UNAVAILABLE));
    }

    @ExceptionHandler(BadRequestException.class)
    private ResponseEntity<ErrorMessage> badRequestHandler(BadRequestException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(exception.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<ErrorMessage> genericHandler(Exception exception) {
        logger.error("Unhandled exception", exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
