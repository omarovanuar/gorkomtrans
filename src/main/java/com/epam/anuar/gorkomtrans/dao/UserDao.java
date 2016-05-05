package com.epam.anuar.gorkomtrans.dao;

import com.epam.anuar.gorkomtrans.entity.User;

import java.sql.*;
import java.util.*;

import static com.epam.anuar.gorkomtrans.dao.Statement.*;

public class UserDao {
    private Connection con;
    private List<String> parameters = new ArrayList<>();
    private ResourceBundle rb = ResourceBundle.getBundle("sql");
    public static final Integer ID_QUANTITY_USERDAO = 10000;
    public static final Integer ID_SHIFT_USERDAO = 0;

    public UserDao(Connection con) {
        this.con = con;
    }

    public byte insertUser(User user) {
        this.parameters.add(user.getId().toString());
        this.parameters.add(user.getLogin());
        this.parameters.add(user.getPassword());
        this.parameters.add(user.getEmail());
        this.parameters.add(user.getFirstName());
        this.parameters.add(user.getLastName());
        this.parameters.add(user.getPhoneNumber());
        this.parameters.add(user.getMainAddress());
        this.parameters.add(user.getBankName());
        this.parameters.add(user.getBankAccount());
        this.parameters.add(user.getWallet().getId().toString());
        String value = rb.getString("insert-user.parameters");
        byte result = executeStatement(con, value, this.parameters);
        this.parameters.clear();
        return result;
    }

    public byte insertByParameters(Map<String, String> parameters) {
        this.parameters.add(parameters.get("Id"));
        this.parameters.add(parameters.get("Login"));
        this.parameters.add(parameters.get("Password"));
        this.parameters.add(parameters.get("Email"));
        this.parameters.add(parameters.get("FirstName"));
        this.parameters.add(parameters.get("LastName"));
        this.parameters.add(parameters.get("PhoneNumber"));
        this.parameters.add(parameters.get("MainAddress"));
        this.parameters.add(parameters.get("Bank"));
        this.parameters.add(parameters.get("BankAccount"));
        this.parameters.add(parameters.get("WalletId"));
        String value = rb.getString("insert-user.parameters");
        byte result = executeStatement(con, value, this.parameters);
        this.parameters.clear();
        return result;
    }

