package com.epam.anuar.gorkomtrans.entity;

public class User extends BaseEntity {

    private RoleType role;
    private String email;
    private String login;
    private String password;

    public User() {
    }

    public User(Integer id, String email, String login, String password) {
        super(id);
        this.email = email;
        this.login = login;
        this.password = password;
    }

    public RoleType getRole() {
        if (role == null) {
            return RoleType.REGISTERED_USER;
        }
        return role;
    }

    public void setRole(RoleType role) {
        this.role = role;
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

}
