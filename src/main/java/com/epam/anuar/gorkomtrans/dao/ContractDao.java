package com.epam.anuar.gorkomtrans.dao;

import com.epam.anuar.gorkomtrans.entity.Contract;
import com.epam.anuar.gorkomtrans.entity.GarbageTechSpecification;
import com.epam.anuar.gorkomtrans.entity.Status;
import org.joda.money.Money;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.*;

public class ContractDao {
    private static Logger log = LoggerFactory.getLogger(ContractDao.class.getName());
    private Connection con;
    private List<String> parameters = new ArrayList<>();
    private ResourceBundle rb = ResourceBundle.getBundle("sql");
    public static final Integer ID_QUANTITY_CONTRACTDAO = 45000;
    public static final Integer ID_SHIFT_CONTRACTDAO = 55000;

    public ContractDao(Connection con) {
        this.con = con;
    }

    public void insert(Contract contract) throws DaoException {
        parameters.add(contract.getId().toString());
        parameters.add(contract.getUser().getId().toString());
        parameters.add(contract.getGarbageTechSpecification().getId().toString());
        parameters.add(contract.getContractAmount().toString());
        parameters.add(contract.getProvidingMonthNumber().toString());
        parameters.add(contract.getContractTotalCapacityString());
        String value = rb.getString("insert.contract");
        Statement.executeStatement(con, value, parameters);
        parameters.clear();
    }

    public Contract findById(Integer id) throws DaoException {
        String value = rb.getString("find-contract.id");
        parameters.add(id.toString());
        PreparedStatement ps = Statement.getStatement(con, value, parameters);
        parameters.clear();
        List<Contract> contracts = getContractFromDb(ps, parameters);
        if (contracts.size() != 0) {
            return contracts.get(0);
        } else {
            return null;
        }
    }

    public List<Contract> searchByAddress(List<GarbageTechSpecification> techSpecs, String userId, Integer offset, Integer noOfRecords) throws DaoException {
        String value = "SELECT * FROM CONTRACT TABLE WHERE USERID = ? AND (";
        parameters.add(userId);
        for (int i = 0; i < techSpecs.size(); i++) {
            String techSpecId = techSpecs.get(i).getId().toString();
            value += "TECHSPECID = ?";
            if (i != techSpecs.size() - 1) value += " OR ";
            else value += ')';
            parameters.add(techSpecId);
        }
        value += "LIMIT ?, ?";
        parameters.add(offset.toString());
        parameters.add(noOfRecords.toString());
        PreparedStatement ps = Statement.getStatement(con, value, parameters);
        List<Contract> contracts = getContractFromDb(ps, parameters);
        parameters.clear();
        return contracts;
    }

    public List<Contract> searchByAddress(List<GarbageTechSpecification> techSpecs, Integer offset, Integer noOfRecords) throws DaoException {
        String value = "SELECT * FROM CONTRACT TABLE WHERE ";
        for (int i = 0; i < techSpecs.size(); i++) {
            String techSpecId = techSpecs.get(i).getId().toString();
            value += "TECHSPECID = ?";
            if (i != techSpecs.size() - 1) value += " OR ";
            parameters.add(techSpecId);
        }
        value += "LIMIT ?, ?";
        parameters.add(offset.toString());
        parameters.add(noOfRecords.toString());
        PreparedStatement ps = Statement.getStatement(con, value, parameters);
        List<Contract> contracts = getContractFromDb(ps, parameters);
        parameters.clear();
        return contracts;
    }

    public List<Contract> searchByStatus(String id, String status, Integer offset, Integer noOfRecords) throws DaoException {
        String value = "SELECT * FROM CONTRACT TABLE WHERE ID = ?, STATUS LIKE ? LIMIT ?, ?";
        parameters.add(id);
        parameters.add(status);
        parameters.add(offset.toString());
        parameters.add(noOfRecords.toString());
        PreparedStatement ps = Statement.getStatement(con, value, parameters);
        List<Contract> contracts = getContractFromDb(ps, parameters);
        parameters.clear();
        return contracts;
    }

    public List<Contract> findByUserId(Integer id) throws DaoException {
        String value = rb.getString("find-contract.user-id");
        parameters.add(id.toString());
        PreparedStatement ps = Statement.getStatement(con, value, parameters);
        List<Contract> contracts = getContractFromDb(ps, parameters);
        parameters.clear();
        return contracts;
    }

