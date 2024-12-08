package com.mathisha.ticketing.Exceptions;

public class NoTicketsFoundException extends RuntimeException {
    public NoTicketsFoundException(String message) {
        super(message);
    }
}
