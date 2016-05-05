package com.epam.anuar.gorkomtrans.service;

import com.epam.anuar.gorkomtrans.entity.Contract;
import com.epam.anuar.gorkomtrans.entity.User;

import java.util.Comparator;

public class Service {
    public static final int ALL_USER_PAGE = 1;
    public static final int ALL_USER_RECORDS = 13;
    public static final int USER_CONTRACT_PAGE = 1;
    public static final int USER_CONTRACT_RECORDS = 13;
    public static final int ALL_CONTRACTS_PAGE = 1;
    public static final int ALL_CONTRACTS_RECORDS = 13;
    public static final Comparator<User> USER_ID_COMPARATOR = (o1, o2) -> o1.getId().compareTo(o2.getId());
    public static final Comparator<Contract> CONTRACT_ID_COMPARATOR = (o1, o2) -> o1.getId().compareTo(o2.getId());
}
