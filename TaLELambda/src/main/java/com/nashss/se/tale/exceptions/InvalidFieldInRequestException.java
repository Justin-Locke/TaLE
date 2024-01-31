package com.nashss.se.tale.exceptions;

public class InvalidFieldInRequestException extends RuntimeException {


    /**
     * Exception without message or cause.
     */
    public InvalidFieldInRequestException() {
        super();
    }

    /**
     * Exception with message.
     * @param message to print.
     */
    public InvalidFieldInRequestException(String message) {
        super(message);
    }

    /**
     * Exception with cause.
     * @param cause that was thrown.
     */
    public InvalidFieldInRequestException(Throwable cause) {
        super(cause);
    }

    /**
     * Exception with both message and cause.
     * @param message to print.
     * @param cause of thrown Exception
     */
    public InvalidFieldInRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
