package com.epam.anuar.gorkomtrans.dao;

import com.epam.anuar.gorkomtrans.entity.User;

import java.sql.*;
import java.util.*;

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
        DaoService.executeStatement(con, value, parameters);
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
        byte result = DaoService.executeStatement(con, value, parameters);
        parameters.clear();
        return result;
    }

    public void save(List<User> userList) {
        userList.forEach(this::insert);
    }

    public User findById(Integer id) {
        String value = rb.getString("find.id");
        parameters.add(id.toString());
        PreparedStatement ps = DaoService.getStatement(con, value, parameters);
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
        PreparedStatement ps = DaoService.getStatement(con, value, parameters);
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
        PreparedStatement ps = DaoService.getStatement(con, value, parameters);
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
        PreparedStatement ps = DaoService.getStatement(con, value, parameters);
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
        PreparedStatement ps = DaoService.getStatement(con, value, parameters);
        List<User> users = getUserFromDb(ps, parameters);
        if (users.size() != 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

    //todo unfinished SQL
    public List<User> findAll() {
        String value = "SELECT * FROM USER";
        PreparedStatement ps = DaoService.getStatement(con, value, parameters);
        List<User> users = getUserFromDb(ps, parameters);
        if (users.size() != 0) {
            return users;
        } else {
            return null;
        }

    }

    private List<User> getUserFromDb(PreparedStatement ps, List<String> parameters) {
        List<User> users = new ArrayList<>();
        User user;
        ResultSet rs = null;
        ResultSetMetaData rsmd;
        Map<String, String> parametersFromDb = new HashMap<>();

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
                users.add(user);
                parameters.clear();
            }
        } catch (SQLException e) {
            throw new DaoException();
        } finally {
            DaoService.closeResultSet(rs);
            DaoService.closeStatement(ps);
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

    public byte update(String id, String password, String email, String firstName, String lastName, String phoneNumber, String mainAddress,
                       String bank, String bankAccount) {
        String value = rb.getString("update.all");
        if (findByEmail(email) != null && (!findById(Integer.parseInt(id)).getEmail().equals(email))) return 2;
        if (password.equals("") || email.equals("")) return 3;
        parameters.add(password);
        parameters.add(email);
        parameters.add(firstName);
        parameters.add(lastName);
        parameters.add(phoneNumber);
        parameters.add(mainAddress);
        parameters.add(bank);
        parameters.add(bankAccount);
        parameters.add(id);
        byte result = DaoService.executeStatement(con, value, parameters);
        parameters.clear();
        return result;
    }
//
//    public void update(List<User> userList) {
//        userList.forEach(this::update);
//    }
//
//    public void delete(User user) {
//        Integer id = user.getId();
//        String login = user.getLogin();
//        String email = user.getEmail();
//        String value = "DELETE FROM USER WHERE LOGIN='" + login + "' OR EMAIL='" + email + "' OR ID=" + id;
//        DaoService.executeStatement(con, value);
//    }
//
//    public void delete(List<User> userList) {
//        userList.forEach(this::delete);
//    }

}
