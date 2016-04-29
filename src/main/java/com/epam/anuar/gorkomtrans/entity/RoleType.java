package com.epam.anuar.gorkomtrans.entity;

public enum RoleType {
    ADMIN(2), MODERATOR(1), REGISTERED_USER(0);
    private Integer roleCode;

    RoleType(Integer roleCode) {
        this.roleCode = roleCode;
    }

    public Integer getRoleCode() {
        return roleCode;
    }
}
