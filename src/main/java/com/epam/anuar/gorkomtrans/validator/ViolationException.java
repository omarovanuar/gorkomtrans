package com.epam.anuar.gorkomtrans.validator;

public class ViolationException extends Exception {
    public ViolationException() {
        super();
    }

    public ViolationException(String message) {
        super(message);
    }
}
