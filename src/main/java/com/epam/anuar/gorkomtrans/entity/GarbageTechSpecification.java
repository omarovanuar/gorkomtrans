package com.epam.anuar.gorkomtrans.entity;

public class GarbageTechSpecification extends BaseEntity{
    private String address;
    private GarbageContainerType type;
    private int garbageContainerNumber;

    public GarbageTechSpecification() {
    }

    public GarbageTechSpecification(Integer id, String address, GarbageContainerType type, int garbageContainerNumber) {
        super(id);
        this.address = address;
        this.type = type;
        this.garbageContainerNumber = garbageContainerNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public GarbageContainerType getType() {
        return type;
    }

    public void setType(GarbageContainerType type) {
        this.type = type;
    }

    public int getGarbageContainerNumber() {
        return garbageContainerNumber;
    }

    public void setGarbageContainerNumber(int garbageContainerNumber) {
        this.garbageContainerNumber = garbageContainerNumber;
    }

    public double getTotalGarbageCapacity() {
        return type.getContainerCapacity()*garbageContainerNumber;
    }
}
