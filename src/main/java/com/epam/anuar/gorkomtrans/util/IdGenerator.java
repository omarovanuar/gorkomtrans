package com.epam.anuar.gorkomtrans.util;

import com.epam.anuar.gorkomtrans.dao.ContractDao;
import com.epam.anuar.gorkomtrans.dao.TechSpecDao;
import com.epam.anuar.gorkomtrans.dao.UserDao;
import com.epam.anuar.gorkomtrans.dao.WalletDao;
import com.epam.anuar.gorkomtrans.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Random;

public class IdGenerator {
    private static Logger log = LoggerFactory.getLogger(ContractDao.class.getName());

    public static Integer generateID(Object object) {
        Random random = new Random();
        Integer id;
        Class[] args = new Class[]{Integer.class};
        try {
            String objName = object.getClass().getSimpleName().toUpperCase();
            id = random.nextInt((Integer) (object.getClass().getField("ID_QUANTITY_" + objName).get(object))) +
                    (Integer) (object.getClass().getField("ID_SHIFT_" + objName).get(object));
            if (object.getClass().getMethod("findById", args).invoke(object, id) != null) {
                id = generateID(object);
            }
        } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            log.warn("Can't generate id " + e);
            throw new RuntimeException();
        }
        return id;
    }
}