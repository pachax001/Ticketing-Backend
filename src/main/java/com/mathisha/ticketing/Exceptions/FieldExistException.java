package com.mathisha.ticketing.Exceptions;

public class FieldExistException extends RuntimeException{
    public FieldExistException(String message) {
        super(message);
    }
}
