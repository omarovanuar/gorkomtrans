package com.epam.anuar.gorkomtrans.entity;

public enum Status {
    SUBMITTED("ОТПРАВЛЕН"), NEW("НОВЫЙ"), AGREED("УТВЕРЖДЕН"), DENIED("ОТКЛОНЕН");
    String ru;

    Status(String ru) {
        this.ru = ru;
    }

    public String getRu() {
        return ru;
    }
}