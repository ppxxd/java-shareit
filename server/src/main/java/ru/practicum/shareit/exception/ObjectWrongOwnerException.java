package ru.practicum.shareit.exception;

public class ObjectWrongOwnerException extends RuntimeException {

    public ObjectWrongOwnerException(String msg) {
        super(msg);
    }
}
