package com.epam.anuar.gorkomtrans.entity;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Map;

public class GarbageTechSpecification extends BaseEntity {
    private String address;
    private Map<String, List<String>> garbageContainerParameters;
    private Integer removePerMonth;

    public GarbageTechSpecification() {
    }

    public GarbageTechSpecification(Integer id, String address, Map<String, List<String>> garbageContainerParameters, Integer removePerMonth) {
        super(id);
        this.address = address;
        this.garbageContainerParameters = garbageContainerParameters;
        this.removePerMonth = removePerMonth;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Map<String, List<String>> getGarbageContainerParameters() {
        return garbageContainerParameters;
    }

    public void setGarbageContainerParameters(Map<String, List<String>> garbageContainerParameters) {
        this.garbageContainerParameters = garbageContainerParameters;
    }

    public void add(String type, List<String> containerNumberAndCapacity) {
        garbageContainerParameters.put(type, containerNumberAndCapacity);
    }

    public Integer getRemovePerMonth() {
        return removePerMonth;
    }

    public void setRemovePerMonth(Integer removePerMonth) {
        this.removePerMonth = removePerMonth;
    }

    public Double getCapacityPerMonth() {
        Double amount = 0.0;
        for (List<String> value : garbageContainerParameters.values()) {
            amount += Integer.parseInt(value.get(0)) * Double.parseDouble(value.get(1));
        }
        amount = amount * removePerMonth;
        return amount;
    }

    public String getCapacityPerMonthString() {
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("###.##", symbols);
        return df.format(getCapacityPerMonth());
    }
}
