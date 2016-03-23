package com.epam.anuar.gorkomtrans.entity;

public enum GarbageContainerType {
    EURO(1.1), STANDART(0.75), NON_STANDART;

    private double containerCapacity;

    GarbageContainerType() {
    }

    GarbageContainerType(double containerCapacity) {
        this.containerCapacity = containerCapacity;
    }

    public double getContainerCapacity() {
        return containerCapacity;
    }

    public void setContainerCapacity(double containerCapacity) {
        this.containerCapacity = containerCapacity;
    }
}
