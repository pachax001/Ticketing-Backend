package com.mathisha.ticketing.Exceptions;

public class EventNotFoundException extends  RuntimeException{
    public EventNotFoundException(String msg) {
        super(msg);
    }
}
