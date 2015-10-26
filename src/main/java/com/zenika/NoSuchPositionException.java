package com.zenika;

public class NoSuchPositionException extends RuntimeException {
    public NoSuchPositionException(String message) {
        super(message);
    }
}
