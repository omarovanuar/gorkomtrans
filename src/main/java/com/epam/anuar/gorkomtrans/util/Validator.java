package com.epam.anuar.gorkomtrans.util;

import com.epam.anuar.gorkomtrans.action.UnloggedException;
import com.epam.anuar.gorkomtrans.dao.DaoFactory;
import com.epam.anuar.gorkomtrans.dao.UserDao;
import com.epam.anuar.gorkomtrans.dao.WalletDao;
import com.epam.anuar.gorkomtrans.entity.RoleType;
import com.epam.anuar.gorkomtrans.entity.User;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Pattern;

public class Validator {
    private static final Pattern EMAIL = Pattern.compile("(?:(?:\\r\\n)?[ \\t])*(?:(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*)|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*:(?:(?:\\r\\n)?[ \\t])*(?:(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*)(?:,\\s*(?:(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*|(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)*\\<(?:(?:\\r\\n)?[ \\t])*(?:@(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*(?:,@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*)*:(?:(?:\\r\\n)?[ \\t])*)?(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\"(?:[^\\\"\\r\\\\]|\\\\.|(?:(?:\\r\\n)?[ \\t]))*\"(?:(?:\\r\\n)?[ \\t])*))*@(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*)(?:\\.(?:(?:\\r\\n)?[ \\t])*(?:[^()<>@,;:\\\\\".\\[\\] \\000-\\031]+(?:(?:(?:\\r\\n)?[ \\t])+|\\Z|(?=[\\[\"()<>@,;:\\\\\".\\[\\]]))|\\[([^\\[\\]\\r\\\\]|\\\\.)*\\](?:(?:\\r\\n)?[ \\t])*))*\\>(?:(?:\\r\\n)?[ \\t])*))*)?;\\s*)");
    private static final Pattern BANK_ACCOUNT = Pattern.compile("[A-Z]{2}\\d{4}-\\d{4}-\\d{4}-\\d{4}");
    private static final Pattern PHONE_NUMBER = Pattern.compile("\\d-\\d{3}-\\d{7}");
    private static final Pattern INTEGER_VALUE = Pattern.compile("[1-9]\\d*");
    private static final Pattern DOUBLE_VALUE = Pattern.compile("\\d+(\\.[0-9]+)?");
    private static Set<Violation> violations = new HashSet<>();

    public static void checkUnlogged(HttpServletRequest req) {
        if (req.getSession(false).getAttribute("user") == null) {
            throw new UnloggedException("Please, login");
        }
    }

    public static void checkAdmin(HttpServletRequest req) {
        User user = (User) req.getSession(false).getAttribute("user");
        if (!(user.getRole().equals(RoleType.ADMIN))) {
            System.out.println(user.getRole().toString());
            throw new UnloggedException("You have not admin role");
        }
    }

    public static void checkAdminOrModer(HttpServletRequest req) {
        User user = (User) req.getSession(false).getAttribute("user");
        if (!(user.getRole().equals(RoleType.ADMIN) || user.getRole().equals(RoleType.MODERATOR))) {
            throw new UnloggedException("You have not admin or moderator role");
        }
    }

    public static void isEmptyTechSpec(String euro, String standard, String nonStandardNumber) {
        if (euro == null && standard == null && nonStandardNumber.equals("0")) {
            throw new RuntimeException("Please, choose type of containers");
        }
    }

    public static Set<Violation> validateRegister(Map<String, String> parameters) {
        Set<Violation> returnViolations = new HashSet<>();
        putViolation(validateLogin(parameters, 0));
        putViolation(validatePassword(parameters, 1));
        putViolation(validateRegisterEmail(parameters, 2));
        putViolation(validateIsEmpty(parameters, 3, "FirstName"));
        putViolation(validateIsEmpty(parameters, 4, "LastName"));
        putViolation(validatePhoneNumber(parameters, 5));
        putViolation(validateIsEmpty(parameters, 6, "MainAddress"));
        putViolation(validateIsEmpty(parameters, 7, "Bank"));
        putViolation(validateBankAccount(parameters, 8));
        returnViolations.addAll(violations);
        violations.clear();
        return returnViolations;
    }

    public static Set<Violation> validatePersonalCabinet(Map<String, String> parameters, HttpServletRequest req) {
        Set<Violation> returnViolations = new HashSet<>();
        putViolation(validatePassword(parameters, 0));
        putViolation(validateEmail(parameters, 1, req));
        putViolation(validateIsEmpty(parameters, 2, "FirstName"));
        putViolation(validateIsEmpty(parameters, 3, "LastName"));
        putViolation(validatePhoneNumber(parameters, 4));
        putViolation(validateIsEmpty(parameters, 5, "MainAddress"));
        putViolation(validateIsEmpty(parameters, 6, "Bank"));
        putViolation(validateBankAccount(parameters, 7));
        returnViolations.addAll(violations);
        violations.clear();
        return returnViolations;
    }

    public static Set<Violation> validateTechSpec(Map<String, String> parameters) {
        Set<Violation> returnViolations = new HashSet<>();
        putViolation(validateIsEmpty(parameters, 0, "tech-address"));
        if (parameters.keySet().contains("euro")) {
            putViolation(validateInteger(parameters, 1, "euro"));
        }
        if (parameters.keySet().contains("standard")) {
            putViolation(validateInteger(parameters, 2, "standard"));
        }
        Integer nonStandardNumber = countNonStandard(parameters);
        for (int i = 1; i < nonStandardNumber; i++) {
            putViolation(validateInteger(parameters, i + 2, "container-number-" + i));
        }
        for (int i = 1; i < nonStandardNumber; i++) {
            putViolation(validateDouble(parameters, i + 5, "container-capacity-" + i));
        }
        putViolation(validateInteger(parameters, 9, "perMonth"));
        putViolation(validateInteger(parameters, 10, "providingMonthNumber"));
        returnViolations.addAll(violations);
        violations.clear();
        return returnViolations;
    }