    public User findById(Integer id) {
        String value = rb.getString("find-user.id");
        parameters.add(id.toString());
        PreparedStatement ps = getStatement(con, value, parameters);
        parameters.clear();
        List<User> users = getUserFromDb(ps, parameters);
        if (users.size() != 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

    public User findByLogin(String login) {
        String value = rb.getString("find-user.login");
        parameters.add(login);
        PreparedStatement ps = getStatement(con, value, parameters);
        parameters.clear();
        List<User> users = getUserFromDb(ps, parameters);
        if (users.size() != 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

    public User findByEmail(String email) {
        String value = rb.getString("find-user.email");
        parameters.add(email);
        PreparedStatement ps = getStatement(con, value, parameters);
        parameters.clear();
        List<User> users = getUserFromDb(ps, parameters);
        if (users.size() != 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

    public User findByCredentials(String login, String password) {
        String value = rb.getString("find-user.login-pass");
        parameters.add(login);
        parameters.add(password);
        PreparedStatement ps = getStatement(con, value, parameters);
        parameters.clear();
        List<User> users = getUserFromDb(ps, parameters);
        if (users.size() != 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

    public List<User> findAll(Integer offset, Integer noOfRecords) {
        String value = rb.getString("find-user.all");
        parameters.add(offset.toString());
        parameters.add(noOfRecords.toString());
        PreparedStatement ps = Statement.getStatement(con, value, parameters);
        parameters.clear();
        return getUserFromDb(ps, parameters);
    }

    private List<User> getUserFromDb(PreparedStatement ps, List<String> parameters) {
        List<User> users = new ArrayList<>();
        User user;
        ResultSet rs = null;
        ResultSetMetaData rsmd;
        Map<String, String> parametersFromDb = new HashMap<>();
        WalletDao walletDao = DaoFactory.getInstance().getWalletDao();

        try {
            rs = ps.getResultSet();
            rsmd = rs.getMetaData();
            while (rs.next()) {
                user = new User();
                for (int i = 1; i < rsmd.getColumnCount() + 1; i++) {
                    parametersFromDb.put(rsmd.getColumnName(i), rs.getString(i));
                }
                user.setId(Integer.parseInt(parametersFromDb.get("ID")));
                user.setLogin(parametersFromDb.get("LOGIN"));
                user.setPassword(parametersFromDb.get("PASSWORD"));
                user.setEmail(parametersFromDb.get("EMAIL"));
                user.setRoleByCode(Integer.parseInt(parametersFromDb.get("ROLE")));
                user.setFirstName(parametersFromDb.get("FIRSTNAME"));
                user.setLastName(parametersFromDb.get("LASTNAME"));
                user.setPhoneNumber(parametersFromDb.get("PHONENUMBER"));
                user.setMainAddress(parametersFromDb.get("MAINADDRESS"));
                user.setBankName(parametersFromDb.get("BANK"));
                user.setBankAccount(parametersFromDb.get("BANKACCOUNT"));
                user.setWallet(walletDao.findById(Integer.parseInt(parametersFromDb.get("WALLETID"))));
                users.add(user);
                parameters.clear();
            }
        } catch (SQLException e) {
            throw new DaoException();
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
        }
        return users;
    }

    public byte update(String id, Map<String, String> parameters) {
        String value = rb.getString("update-user.all");
        this.parameters.add(parameters.get("Password"));
        this.parameters.add(parameters.get("Email"));
        this.parameters.add(parameters.get("FirstName"));
        this.parameters.add(parameters.get("LastName"));
        this.parameters.add(parameters.get("PhoneNumber"));
        this.parameters.add(parameters.get("MainAddress"));
        this.parameters.add(parameters.get("Bank"));
        this.parameters.add(parameters.get("BankAccount"));
        this.parameters.add(id);
        byte result = executeStatement(con, value, this.parameters);
        this.parameters.clear();
        return result;
    }

    public byte updateWithWallet(String id, Map<String, String> parameters, String walletId) {
        String value = rb.getString("update-user.all-with-wallet");
        this.parameters.add(parameters.get("Password"));
        this.parameters.add(parameters.get("Email"));
        this.parameters.add(parameters.get("FirstName"));
        this.parameters.add(parameters.get("LastName"));
        this.parameters.add(parameters.get("PhoneNumber"));
        this.parameters.add(parameters.get("MainAddress"));
        this.parameters.add(parameters.get("Bank"));
        this.parameters.add(parameters.get("BankAccount"));
        this.parameters.add(walletId);
        this.parameters.add(id);
        byte result = executeStatement(con, value, this.parameters);
        this.parameters.clear();
        return result;
    }

    public byte updatePassEmailRole(String id, String password, String email, String role) {
        String value = rb.getString("update-user.pass-email-role");
        if (findByEmail(email) != null && (!findById(Integer.parseInt(id)).getEmail().equals(email))) return 2;
        if (password.equals("") || email.equals("")) return 3;
        parameters.add(password);
        parameters.add(email);
        parameters.add(role);
        parameters.add(id);
        byte result = executeStatement(con, value, parameters);
        parameters.clear();
        return result;
    }

    public void deleteById(String id) {
        parameters.add(id);
        String value = rb.getString("delete-user.id");
        Statement.executeStatement(con, value, parameters);
        parameters.clear();
    }

    public List<User> searchByLogin(String loginPart, Integer offset, Integer noOfRecords) {
        String value = rb.getString("search-user.login");
        parameters.add(loginPart + '%');
        parameters.add(offset.toString());
        parameters.add(noOfRecords.toString());
        PreparedStatement ps = Statement.getStatement(con, value, parameters);
        List<User> users = getUserFromDb(ps, parameters);
        parameters.clear();
        return users;
    }
}
