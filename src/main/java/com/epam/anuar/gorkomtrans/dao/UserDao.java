package com.epam.anuar.gorkomtrans.dao;

import com.epam.anuar.gorkomtrans.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class UserDao {
    private Connection con;

    public UserDao(Connection con) {
        this.con = con;
    }

    public void insert(User user) {
        Integer id = user.getId();
        String login = user.getLogin();
        String password = user.getPassword();
        String email = user.getEmail();
        String value = "INSERT INTO USER VALUES(" + id + ", " + "'" + login + "'" + ", " + "'" + password + "'" + ", " + "'" + email + "');";
        DaoService.executeStatement(con, value);
    }

    public byte insert(String login, String password, String email) {
        Random random = new Random();
        Integer id = random.nextInt(10000);
        if (findById(id) != null) {
           insert(login, password, email);
        }
        if (findByLogin(login) != null) return 1;
        if (findByEmail(email) != null) return 2;
        String value = "INSERT INTO USER VALUES(" + id + ", " + "'" + login + "'" + ", " + "'" + password + "'" + ", " + "'" + email + "');";
        return DaoService.executeStatement(con, value);
    }

    public void save(List<User> userList) {
        userList.forEach(this::insert);
    }

    public User findById(Integer id) {
        String value = "SELECT * FROM USER WHERE ID = " + id;
        PreparedStatement ps = DaoService.getStatement(con, value);
        List<User> users = getUserFromDb(ps);
        if (users.size() != 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

    public User findByLogin(String login) {
        String value = "SELECT * FROM USER WHERE LOGIN = '" + login + "'";
        PreparedStatement ps = DaoService.getStatement(con, value);
        List<User> users = getUserFromDb(ps);
        if (users.size() != 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

    public User findByEmail(String email) {
        String value = "SELECT * FROM USER WHERE EMAIL = '" + email + "'";
        PreparedStatement ps = DaoService.getStatement(con, value);
        List<User> users = getUserFromDb(ps);
        if (users.size() != 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

    public User findByAllParameters(Map<String, String> params) {
        String value = "SELECT * FROM USER WHERE ID = " + params.get("ID") +
                " AND LOGIN = '" + params.get("LOGIN") +
                "' AND PASSWORD = '" + params.get("PASSWORD") + "'";
        PreparedStatement ps = DaoService.getStatement(con, value);
        List<User> users = getUserFromDb(ps);
        if (users.size() != 0) {
            return users.get(0);
        } else {
            return null;
        }
    }

    public List<User> findAll() {
        String value = "SELECT * FROM USER";
        PreparedStatement ps = DaoService.getStatement(con, value);
        List<User> users = getUserFromDb(ps);
        if (users.size() != 0) {
            return users;
        } else {
            return null;
        }

    }

    private List<User> getUserFromDb(PreparedStatement ps) {
        List<User> users = new ArrayList<>();
        User user;
        ResultSet rs = null;
        String login;
        String password;
        String email;
        Integer id;

        try {
            rs = ps.getResultSet();
            while (rs.next()) {
                user = new User();
                id = rs.getInt("ID");
                login = rs.getString("LOGIN");
                password = rs.getString("PASSWORD");
                email = rs.getString("EMAIL");
                user.setId(id);
                user.setLogin(login);
                user.setPassword(password);
                user.setEmail(email);
                users.add(user);
            }
        } catch (SQLException e) {
            throw new DaoException();
        } finally {
            DaoService.closeResultSet(rs);
            DaoService.closeStatement(ps);
        }
        return users;
    }


    public void update(User user) {
        Integer id = user.getId();
        String login = user.getLogin();
        String password = user.getPassword();
        String email = user.getEmail();
        String value = "UPDATE USER SET LOGIN='" + login + "', PASSWORD='" + password + "', EMAIL='" + email + "' WHERE ID=" + id;
        DaoService.executeStatement(con, value);
    }

    public void update(List<User> userList) {
        userList.forEach(this::update);
    }

    public void delete(User user) {
        Integer id = user.getId();
        String login = user.getLogin();
        String email = user.getEmail();
        String value = "DELETE FROM USER WHERE LOGIN='" + login + "' OR EMAIL='" + email + "' OR ID=" + id;
        DaoService.executeStatement(con, value);
    }

    public void delete(List<User> userList) {
        userList.forEach(this::delete);
    }

    public User findByCredentials(String login, String password) {
        String value = "SELECT * FROM USER WHERE LOGIN = '" + login + "' AND PASSWORD='" + password + "'";
        PreparedStatement ps = DaoService.getStatement(con, value);
        List<User> users = getUserFromDb(ps);
        if (users.size() != 0) {
            return users.get(0);
        } else {
            return null;
        }
    }
}
