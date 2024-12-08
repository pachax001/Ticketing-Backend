package com.mathisha.ticketing.Exceptions;

public class NotEnoughTicketsException extends RuntimeException {
    public NotEnoughTicketsException(String message) {
        super(message);
    }
}
