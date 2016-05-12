package com.epam.anuar.gorkomtrans.service;

import com.epam.anuar.gorkomtrans.dao.DaoException;
import com.epam.anuar.gorkomtrans.dao.DaoFactory;
import com.epam.anuar.gorkomtrans.dao.UserDao;
import com.epam.anuar.gorkomtrans.entity.User;
import com.epam.anuar.gorkomtrans.entity.Wallet;
import com.epam.anuar.gorkomtrans.validator.ViolationException;
import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static com.epam.anuar.gorkomtrans.util.IdGenerator.generateID;

public class UserService {
    private final static Logger log = LoggerFactory.getLogger(UserService.class);
    public static final int ALL_USER_PAGE = 1;
    public static final int ALL_USER_RECORDS = 12;
    private static final Comparator<User> USER_ID_COMPARATOR = (o1, o2) -> o1.getId().compareTo(o2.getId());
    private DaoFactory dao;
    private UserDao userDao;

    public UserService() {
        dao = DaoFactory.getInstance();
        userDao = dao.getUserDao();
    }

    public User getLoginUser(String login, String password) throws ServiceException, ViolationException {
        User user;
        try {
            user = userDao.findByCredentials(login, password);
        } catch (DaoException e) {
            log.warn("Can't find user by credentials");
            throw new ServiceException();
        } finally {
            dao.close();
        }
        if (user == null) {
            log.info("Invalid login or pass");
            throw new ViolationException("Invalid login or pass");
        } else {
            return user;
        }
    }

    public User getUserByLogin(String login) throws ServiceException {
        User user;
        try {
            user = userDao.findByLogin(login);
        } catch (DaoException e) {
            log.warn("Can't find user by login");
            throw new ServiceException();
        } finally {
            dao.close();
        }
        return user;
    }

    public User getUserById(String id) throws ServiceException {
        User user;
        try {
            user = userDao.findById(Integer.parseInt(id));
        } catch (DaoException e) {
            log.warn("Can't find user by id");
            throw new ServiceException();
        } finally {
            dao.close();
        }
        return user;
    }

    public User addNewUser(Map<String, String> parameters) throws ServiceException {
        Integer id = generateID(userDao);
        parameters.put("Id", id.toString());
        try {
            userDao.insertByParameters(parameters);
        } catch (DaoException e) {
            log.warn("Can't insert user by parameters");
            throw new ServiceException();
        } finally {
            dao.close();
        }
        return new User(id, parameters.get("Email"), parameters.get("Login"), parameters.get("Password"));
    }

    public User getUserWithNewWallet(String id, Map<String, String> parameters, String walletId) throws ServiceException {
        try {
            userDao.updateWithWallet(id, parameters, walletId);
        } catch (DaoException e) {
            log.warn("Can't update user with wallet");
            throw new ServiceException();
        } finally {
            dao.close();
        }
        User user = getUserByParameters(id, parameters);
        user.setWallet(new Wallet(Integer.parseInt(walletId), user.getBankRequisitions(), Money.parse("KZT 0.00")));
        return user;
    }

    public User getUpdatedUser(String id, Map<String, String> parameters, Wallet wallet) throws ServiceException {
        try {
            userDao.update(id, parameters);
        } catch (DaoException e) {
            log.warn("Can't update user");
            throw new ServiceException();
        } finally {
            dao.close();
        }
        User user = getUserByParameters(id, parameters);
        user.setWallet(wallet);
        return user;
    }

    private User getUserByParameters(String id, Map<String, String> parameters) {
        return new User(Integer.parseInt(id), parameters.get("Password"), parameters.get("Email"),
                parameters.get("FirstName"), parameters.get("LastName"), parameters.get("PhoneNumber"),
                parameters.get("MainAddress"), parameters.get("Bank"), parameters.get("BankAccount"));
    }

    public List<User> getAllUsersPerPage(int page, int recordsPerPage) throws ServiceException {
        List<User> users;
        try {
            users = userDao.findAll((page - 1) * recordsPerPage, recordsPerPage);
        } catch (DaoException e) {
            log.warn("Can't find all users");
            throw new ServiceException();
        } finally {
            dao.close();
        }
        Collections.sort(users, USER_ID_COMPARATOR);
        return users;
    }


    public List<User> getUserByLoginPart(String loginPart, int page, int recordsPerPage) throws ServiceException {
        List<User> users;
        try {
            users = userDao.searchByLogin(loginPart, (page - 1) * recordsPerPage, recordsPerPage);
        } catch (DaoException e) {
            log.warn("Can't search user by login");
            throw new ServiceException();
        }
        dao.close();
        Collections.sort(users, USER_ID_COMPARATOR);
        return users;
    }

    public void updateUserView(Map<String, String> parameters) throws ServiceException {
        try {
            userDao.updatePassEmailRole(parameters.get("id"), parameters.get("Password"),
                    parameters.get("Email"), parameters.get("role-select"));
        } catch (DaoException e) {
            log.warn("Can't update user password, email, role");
            throw new ServiceException();
        }
        dao.close();
    }

    public void deleteUserById(String id) throws ServiceException {
        try {
            userDao.deleteById(id);
        } catch (DaoException e) {
            log.warn("Can't delete user by id");
            throw new ServiceException();
        }
        dao.close();
    }
}
