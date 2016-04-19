package com.epam.anuar.gorkomtrans.dao;

import com.epam.anuar.gorkomtrans.entity.Wallet;
import org.joda.money.Money;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.anuar.gorkomtrans.dao.DaoService.*;

public class WalletDao {
    private Connection con;
    private List<String> parameters = new ArrayList<>();

    public WalletDao(Connection con) {
        this.con = con;
    }

    public Wallet findById(Integer id) {
        String value = "SELECT * FROM WALLET WHERE ID = ?";
        parameters.add(id.toString());
        PreparedStatement ps = getStatement(con, value, parameters);
        parameters.clear();
        List<Wallet> wallets = getWalletFromDb(ps, parameters);
        if (wallets.size() != 0) {
            return wallets.get(0);
        } else {
            return null;
        }
    }

    private List<Wallet> getWalletFromDb(PreparedStatement ps, List<String> parameters) {
        List<Wallet> wallets = new ArrayList<>();
        Wallet wallet;
        ResultSet rs = null;
        ResultSetMetaData rsmd;
        Map<String, String> parametersFromDb = new HashMap<>();

        try {
            rs = ps.getResultSet();
            rsmd = rs.getMetaData();
            while (rs.next()) {
                for (int i = 1; i < rsmd.getColumnCount()+1; i++) {
                    parametersFromDb.put(rsmd.getColumnName(i), rs.getString(i));
                }
                wallet = new Wallet(Integer.parseInt(parametersFromDb.get("ID")), parametersFromDb.get("ACCOUNT"), Money.parse(parametersFromDb.get("MONEY")));
                wallets.add(wallet);
                parameters.clear();
            }
        } catch (SQLException e) {
            throw new DaoException();
        } finally {
            closeResultSet(rs);
            closeStatement(ps);
        }
        return wallets;
    }

    public void insert(Integer id, String account) {
        parameters.add(id.toString());
        parameters.add(account);
        String value = "INSERT INTO WALLET VALUES(?, ?, 'KZT 0.00')";
        executeStatement(con, value, parameters);
        parameters.clear();
    }
}
