package com.epam.anuar.gorkomtrans.entity;

public enum Status {
    SUBMITTED("���������"), NEW("�����"), AGREED("���������"), DENIED("��������");
    String ru;

    Status(String ru) {
        this.ru = ru;
    }

    public String getRu() {
        return ru;
    }
}