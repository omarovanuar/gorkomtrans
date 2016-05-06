package com.epam.anuar.gorkomtrans.action;

public class AccessException extends RuntimeException {
    public AccessException() {
    }

    public AccessException(String reason) {
        super(reason);
    }

    public AccessException(String reason, Throwable cause) {
        super(reason, cause);
    }

    public AccessException(Throwable cause) {
        super(cause);
    }
}
