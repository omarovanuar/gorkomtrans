package com.epam.anuar.gorkomtrans.dao;

import com.epam.anuar.gorkomtrans.entity.Contract;
import com.epam.anuar.gorkomtrans.entity.Status;
import org.joda.money.Money;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContractDao {
    private static Logger log = LoggerFactory.getLogger(ContractDao.class.getName());
    private Connection con;
    private List<String> parameters = new ArrayList<>();

    public ContractDao(Connection con) {
        this.con = con;
    }

    public void insert(Contract contract) {
        parameters.add(contract.getId().toString());
        parameters.add(contract.getUser().getId().toString());
        parameters.add(contract.getGarbageTechSpecification().getId().toString());
        parameters.add(contract.getContractAmount().toString());
        parameters.add(contract.getProvidingMonthNumber().toString());
        parameters.add(contract.getContractTotalCapacityString());
        String value = "INSERT INTO CONTRACT VALUES (?, ?, ?, ?, ?, '', 'NEW', ?)";
        DaoService.executeStatement(con, value, parameters);
        parameters.clear();
    }

    public Contract findById(Integer id) {
        String value = "SELECT * FROM CONTRACT TABLE WHERE ID = ?";
        parameters.add(id.toString());
        PreparedStatement ps = DaoService.getStatement(con, value, parameters);
        parameters.clear();
        List<Contract> contracts = getContractFromDb(ps, parameters);
        if (contracts.size() != 0) {
            return contracts.get(0);
        } else {
            return null;
        }
    }

    public List<Contract> findByUserId(Integer id) {
        String value = "SELECT * FROM CONTRACT TABLE WHERE USERID = ?";
        parameters.add(id.toString());
        PreparedStatement ps = DaoService.getStatement(con, value, parameters);
        List<Contract> contracts = getContractFromDb(ps, parameters);
        parameters.clear();
        return contracts;
    }

    public List<Contract> findByUserId(Integer id, Integer offset, Integer noOfRecords) {
        String value = "SELECT * FROM CONTRACT TABLE WHERE USERID = ? LIMIT ?, ?";
        parameters.add(id.toString());
        parameters.add(offset.toString());
        parameters.add(noOfRecords.toString());
        PreparedStatement ps = DaoService.getStatement(con, value, parameters);
        List<Contract> contracts = getContractFromDb(ps, parameters);
        parameters.clear();
        return contracts;
    }

    public List<Contract> findAll(Integer offset, Integer noOfRecords) {
        String value = "SELECT * FROM CONTRACT TABLE LIMIT ?, ?";
        parameters.add(offset.toString());
        parameters.add(noOfRecords.toString());
        PreparedStatement ps = DaoService.getStatement(con, value, parameters);
        List<Contract> contracts = getContractFromDb(ps, parameters);
        parameters.clear();
        return contracts;
    }

    public int userRowsNumber(String id) {
        String value = "SELECT ROWNUM(), * FROM CONTRACT WHERE ID = " + id;
        return DaoService.calculateRowNumber(value, con);
    }

    public int allRowsNumber() {
        String value = "SELECT ROWNUM(), * FROM CONTRACT";
        return DaoService.calculateRowNumber(value, con);
    }

    private List<Contract> getContractFromDb(PreparedStatement ps, List<String> parameters) {
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
            DaoService.closeResultSet(rs);
            DaoService.closeStatement(ps);
        }
        return contracts;
    }

    public void update(Integer id, String signDate, Status status) {
        String value = "UPDATE CONTRACT SET SIGNDATE = ?, STATUS = ? WHERE ID = ?";
        parameters.add(signDate);
        parameters.add(status.toString());
        parameters.add(id.toString());
        DaoService.executeStatement(con, value, parameters);
        parameters.clear();
    }

    public void updateStatus(Integer id, Status status) {
        String value = "UPDATE CONTRACT SET STATUS = ? WHERE ID = ?";
        parameters.add(status.toString());
        parameters.add(id.toString());
        DaoService.executeStatement(con, value, parameters);
        parameters.clear();
    }

    public void deleteByUserId(String id) {
        parameters.add(id);
        String value = "DELETE FROM CONTRACT WHERE USERID = ?";
        DaoService.executeStatement(con, value, parameters);
        parameters.clear();
    }
}
