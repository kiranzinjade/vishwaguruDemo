package com.techvg.vks.exceptions;

public class AlreadyExitsException extends RuntimeException{
    private static final long serialVersionUID = 7957651750325704624L;

    public AlreadyExitsException() {
        super();
    }

    public AlreadyExitsException(final String message) {
        super(message);
    }
}
