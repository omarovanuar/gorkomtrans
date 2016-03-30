package com.epam.anuar.gorkomtrans.dao;

import com.epam.anuar.gorkomtrans.entity.User;

import java.sql.*;
import java.util.List;

public class UserDao {
    private Connection con;

    public UserDao(Connection con) {
        this.con = con;
    }

    public void insert(User user) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Integer id = user.getId();
        String login = user.getLogin();
        String password = user.getPassword();
        String email = user.getEmail();
        String value = "INSERT INTO USER VALUES(" + id + ", " + "'" + login + "'" + ", " + "'" + password + "'" + ", " + "'" + email + "');";
        try {
            ps = con.prepareStatement(value);
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (rs != null) rs.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
        }
    }

    public void save(List<User> userList) {

    }
}
