package me.andregarcia0412.platinado.shared.exception;

import me.andregarcia0412.platinado.shared.exception.dto.ErrorMessage;
import me.andregarcia0412.platinado.shared.exception.exceptions.BadRequestException;
import me.andregarcia0412.platinado.shared.exception.exceptions.ConflictException;
import me.andregarcia0412.platinado.shared.exception.exceptions.NotFoundException;
import me.andregarcia0412.platinado.shared.exception.exceptions.UnauthorizedException;
import me.andregarcia0412.platinado.shared.provider.storage.exception.StorageException;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    private ResponseEntity<ErrorMessage> notFoundHandler(NotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorMessage(exception.getMessage(), HttpStatus.NOT_FOUND));
    }

    @ExceptionHandler(ConflictException.class)
    private ResponseEntity<ErrorMessage> conflictHandler(ConflictException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ErrorMessage(exception.getMessage(), HttpStatus.CONFLICT, exception.getField()));
    }

    @ExceptionHandler(UnauthorizedException.class)
    private ResponseEntity<ErrorMessage> unauthorizedHandler(UnauthorizedException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorMessage(exception.getMessage(), HttpStatus.UNAUTHORIZED));
    }

    @ExceptionHandler(BadCredentialsException.class)
    private ResponseEntity<ErrorMessage> badCredentialsHandler(BadCredentialsException exception) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorMessage("Invalid credentials", HttpStatus.UNAUTHORIZED));
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException exception,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {
        FieldError fieldError = exception.getBindingResult().getFieldErrors().getFirst();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(
                        fieldError.getDefaultMessage(),
                        HttpStatus.BAD_REQUEST,
                        fieldError.getField()
                )
        );
    }

    @ExceptionHandler(BadRequestException.class)
    private ResponseEntity<ErrorMessage> badRequestHandler(BadRequestException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(exception.getMessage(), HttpStatus.BAD_REQUEST));
    }

    @Override
    protected ResponseEntity<Object> handleMaxUploadSizeExceededException(
            MaxUploadSizeExceededException exception,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {
        return ResponseEntity.status(HttpStatus.CONTENT_TOO_LARGE).body(new ErrorMessage("File is too large", HttpStatus.CONTENT_TOO_LARGE));
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(
            MissingServletRequestPartException exception,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorMessage(
                        "Required part '" + exception.getRequestPartName() + "' is missing",
                        HttpStatus.BAD_REQUEST,
                        exception.getRequestPartName()
                )
        );
    }

    @ExceptionHandler(StorageException.class)
    private ResponseEntity<ErrorMessage> storageHandler(StorageException exception) {
        logger.error("Storage failure", exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("Failed to store file", HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<ErrorMessage> genericHandler(Exception exception) {
        logger.error("Unhandled exception", exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ErrorMessage("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
