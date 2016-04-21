package com.epam.anuar.gorkomtrans.dao;

import org.joda.money.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ContractPayTransaction {
    private static Logger log = LoggerFactory.getLogger(ContractPayTransaction.class.getName());
    private Connection con;

    public ContractPayTransaction(Connection con) {
        this.con = con;
    }

    public synchronized byte transfer(String summa, String customerWalletId, String providerWalletId) {
        byte result = 0;
        try {
            Statement st = null;
            try {
                con.setAutoCommit(false);
                Money sum = Money.parse(summa);
                if (sum.isLessThan(Money.parse("KZT 0"))) {
                    throw new NumberFormatException("less or equals zero");
                }
                st = con.createStatement();
                ResultSet rsFrom = st.executeQuery("SELECT MONEY FROM WALLET WHERE ID = " + customerWalletId);
                Money accountFrom = Money.parse("KZT 0");
                while (rsFrom.next()) {
                    accountFrom = Money.parse(rsFrom.getString(1));
                }
                Money resultFrom;
                if (accountFrom.isGreaterThan(sum)) {
                    resultFrom = accountFrom.minus(sum);
                } else {
                    throw new SQLException("Invalid balance");
                }
                st.executeUpdate("UPDATE WALLET SET MONEY = '" + resultFrom.toString() + "' WHERE ID = " + customerWalletId);
                ResultSet rsTo = st.executeQuery("SELECT MONEY FROM WALLET WHERE ID = " + providerWalletId);
                Money accountTo = Money.parse("KZT 0");
                while (rsTo.next()) {
                    accountTo = Money.parse(rsTo.getString(1));
                }
                Money resultTo = accountTo.plus(sum);
                st.executeUpdate("UPDATE WALLET SET MONEY = '" + resultTo.toString() + "' WHERE ID = " + providerWalletId);
                con.commit();
                result = 1;
            } catch (SQLException e) {
                log.warn("SQLState: " + e.getSQLState() + "Error Message: " + e.getMessage());
                this.con.rollback();
            } catch (NumberFormatException e) {
                log.warn("Invalid summa: " + summa);
            } finally {
                if (st != null) {
                    try {
                        st.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
                this.con.setAutoCommit(true);
            }
        } catch (SQLException e) {
            log.warn("transaction failed");
        }
        return result;
    }


}
