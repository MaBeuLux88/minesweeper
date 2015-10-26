package com.zenika;

public class TooMuchMinesException extends RuntimeException {
    public TooMuchMinesException(String message) {
        super(message);
    }
}
