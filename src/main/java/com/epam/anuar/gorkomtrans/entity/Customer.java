package com.epam.anuar.gorkomtrans.entity;

import java.util.List;

public class Customer extends User{
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String mainAddress;
    private List<GarbageTechSpecification> garbageTechSpecList;
    private String bankDetails;


    public Customer() {
    }

    public Customer(Integer id, String email, String login, String password, String firstName, String lastName, String phoneNumber, String mainAddress, List<GarbageTechSpecification> garbageTechSpecList, String bankDetails) {
        super(id, email, login, password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.mainAddress = mainAddress;
        this.garbageTechSpecList = garbageTechSpecList;
        this.bankDetails = bankDetails;
    }

    public String getFullName() {
        return firstName + lastName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMainAddress() {
        return mainAddress;
    }

    public void setMainAddress(String mainAddress) {
        this.mainAddress = mainAddress;
    }

    public List<GarbageTechSpecification> getGarbageTechSpecList() {
        return garbageTechSpecList;
    }

    public void setGarbageTechSpecList(List<GarbageTechSpecification> garbageTechSpecList) {
        this.garbageTechSpecList = garbageTechSpecList;
    }

    public String getBankDetails() {
        return bankDetails;
    }

    public void setBankDetails(String bankDetails) {
        this.bankDetails = bankDetails;
    }

    public void addGarbageLocationList(GarbageTechSpecification garbageSpec) {
        garbageTechSpecList.add(garbageSpec);
    }

    public void removeGarbageLocationList(GarbageTechSpecification garbageSpec) {
        garbageTechSpecList.remove(garbageSpec);
    }

    public double getCustomerGarbageCapacity() {
        double totalCapacity = 0;
        for (GarbageTechSpecification techspec : garbageTechSpecList) {
            totalCapacity += techspec.getTotalGarbageCapacity();
        }
        return totalCapacity;
    }
}
