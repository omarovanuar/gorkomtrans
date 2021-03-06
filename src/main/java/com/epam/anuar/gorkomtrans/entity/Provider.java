package com.epam.anuar.gorkomtrans.entity;

public class Provider {
    private static final Provider instance = new Provider("GorKomTrans", "(7212) 56-23-70, (7212) 56-51-08, (7212) 56-31-81, (7212) 56-11-33",
            "Prigorodnay st. 7/2", "Sberbank KZ7824482901837461", "gorkomtrans@yandex.kz");
    private String organizationName;
    private String phoneNumber;
    private String mainAddress;
    private String bankDetails;
    private String email;

    private Provider(String organizationName, String phoneNumber, String mainAddress, String bankDetails, String email) {
        this.organizationName = organizationName;
        this.phoneNumber = phoneNumber;
        this.mainAddress = mainAddress;
        this.bankDetails = bankDetails;
        this.email = email;
    }

    public static Provider getProviderInstance() {
        return instance;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getMainAddress() {
        return mainAddress;
    }

    public String getBankDetails() {
        return bankDetails;
    }

    public String getEmail() {
        return email;
    }
}
