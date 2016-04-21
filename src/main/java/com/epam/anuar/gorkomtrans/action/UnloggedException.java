package com.epam.anuar.gorkomtrans.action;

public class UnloggedException extends RuntimeException {
    public UnloggedException() {
    }

    public UnloggedException(String reason) {
        super(reason);
    }

    public UnloggedException(String reason, Throwable cause) {
        super(reason, cause);
    }

    public UnloggedException(Throwable cause) {
        super(cause);
    }
}
