package com.epam.anuar.gorkomtrans.entity;

public class User extends BaseEntity {

    private RoleType role;
    private String email;
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String mainAddress;
    private String bankName;
    private String bankAccount;

    public User() {
    }

    public User(Integer id, String email, String login, String password) {
        super(id);
        this.email = email;
        this.login = login;
        this.password = password;
    }

    public User(Integer id, String email, String login, String password, String firstName, String lastName, String phoneNumber,
                String mainAddress, String bankName, String bankAccount) {
        super(id);
        this.email = email;
        this.login = login;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.mainAddress = mainAddress;
        this.bankName = bankName;
        this.bankAccount = bankAccount;
    }

    public RoleType getRole() {
        if (role == null) {
            return RoleType.REGISTERED_USER;
        }
        return role;
    }

    public void setRoleByCode(Integer roleCode) {
        switch (roleCode) {
            case 0:
                this.role = RoleType.REGISTERED_USER;
                break;
            case 1:
                this.role = RoleType.MODERATOR;
                break;
            case 2:
                this.role = RoleType.ADMIN;
                break;
            default:
                throw new RuntimeException();
        }
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return firstName + lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
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
