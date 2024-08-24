package ru.practicum.shareit.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler({ObjectExistException.class,})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleObjectExistException(final ObjectExistException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler(ObjectAccessException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleObjectAccessException(final ObjectAccessException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler({ObjectCreateException.class,})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleObjectCreateException(final ObjectCreateException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler({ObjectUpdateException.class,})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleObjectUpdateException(final ObjectUpdateException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler({ObjectNotAvailableException.class,})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleObjectNotAvailableException(final ObjectNotAvailableException ex) {
        return new ErrorResponse(ex.getMessage());
    }

    @ExceptionHandler({InvalidDataException.class,})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidDataException(final InvalidDataException ex) {
        return new ErrorResponse(ex.getMessage());
    }
}
