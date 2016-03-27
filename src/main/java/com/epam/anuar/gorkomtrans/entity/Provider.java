package com.epam.anuar.gorkomtrans.entity;

public class Provider {
    private String organizationName;
    private String phoneNumber;
    private String mainAddress;
    private String bankDetails;

    public Provider() {
    }

    private Provider(String organizationName, String phoneNumber, String mainAddress, String bankDetails) {
        this.organizationName = organizationName;
        this.phoneNumber = phoneNumber;
        this.mainAddress = mainAddress;
        this.bankDetails = bankDetails;
    }

    public void getProviderInstance() {
        new Provider("GorKomTrans", "8-7212-56-11-33", "Prigorodnay st. 7/2", "Sberbank KZ7824482901837461");
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
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

    public String getBankDetails() {
        return bankDetails;
    }

    public void setBankDetails(String bankDetails) {
        this.bankDetails = bankDetails;
    }
}
