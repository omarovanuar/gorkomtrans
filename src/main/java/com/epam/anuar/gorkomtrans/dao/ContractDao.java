package com.epam.anuar.gorkomtrans.dao;

import com.epam.anuar.gorkomtrans.entity.Contract;
import org.joda.money.Money;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContractDao {
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
        String value = "INSERT INTO CONTRACT VALUES (?, ?, ?, ?, ?, '', FALSE, ?)";
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
        parameters.clear();
        return getContractFromDb(ps, parameters);
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
                contract.setSanctioned(Boolean.getBoolean(parametersFromDb.get("SANCTIONED")));
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

    public void update(Integer id, String signdate) {
        String value = "UPDATE CONTRACT SET SIGNDATE = ? WHERE ID = ?";
        parameters.add(signdate);
        parameters.add(id.toString());
        DaoService.executeStatement(con, value, parameters);
        parameters.clear();
    }
}
