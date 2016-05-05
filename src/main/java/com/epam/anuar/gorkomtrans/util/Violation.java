package com.epam.anuar.gorkomtrans.util;

public class Violation {
    private String violation;
    private Integer fieldNumber;

    public Violation(String violation) {
        this.violation = violation;
    }

    public Violation(String violation, Integer fieldNumber) {
        this.violation = violation;
        this.fieldNumber = fieldNumber;
    }

    public String getViolation() {
        return violation;
    }

    public Integer getFieldNumber() {
        return fieldNumber;
    }
}