    private static Integer countNonStandard(Map<String, String> parameters) {
        if (parameters.keySet().contains("container-number-3")) return 4;
        else if (parameters.keySet().contains("container-number-2")) return 3;
        else if (parameters.keySet().contains("container-number-1")) return 2;
        else return 1;
    }

    private static Violation validateLogin(Map<String, String> parameters, Integer violationNumber) {
        DaoFactory dao = DaoFactory.getInstance();
        UserDao userDao = dao.getUserDao();
        if (parameters.get("Login").isEmpty()) {
            return new Violation("Login can't be empty", violationNumber);
        } else if (userDao.findByLogin(parameters.get("Login")) != null) {
            return new Violation("Current login is already exist", violationNumber);
        }
        dao.close();
        return null;
    }

    private static Violation validatePassword(Map<String, String> parameters, Integer violationNumber) {
        if (parameters.get("Password").isEmpty()){
            return new Violation("Password can't be empty", violationNumber);
        } else if (parameters.get("Password").length() < 8) {
            return new Violation("Password must contain at least 8 symbols", violationNumber);
        }
        return null;
    }

    private static Violation validateRegisterEmail(Map<String, String> parameters, Integer violationNumber) {
        DaoFactory dao = DaoFactory.getInstance();
        UserDao userDao = dao.getUserDao();
        if (parameters.get("Email").isEmpty()){
            return new Violation("Email can't be empty", violationNumber);
        } else if (userDao.findByEmail(parameters.get("Email")) != null) {
            return new Violation("Current email is already exist", violationNumber);
        } else if (!EMAIL.matcher(parameters.get("Email")).matches()) {
            return new Violation("Invalid email", violationNumber);
        }
        dao.close();
        return null;
    }

    private static Violation validateEmail(Map<String, String> parameters, Integer violationNumber, HttpServletRequest req) {
        DaoFactory dao = DaoFactory.getInstance();
        UserDao userDao = dao.getUserDao();
        if (parameters.get("Email").isEmpty()){
            return new Violation("Email can't be empty", violationNumber);
        } else if (userDao.findByEmail(parameters.get("Email")) != null &&
                !((User) req.getSession(false).getAttribute("user")).getEmail().equals(parameters.get("Email"))) {
            dao.close();
            return new Violation("Current email is already exist", violationNumber);
        } else if (!EMAIL.matcher(parameters.get("Email")).matches()) {
            return new Violation("Invalid email", violationNumber);
        }
        dao.close();
        return null;
    }

    private static Violation validateIsEmpty(Map<String, String> parameters, Integer violationNumber, String emptyParam) {
        if (parameters.get(emptyParam).isEmpty()){
            return new Violation(emptyParam + " can't be empty", violationNumber);
        }
        return null;
    }

    private static Violation validateInteger(Map<String, String> parameters, Integer violationNumber, String validatingString) {
        if (parameters.get(validatingString).isEmpty()){
            return new Violation(validatingString + " can't be empty", violationNumber);
        } else if (!INTEGER_VALUE.matcher(parameters.get(validatingString)).matches()) {
            return new Violation(validatingString + " must be Integer type", violationNumber);
        }
        return null;
    }

    private static Violation validateDouble(Map<String, String> parameters, Integer violationNumber, String validatingString) {
        if (parameters.get(validatingString).isEmpty()){
            return new Violation(validatingString + " can't be empty", violationNumber);
        } else if (!DOUBLE_VALUE.matcher(parameters.get(validatingString)).matches()) {
            return new Violation(validatingString + " must be Double type", violationNumber);
        }
        return null;
    }

    private static Violation validatePhoneNumber(Map<String, String> parameters, Integer violationNumber) {
        if (parameters.get("PhoneNumber").isEmpty()){
            return new Violation("PhoneNumber can't be empty", violationNumber);
        } else if (!PHONE_NUMBER.matcher(parameters.get("PhoneNumber")).matches()) {
            return new Violation("PhoneNumber must be in the form N-NNN-NNNNNNN(N-Number)", violationNumber);
        }
        return null;
    }

    private static Violation validateBankAccount(Map<String, String> parameters, Integer violationNumber) {
        DaoFactory dao = DaoFactory.getInstance();
        WalletDao walletDao = dao.getWalletDao();
        if (parameters.get("BankAccount").isEmpty()){
            return new Violation("BankAccount can't be empty", violationNumber);
        } else if (!BANK_ACCOUNT.matcher(parameters.get("BankAccount")).matches()) {
            return new Violation("BankAccount must be in the form LLNNNN-NNNN-NNNN-NNNN(L-Big letter, N-Number", violationNumber);
        } else if (walletDao.findByAccount(parameters.get("Bank") + " " + parameters.get("BankAccount")) != null) {
            dao.close();
            return new Violation("BankAccount is already exist", violationNumber);
        }
        dao.close();
        return null;
    }



    private static void putViolation(Violation violation) {
        if (violation != null) {
            violations.add(violation);
        }
    }


}
