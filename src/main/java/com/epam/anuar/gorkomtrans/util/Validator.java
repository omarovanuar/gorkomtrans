package com.epam.anuar.gorkomtrans.util;

import com.epam.anuar.gorkomtrans.action.AccessException;
import com.epam.anuar.gorkomtrans.dao.DaoException;
import com.epam.anuar.gorkomtrans.dao.DaoFactory;
import com.epam.anuar.gorkomtrans.dao.UserDao;
import com.epam.anuar.gorkomtrans.dao.WalletDao;
import com.epam.anuar.gorkomtrans.entity.RoleType;
import com.epam.anuar.gorkomtrans.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Pattern;

public class Validator {
    private final static Logger log = LoggerFactory.getLogger(Validator.class);
    private static final Pattern EMAIL = Pattern.compile(".+@.+\\..+");
    private static final Pattern BANK_ACCOUNT = Pattern.compile("[A-Z]{2}\\d{4}-\\d{4}-\\d{4}-\\d{4}");
    private static final Pattern PHONE_NUMBER = Pattern.compile("\\d-\\d{3}-\\d{7}");
    private static final Pattern INTEGER_VALUE = Pattern.compile("[1-9]\\d*");
    private static final Pattern DOUBLE_VALUE = Pattern.compile("\\d+(\\.[0-9]+)?");
    private static Set<Violation> violations = new HashSet<>();
    private static ResourceBundle bundle;

    public static void checkUnlogged(HttpServletRequest req) {
        if (req.getSession(false).getAttribute("user") == null) {
            bundle = ResourceBundle.getBundle("error", Locale.forLanguageTag(req.getSession(false).getAttribute("locale").toString()));
            log.info("Unlogged access");
            throw new AccessException(bundle.getString("not.logged"));
        }
    }

    public static void checkAdmin(HttpServletRequest req) {
        User user = (User) req.getSession(false).getAttribute("user");
        if (!(user.getRole().equals(RoleType.ADMIN))) {
            bundle = ResourceBundle.getBundle("error", Locale.forLanguageTag(req.getSession(false).getAttribute("locale").toString()));
            log.warn("Not admin access: " + user.getLogin());
            throw new AccessException(bundle.getString("not.admin"));
        }
    }

    public static void checkAdminOrModer(HttpServletRequest req) {
        User user = (User) req.getSession(false).getAttribute("user");
        if (!(user.getRole().equals(RoleType.ADMIN) || user.getRole().equals(RoleType.MODERATOR))) {
            bundle = ResourceBundle.getBundle("error", Locale.forLanguageTag(req.getSession(false).getAttribute("locale").toString()));
            log.warn("Not admin or moderator access: " + user.getLogin());
            throw new AccessException(bundle.getString("not.admin-moder"));
        }
    }

    public static Violation isEmptyTechSpec(String euro, String standard, String nonStandardNumber, HttpServletRequest req) {
        if (euro == null && standard == null && nonStandardNumber.equals("0")) {
            bundle = ResourceBundle.getBundle("error", Locale.forLanguageTag(req.getSession(false).getAttribute("locale").toString()));
            return new Violation(bundle.getString("empty.type"), 0);
        } else return null;
    }

