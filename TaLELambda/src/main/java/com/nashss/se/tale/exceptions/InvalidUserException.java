package com.nashss.se.tale.exceptions;
public class InvalidUserException extends RuntimeException {
    /**
     * Exception without message or cause.
     */
    public InvalidUserException() {
        super();
    }

    /**
     * Exception with message.
     * @param message to print.
     */
    public InvalidUserException(String message) {
        super(message);
    }

    /**
     * Exception with cause.
     * @param cause that was thrown.
     */
    public InvalidUserException(Throwable cause) {
        super(cause);
    }

    /**
     * Exception with both message and cause.
     * @param message to print.
     * @param cause of thrown Exception
     */
    public InvalidUserException(String message, Throwable cause) {
        super(message, cause);
    }
}
