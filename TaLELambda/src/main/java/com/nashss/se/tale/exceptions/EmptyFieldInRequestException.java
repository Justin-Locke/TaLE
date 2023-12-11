package com.nashss.se.tale.exceptions;

public class EmptyFieldInRequestException extends RuntimeException {

    private static final long serialVersionUID = -832881481732338685L;

    /**
     * Exception without message or cause.
     */
    public EmptyFieldInRequestException() {
        super();
    }

    /**
     * Exception with message.
     * @param message to print.
     */
    public EmptyFieldInRequestException(String message) {
        super(message);
    }

    /**
     * Exception with cause.
     * @param cause that was thrown.
     */
    public EmptyFieldInRequestException(Throwable cause) {
        super(cause);
    }

    /**
     * Exception with both message and cause.
     * @param message to print.
     * @param cause of thrown Exception
     */
    public EmptyFieldInRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