    public static Set<Violation> validateRegister(Map<String, String> parameters, HttpServletRequest req) throws ViolationException {
        bundle = ResourceBundle.getBundle("error", Locale.forLanguageTag(req.getSession(false).getAttribute("locale").toString()));
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

    public static Violation InvalidLoginPass(HttpServletRequest req) {
        bundle = ResourceBundle.getBundle("error", Locale.forLanguageTag(req.getSession(false).getAttribute("locale").toString()));
        return new Violation(bundle.getString("invalid.login-pass"));
    }

    public static Set<Violation> validatePersonalCabinet(Map<String, String> parameters, HttpServletRequest req) throws ViolationException {
        bundle = ResourceBundle.getBundle("error", Locale.forLanguageTag(req.getSession(false).getAttribute("locale").toString()));
        Set<Violation> returnViolations = new HashSet<>();
        putViolation(validatePassword(parameters, 0));
        putViolation(validatePersonalEmail(parameters, 1, req));
        putViolation(validateIsEmpty(parameters, 2, "FirstName"));
        putViolation(validateIsEmpty(parameters, 3, "LastName"));
        putViolation(validatePhoneNumber(parameters, 4));
        putViolation(validateIsEmpty(parameters, 5, "MainAddress"));
        putViolation(validateIsEmpty(parameters, 6, "Bank"));
        putViolation(validateChangeBankAccount(parameters, 7, req));
        returnViolations.addAll(violations);
        violations.clear();
        return returnViolations;
    }

    public static Set<Violation> validateUserView(Map<String, String> parameters, HttpServletRequest req) throws ViolationException {
        bundle = ResourceBundle.getBundle("error", Locale.forLanguageTag(req.getSession(false).getAttribute("locale").toString()));
        Set<Violation> returnViolations = new HashSet<>();
        putViolation(validatePassword(parameters, 0));
        putViolation(validateEmail(parameters, 1));
        putViolation(validateDouble(parameters, 2, "balance"));
        returnViolations.addAll(violations);
        violations.clear();
        return returnViolations;
    }

    public static Set<Violation> validateTechSpec(Map<String, String> parameters, HttpServletRequest req) {
        bundle = ResourceBundle.getBundle("error", Locale.forLanguageTag(req.getSession(false).getAttribute("locale").toString()));
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

    private static Violation validateLogin(Map<String, String> parameters, Integer violationNumber) throws ViolationException {
        DaoFactory dao = DaoFactory.getInstance();
        UserDao userDao = dao.getUserDao();
        try {
            if (parameters.get("Login").isEmpty()) {
                return new Violation(bundle.getString("empty.login"), violationNumber);
            } else if (userDao.findByLogin(parameters.get("Login")) != null) {
                return new Violation(bundle.getString("exist.login"), violationNumber);
            }
        } catch (DaoException e) {
            log.warn("Can't find user by login");
            throw new ViolationException();
        } finally {
            dao.close();
        }
        return null;
    }

    private static Violation validatePassword(Map<String, String> parameters, Integer violationNumber) {
        if (parameters.get("Password").isEmpty()) {
            return new Violation(bundle.getString("empty.password"), violationNumber);
        } else if (parameters.get("Password").length() < 8) {
            return new Violation(bundle.getString("not.password"), violationNumber);
        }
        return null;
    }

    private static Violation validateRegisterEmail(Map<String, String> parameters, Integer violationNumber) throws ViolationException {
        DaoFactory dao = DaoFactory.getInstance();
        UserDao userDao = dao.getUserDao();
        try {
            if (parameters.get("Email").isEmpty()) {
                return new Violation(bundle.getString("empty.email"), violationNumber);
            } else if (userDao.findByEmail(parameters.get("Email")) != null) {
                return new Violation(bundle.getString("exist.email"), violationNumber);
            } else if (!EMAIL.matcher(parameters.get("Email")).matches()) {
                return new Violation(bundle.getString("not.email"), violationNumber);
            }
        } catch (DaoException e) {
            log.warn("Can't find user by email");
            throw new ViolationException();
        } finally {
            dao.close();
        }
        return null;
    }

    private static Violation validatePersonalEmail(Map<String, String> parameters, Integer violationNumber, HttpServletRequest req) throws ViolationException {
        DaoFactory dao = DaoFactory.getInstance();
        UserDao userDao = dao.getUserDao();
        try {
            if (parameters.get("Email").isEmpty()) {
                return new Violation(bundle.getString("empty.email"), violationNumber);
            } else if (userDao.findByEmail(parameters.get("Email")) != null &&
                    !((User) req.getSession(false).getAttribute("user")).getEmail().equals(parameters.get("Email"))) {
                dao.close();
                return new Violation(bundle.getString("exist.email"), violationNumber);
            } else if (!EMAIL.matcher(parameters.get("Email")).matches()) {
                return new Violation(bundle.getString("not.email"), violationNumber);
            }
        } catch (DaoException e) {
            log.warn("Can't find user by email");
            throw new ViolationException();
        } finally {
            dao.close();
        }
        return null;
    }

    private static Violation validateEmail(Map<String, String> parameters, Integer violationNumber) throws ViolationException {
        DaoFactory dao = DaoFactory.getInstance();
        UserDao userDao = dao.getUserDao();
        try {
            if (parameters.get("Email").isEmpty()) {
                return new Violation(bundle.getString("empty.email"), violationNumber);
            } else if (userDao.findByEmail(parameters.get("Email")) != null &&
                    !(userDao.findById(Integer.parseInt(parameters.get("id")))).getEmail().equals(parameters.get("Email"))) {
                dao.close();
                return new Violation(bundle.getString("exist.email"), violationNumber);
            } else if (!EMAIL.matcher(parameters.get("Email")).matches()) {
                return new Violation(bundle.getString("not.email"), violationNumber);
            }
        } catch (DaoException e) {
            log.warn("Can't find user by id or email");
            throw new ViolationException();
        } finally {
            dao.close();
        }
        return null;
    }

    private static Violation validateIsEmpty(Map<String, String> parameters, Integer violationNumber, String emptyParam) {
        if (parameters.get(emptyParam).isEmpty()) {
            return new Violation(bundle.getString("empty.all"), violationNumber);
        }
        return null;
    }

    private static Violation validateInteger(Map<String, String> parameters, Integer violationNumber, String validatingString) {
        if (parameters.get(validatingString).isEmpty()) {
            return new Violation(validatingString + " " + bundle.getString("empty.all"), violationNumber);
        } else if (!INTEGER_VALUE.matcher(parameters.get(validatingString)).matches()) {
            return new Violation(validatingString + " " + bundle.getString("not.integer"), violationNumber);
        }
        return null;
    }

    private static Violation validateDouble(Map<String, String> parameters, Integer violationNumber, String validatingString) {
        if (parameters.get(validatingString).isEmpty()) {
            return new Violation(validatingString + " " + bundle.getString("empty.all"), violationNumber);
        } else if (!DOUBLE_VALUE.matcher(parameters.get(validatingString)).matches()) {
            return new Violation(validatingString + " " + bundle.getString("not.double"), violationNumber);
        }
        return null;
    }

    private static Violation validatePhoneNumber(Map<String, String> parameters, Integer violationNumber) {
        if (parameters.get("PhoneNumber").isEmpty()) {
            return new Violation(bundle.getString("empty.phone"), violationNumber);
        } else if (!PHONE_NUMBER.matcher(parameters.get("PhoneNumber")).matches()) {
            return new Violation(bundle.getString("not.phone"), violationNumber);
        }
        return null;
    }

    private static Violation validateBankAccount(Map<String, String> parameters, Integer violationNumber) throws ViolationException {
        DaoFactory dao = DaoFactory.getInstance();
        WalletDao walletDao = dao.getWalletDao();
        try {
            if (parameters.get("BankAccount").isEmpty()) {
                return new Violation(bundle.getString("empty.bank-account"), violationNumber);
            } else if (!BANK_ACCOUNT.matcher(parameters.get("BankAccount")).matches()) {
                return new Violation(bundle.getString("not.bank-account"), violationNumber);
            } else if (walletDao.findByAccount(parameters.get("Bank") + " " + parameters.get("BankAccount")) != null) {
                dao.close();
                return new Violation(bundle.getString("exist.bank-account"), violationNumber);
            }
        } catch (DaoException e) {
            log.warn("Can't find wallet by account");
            throw new ViolationException();
        } finally {
            dao.close();
        }
        return null;
    }

    private static Violation validateChangeBankAccount(Map<String, String> parameters, Integer violationNumber, HttpServletRequest req) throws ViolationException {
        DaoFactory dao = DaoFactory.getInstance();
        WalletDao walletDao = dao.getWalletDao();
        try {
            if (parameters.get("BankAccount").isEmpty()) {
                return new Violation(bundle.getString("empty.bank-account"), violationNumber);
            } else if (!BANK_ACCOUNT.matcher(parameters.get("BankAccount")).matches()) {
                return new Violation(bundle.getString("not.bank-account"), violationNumber);
            } else if (walletDao.findByAccount(parameters.get("Bank") + " " + parameters.get("BankAccount")) != null &&
                    !((User) req.getSession(false).getAttribute("user")).getBankRequisitions().equals(parameters.get("Bank") + " "
                            + parameters.get("BankAccount"))) {
                dao.close();
                return new Violation(bundle.getString("exist.bank-account"), violationNumber);
            }
        } catch (DaoException e) {
            log.warn("Can't find wallet by account");
            throw new ViolationException();
        } finally {
            dao.close();
        }
        return null;
    }

    private static void putViolation(Violation violation) {
        if (violation != null) {
            violations.add(violation);
        }
    }


}
