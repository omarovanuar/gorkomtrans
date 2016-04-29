package com.epam.anuar.gorkomtrans.entity;

public enum Status {
    SUBMITTED("нропюбкем"), NEW("мнбши"), AGREED("србепфдем"), DENIED("нрйкнмем");
    String ru;

    Status(String ru) {
        this.ru = ru;
    }

    public String getRu() {
        return ru;
    }
}