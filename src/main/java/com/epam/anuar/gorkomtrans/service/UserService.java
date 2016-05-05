package com.epam.anuar.gorkomtrans.service;

import com.epam.anuar.gorkomtrans.dao.DaoFactory;
import com.epam.anuar.gorkomtrans.dao.UserDao;
import com.epam.anuar.gorkomtrans.entity.User;
import com.epam.anuar.gorkomtrans.entity.Wallet;
import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static com.epam.anuar.gorkomtrans.util.IdGenerator.generateID;

public class UserService {
    public static final int ALL_USER_PAGE = 1;
    public static final int ALL_USER_RECORDS = 13;
    private static final Comparator<User> USER_ID_COMPARATOR = (o1, o2) -> o1.getId().compareTo(o2.getId());
    private final static Logger log = LoggerFactory.getLogger(UserService.class);
    private DaoFactory dao;
    private UserDao userDao;

    public UserService() {
        dao = DaoFactory.getInstance();
        userDao = dao.getUserDao();
    }

    public User getLoginUser(String login, String password) throws ServiceException {
        User loginUser = userDao.findByCredentials(login, password);
        dao.close();
        if (loginUser == null) {
            log.info("Invalid login or pass");
            throw new ServiceException("Invalid login or pass");
        } else {
            return loginUser;
        }
    }


    public User getViewingUser(String id) {
        User user = userDao.findById(Integer.parseInt(id));
        dao.close();
        return user;
    }

    public User getUserByLogin(String login) {
        User user = userDao.findByLogin(login);
        dao.close();
        return user;
    }

    public User getUserById(String id) {
        User user = userDao.findById(Integer.parseInt(id));
        dao.close();
        return user;
    }

    public User addNewUser(Map<String, String> parameters) {
        Integer id = generateID(userDao);
        parameters.put("Id", id.toString());
        userDao.insertByParameters(parameters);
        User user = new User(id, parameters.get("Email"), parameters.get("Login"), parameters.get("Password"));
        dao.close();
        return user;
    }

    public User getUserWithNewWallet(String id, Map<String, String> parameters, String walletId) {
        userDao.updateWithWallet(id, parameters, walletId);
        User user = getUserByParameters(id, parameters);
        user.setWallet(new Wallet(Integer.parseInt(walletId), user.getBankRequisitions(), Money.parse("KZT 0.00")));
        dao.close();
        return user;
    }

    public User getUpdatedUser(String id, Map<String, String> parameters, Wallet wallet) {
        userDao.update(id, parameters);
        User user = getUserByParameters(id, parameters);
        user.setWallet(wallet);
        dao.close();
        return user;
    }

    private User getUserByParameters(String id, Map<String, String> parameters) {
        return new User(Integer.parseInt(id), parameters.get("Password"), parameters.get("Email"),
                parameters.get("FirstName"), parameters.get("LastName"), parameters.get("PhoneNumber"),
                parameters.get("MainAddress"), parameters.get("Bank"), parameters.get("BankAccount"));
    }

    public List<User> getAllUsersPerPage(int page, int recordsPerPage) {
        List<User> users = userDao.findAll((page - 1) * recordsPerPage, recordsPerPage);
        Collections.sort(users, USER_ID_COMPARATOR);
        dao.close();
        return users;
    }


    public List<User> getUserByLoginPart(String loginPart, int page, int recordsPerPage) {
        List<User> users = userDao.searchByLogin(loginPart, (page - 1) * recordsPerPage, recordsPerPage);
        dao.close();
        Collections.sort(users, USER_ID_COMPARATOR);
        return users;
    }

    public void updateUserView(Map<String, String> parameters) {
        userDao.updatePassEmailRole(parameters.get("id"), parameters.get("Password"),
                parameters.get("Email"), parameters.get("role-select"));
        dao.close();
    }

    public void deleteUserById(String id) {
        userDao.deleteById(id);
        dao.close();
    }
}
