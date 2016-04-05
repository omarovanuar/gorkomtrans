package com.epam.anuar.gorkomtrans.entity;

public class Provider {
    private static final Provider instance = new Provider("GorKomTrans", "(7212) 56-23-70, (7212) 56-51-08, (7212) 56-31-81, (7212) 56-11-33",
            "Prigorodnay st. 7/2", "Sberbank KZ7824482901837461", 100032, "(7212) 56-51-08", "gorkomtrans@yandex.kz");
    private String organizationName;
    private String phoneNumber;
    private String mainAddress;
    private String bankDetails;
    private Integer postIndex;
    private String fax;
    private String email;

    private Provider(String organizationName, String phoneNumber, String mainAddress, String bankDetails, Integer postIndex, String fax, String email) {
        this.organizationName = organizationName;
        this.phoneNumber = phoneNumber;
        this.mainAddress = mainAddress;
        this.bankDetails = bankDetails;
        this.postIndex = postIndex;
        this.fax = fax;
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

    public String getPostIndex() {
        return postIndex.toString();
    }

    public String getFax() {
        return fax;
    }

    public String getEmail() {
        return email;
    }
}
