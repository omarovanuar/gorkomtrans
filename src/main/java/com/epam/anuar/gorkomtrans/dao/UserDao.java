package com.epam.anuar.gorkomtrans.dao;

import com.epam.anuar.gorkomtrans.entity.User;
import com.epam.anuar.gorkomtrans.entity.Wallet;

import java.sql.*;
import java.util.*;

import static com.epam.anuar.gorkomtrans.dao.DaoService.*;

public class UserDao {
    private Connection con;
    private List<String> parameters = new ArrayList<>();
    private ResourceBundle rb = ResourceBundle.getBundle("user-sql");

    public UserDao(Connection con) {
        this.con = con;
    }

    public void insert(User user) {
        parameters.add(user.getId().toString());
        parameters.add(user.getLogin());
        parameters.add(user.getPassword());
        parameters.add(user.getEmail());
        parameters.add(user.getFirstName());
        parameters.add(user.getLastName());
        parameters.add(user.getPhoneNumber());
        parameters.add(user.getMainAddress());
        parameters.add(user.getBankName());
        parameters.add(user.getBankAccount());
        String value = rb.getString("insert.user");
        executeStatement(con, value, parameters);
        parameters.clear();
    }

    public byte insert(String login, String password, String email) {
        Random random = new Random();
        Integer id = random.nextInt(10000);
        if (findById(id) != null) {
           insert(login, password, email);
        }
        if (findByLogin(login) != null) return 1;
        if (findByEmail(email) != null) return 2;
        if (login.equals("") || password.equals("") || email.equals("")) return 3;
        parameters.add(id.toString());
        parameters.add(login);
        parameters.add(password);
        parameters.add(email);
        String value = rb.getString("insert.login-pass-email");
        byte result = executeStatement(con, value, parameters);
        parameters.clear();
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
        String value = rb.getString("insert.parameters");
        byte result = executeStatement(con, value, this.parameters);
        this.parameters.clear();
        return result;
    }

    public void save(List<User> userList) {
        userList.forEach(this::insert);
    }

    public User findById(Integer id) {
        String value = rb.getString("find.id");
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
        String value = rb.getString("find.login");
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
        String value = rb.getString("find.email");
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
        String value = rb.getString("find.login-pass");
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

    public User findByWalletId(Integer walletId) {
        String value = rb.getString("find.wallet");
        parameters.add(walletId.toString());
        PreparedStatement ps = getStatement(con, value, parameters);
        parameters.clear();
        List<User> users = getUserFromDb(ps, parameters);
        if (users.size() != 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

    //todo unfinished SQL
    public User findByAllParameters(Map<String, String> params) {
        String value = "SELECT * FROM USER WHERE ID = " + params.get("ID") +
                " AND LOGIN = '" + params.get("LOGIN") +
                "' AND PASSWORD = '" + params.get("PASSWORD") + "'";
        PreparedStatement ps = getStatement(con, value, parameters);
        List<User> users = getUserFromDb(ps, parameters);
        if (users.size() != 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

    public List<User> findAll(Integer offset, Integer noOfRecords) {
        String value = "SELECT * FROM USER TABLE LIMIT ?, ?";
        parameters.add(offset.toString());
        parameters.add(noOfRecords.toString());
        PreparedStatement ps = DaoService.getStatement(con, value, parameters);
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
                for (int i = 1; i < rsmd.getColumnCount()+1; i++) {
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


//    public void update(User user) {
//        Integer id = user.getId();
//        String login = user.getLogin();
//        String password = user.getPassword();
//        String email = user.getEmail();
//        String value = "UPDATE USER SET LOGIN='" + login + "', PASSWORD='" + password + "', EMAIL='" + email + "' WHERE ID=" + id;
//        DaoService.executeStatement(con, value);
//    }

//    public byte update(String id, String password, String email) {
//        String value = "UPDATE USER SET PASSWORD='" + password + "', EMAIL='" + email + "' WHERE ID=" + id;
//        if (findByEmail(email) != null && (!findById(Integer.parseInt(id)).getEmail().equals(email))) return 2;
//        if (password.equals("") || email.equals("")) return 3;
//        return DaoService.executeStatement(con, value);
//    }

    public byte update(String id, Map<String, String> parameters) {
        String value = rb.getString("update.all");
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
        String value = rb.getString("update.all-with-wallet");
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
        String value = rb.getString("update.pass-email-role");
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
//
//    public void update(List<User> userList) {
//        userList.forEach(this::update);
//    }
//
    public void deleteById(String id) {
        parameters.add(id);
        String value = "DELETE FROM USER WHERE ID = ?";
        DaoService.executeStatement(con, value, parameters);
        parameters.clear();
    }
//
//    public void delete(List<User> userList) {
//        userList.forEach(this::delete);
//    }

    public int allRowsNumber() {
        String value = "SELECT ROWNUM(), * FROM USER";
        return DaoService.calculateRowNumber(value, con);
    }



}
