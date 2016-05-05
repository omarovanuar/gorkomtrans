package com.epam.anuar.gorkomtrans.dao;

import com.epam.anuar.gorkomtrans.entity.Wallet;
import org.joda.money.Money;

import java.sql.*;
import java.util.*;

import static com.epam.anuar.gorkomtrans.dao.Statement.*;

public class WalletDao {
    private Connection con;
    private List<String> parameters = new ArrayList<>();
    private ResourceBundle rb = ResourceBundle.getBundle("sql");
    public static final Integer ID_QUANTITY_WALLETDAO = 20000;
    public static final Integer ID_SHIFT_WALLETDAO = 100000;

    public WalletDao(Connection con) {
        this.con = con;
    }

    public Wallet findById(Integer id) {
        String value = rb.getString("find-wallet.id");
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

    public Wallet findByAccount(String account) {
        String value = rb.getString("find-wallet.account");
        parameters.add(account);
        PreparedStatement ps = getStatement(con, value, parameters);
        parameters.clear();
        List<Wallet> wallets = getWalletFromDb(ps, parameters);
        if (wallets.size() != 0) {
            return wallets.get(0);
        } else {
            return null;
        }
    }

    public void insert(Integer id, String account) {
        parameters.add(id.toString());
        parameters.add(account);
        String value = rb.getString("insert.wallet");
        executeStatement(con, value, parameters);
        parameters.clear();
    }

    public byte updateBalance(String id, String money) {
        String value = rb.getString("update-wallet.balance");
        if (Double.parseDouble(money) < 0) return 4;
        parameters.add("KZT " + money);
        parameters.add(id);
        byte result = executeStatement(con, value, parameters);
        parameters.clear();
        return result;
    }

    public void deleteById(String id) {
        parameters.add(id);
        String value = rb.getString("delete-wallet.id");
        Statement.executeStatement(con, value, parameters);
        parameters.clear();
    }

    public void deleteByAccount(String account) {
        parameters.add(account);
        String value = rb.getString("delete-wallet.account");
        Statement.executeStatement(con, value, parameters);
        parameters.clear();
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
                for (int i = 1; i < rsmd.getColumnCount() + 1; i++) {
                    parametersFromDb.put(rsmd.getColumnName(i), rs.getString(i));
                }
                wallet = new Wallet(Integer.parseInt(parametersFromDb.get("ID")), parametersFromDb.get("ACCOUNT"),
                        Money.parse(parametersFromDb.get("MONEY")));
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


}
