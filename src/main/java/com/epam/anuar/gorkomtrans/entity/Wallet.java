package com.epam.anuar.gorkomtrans.entity;

import org.joda.money.Money;

public class Wallet extends BaseEntity {
    private String account;
    private Money money;

    public Wallet(Integer id, String account, Money money) {
        super(id);
        this.account = account;
        this.money = money;
    }

    public String getAccount() {
        return account;
    }

    public Money getMoney() {
        return money;
    }
}
