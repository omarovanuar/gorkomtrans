package com.epam.anuar.gorkomtrans.entity;

public enum GarbageContainerType {
    EURO(1.1), STANDARD(0.75), NON_STANDARD;

    private double containerCapacity;

    GarbageContainerType() {
    }

    GarbageContainerType(double containerCapacity) {
        this.containerCapacity = containerCapacity;
    }

    public Double getContainerCapacity() {
        return containerCapacity;
    }
}
