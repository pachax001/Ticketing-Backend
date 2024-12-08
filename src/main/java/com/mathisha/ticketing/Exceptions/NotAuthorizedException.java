package com.mathisha.ticketing.Exceptions;

public class NotAuthorizedException extends RuntimeException{
    public NotAuthorizedException(String msg) {
        super(msg);
    }
}
