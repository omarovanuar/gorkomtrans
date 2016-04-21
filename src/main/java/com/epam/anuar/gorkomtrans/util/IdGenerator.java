package com.epam.anuar.gorkomtrans.util;

import com.epam.anuar.gorkomtrans.dao.ContractDao;
import com.epam.anuar.gorkomtrans.dao.TechSpecDao;
import com.epam.anuar.gorkomtrans.dao.UserDao;
import com.epam.anuar.gorkomtrans.dao.WalletDao;
import com.epam.anuar.gorkomtrans.entity.Wallet;

import java.util.Random;

public class IdGenerator {
    private static final Integer USER_ID_RANGE = 0;
    private static final Integer USER_ID_NUMBER = 10000;

    // TODO: 17.04.2016  
//    public static <T> Integer generate(Class<T> clazz) {
//        Random random = new Random();
//        Integer id = random.nextInt(clazz.getClass().getClassLoader().) + 10000;
//        if (T.findById(id) != null) {
//            id = generateTechSpecId(techSpecDao);
//        }
//        return id;
//    }
    
    public static Integer generateID(UserDao userDao) {
        Random random = new Random();
        Integer id = random.nextInt(10000);
        if (userDao.findById(id) != null) {
            id = generateID(userDao);
        }
        return id;
    }

    public static Integer generateID(TechSpecDao techSpecDao) {
        Random random = new Random();
        Integer id = random.nextInt(45000) + 10000;
        if (techSpecDao.findById(id) != null) {
            id = generateID(techSpecDao);
        }
        return id;
    }

    public static Integer generateID(ContractDao contractDao) {
        Random random = new Random();
        Integer id = random.nextInt(45000) + 55000;
        if (contractDao.findById(id) != null) {
            id = generateID(contractDao);
        }
        return id;
    }

    public static Integer generateID(WalletDao walletDao) {
        Random random = new Random();
        Integer id = random.nextInt(20000) + 100000;
        if (walletDao.findById(id) != null) {
            id = generateID(walletDao);
        }
        return id;
    }
}