    public List<Contract> findByUserId(Integer id, Integer offset, Integer noOfRecords) throws DaoException {
        String value = rb.getString("find-contract.user-id-row");
        parameters.add(id.toString());
        parameters.add(offset.toString());
        parameters.add(noOfRecords.toString());
        PreparedStatement ps = Statement.getStatement(con, value, parameters);
        List<Contract> contracts = getContractFromDb(ps, parameters);
        parameters.clear();
        return contracts;
    }

    public List<Contract> findAll(Integer offset, Integer noOfRecords) throws DaoException {
        String value = rb.getString("find-contract.all");
        parameters.add(offset.toString());
        parameters.add(noOfRecords.toString());
        PreparedStatement ps = Statement.getStatement(con, value, parameters);
        List<Contract> contracts = getContractFromDb(ps, parameters);
        parameters.clear();
        return contracts;
    }

    private List<Contract> getContractFromDb(PreparedStatement ps, List<String> parameters) throws DaoException {
        List<Contract> contracts = new ArrayList<>();
        Contract contract;
        ResultSet rs = null;
        ResultSetMetaData rsmd;
        Map<String, String> parametersFromDb = new HashMap<>();
        UserDao userDao = DaoFactory.getInstance().getUserDao();
        TechSpecDao techSpecDao = DaoFactory.getInstance().getTechSpecDao();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd.MM.yyyy HH:mm");

        try {
            rs = ps.getResultSet();
            rsmd = rs.getMetaData();
            while (rs.next()) {
                contract = new Contract();
                for (int i = 1; i < rsmd.getColumnCount()+1; i++) {
                    parametersFromDb.put(rsmd.getColumnName(i), rs.getString(i));
                }
                contract.setId(Integer.parseInt(parametersFromDb.get("ID")));
                contract.setUser(userDao.findById(Integer.parseInt(parametersFromDb.get("USERID"))));
                contract.setGts(techSpecDao.findById(Integer.parseInt(parametersFromDb.get("TECHSPECID"))));
                contract.setContractAmount(Money.parse(parametersFromDb.get("AMOUNT")));
                contract.setProvidingMonthNumber(Integer.parseInt(parametersFromDb.get("TERM")));
                if (!parametersFromDb.get("SIGNDATE").equals("")) {
                    contract.setSignDate(formatter.parseDateTime(parametersFromDb.get("SIGNDATE")));
                }
                contract.setStatus(Status.valueOf(parametersFromDb.get("STATUS")));
                contract.setContractTotalCapacity(Double.parseDouble(parametersFromDb.get("TOTALCAPACITY")));
                contracts.add(contract);
                parameters.clear();
            }
        } catch (SQLException e) {
            throw new DaoException();
        } finally {
            Statement.closeResultSet(rs);
            Statement.closeStatement(ps);
        }
        return contracts;
    }

    public void update(Integer id, String signDate, Status status) throws DaoException {
        String value = rb.getString("update-contract.signdate-status");
        parameters.add(signDate);
        parameters.add(status.toString());
        parameters.add(id.toString());
        Statement.executeStatement(con, value, parameters);
        parameters.clear();
    }

    public void updateStatus(Integer id, Status status) throws DaoException {
        String value = rb.getString("update-contract.status");
        parameters.add(status.toString());
        parameters.add(id.toString());
        Statement.executeStatement(con, value, parameters);
        parameters.clear();
    }

    public void deleteById(String id) throws DaoException {
        parameters.add(id);
        String value = rb.getString("delete-contract.id");
        Statement.executeStatement(con, value, parameters);
        parameters.clear();
    }

    public void deleteByUserId(String id) throws DaoException {
        parameters.add(id);
        String value = rb.getString("delete-contract.user-id");
        Statement.executeStatement(con, value, parameters);
        parameters.clear();
    }

    public synchronized byte transfer(String summa, String customerWalletId, String providerWalletId) throws DaoException {
        byte result = 0;
        try {
            java.sql.Statement st = null;
            try {
                con.setAutoCommit(false);
                Money sum = Money.parse(summa);
                if (sum.isLessThan(Money.parse("KZT 0"))) {
                    throw new DaoException("less or equals zero");
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
                    throw new DaoException("Customer has not enough money");
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
