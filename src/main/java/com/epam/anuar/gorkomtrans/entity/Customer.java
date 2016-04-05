package com.epam.anuar.gorkomtrans.entity;

public class Customer extends User{
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String mainAddress;
    private String bankName;
    private String bankAccount;


    public Customer() {
    }

    public Customer(String firstName, String lastName, String phoneNumber, String mainAddress, String bankName, String bankAccount) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.mainAddress = mainAddress;
        this.bankName = bankName;
        this.bankAccount = bankAccount;
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

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }


}
