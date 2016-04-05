package com.epam.anuar.gorkomtrans.entity;

import java.util.Map;

public class GarbageTechSpecification extends BaseEntity{
    private String address;
    private Map<GarbageContainerType, Integer> garbageContainerParameters;

    public GarbageTechSpecification() {
    }

    public GarbageTechSpecification(Integer id, String address, Map<GarbageContainerType, Integer> garbageContainerParameters) {
        super(id);
        this.address = address;
        this.garbageContainerParameters = garbageContainerParameters;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void add(GarbageContainerType type, int number) {
        garbageContainerParameters.put(type, number);
    }

    public void delete(GarbageContainerType type, int number) {
        garbageContainerParameters.remove(type);
    }

    public double getTotalGarbageCapacity() {
        double amount = 0;
        for (Map.Entry<GarbageContainerType, Integer> entry : garbageContainerParameters.entrySet()) {
            amount += entry.getKey().getContainerCapacity() * entry.getValue();
        }
        return amount;
    }
}
