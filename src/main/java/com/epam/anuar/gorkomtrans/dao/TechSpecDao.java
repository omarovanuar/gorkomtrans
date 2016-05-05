package com.epam.anuar.gorkomtrans.dao;

import com.epam.anuar.gorkomtrans.entity.GarbageContainerType;
import com.epam.anuar.gorkomtrans.entity.GarbageTechSpecification;

import java.sql.*;
import java.util.*;

public class TechSpecDao {
    private Connection con;
    private List<String> parameters = new ArrayList<>();
    private ResourceBundle rb = ResourceBundle.getBundle("sql");
    public static final Integer ID_QUANTITY_TECHSPECDAO = 45000;
    public static final Integer ID_SHIFT_TECHSPECDAO = 10000;


    public TechSpecDao(Connection con) {
        this.con = con;
    }

    public void insert(GarbageTechSpecification techSpec) {
        parameters.add(techSpec.getId().toString());
        parameters.add(techSpec.getAddress());
        addContainer(techSpec, parameters, "EURO", 0);
        addContainer(techSpec, parameters, "STANDARD", 0);
        addContainer(techSpec, parameters, "NON_STANDARD0", 0);
        addContainer(techSpec, parameters, "NON_STANDARD0", 1);
        addContainer(techSpec, parameters, "NON_STANDARD2", 0);
        addContainer(techSpec, parameters, "NON_STANDARD2", 1);
        addContainer(techSpec, parameters, "NON_STANDARD4", 0);
        addContainer(techSpec, parameters, "NON_STANDARD4", 1);
        parameters.add(techSpec.getRemovePerMonth().toString());
        String value = rb.getString("insert.techspec");
        Statement.executeStatement(con, value, parameters);
        parameters.clear();
    }

    public GarbageTechSpecification findById(Integer id) {
        String value = rb.getString("find-techspec.id");
        parameters.add(id.toString());
        PreparedStatement ps = Statement.getStatement(con, value, parameters);
        parameters.clear();
        List<GarbageTechSpecification> techSpecs = getTechSpecFromDb(ps, parameters);
        if (techSpecs.size() != 0) {
            return techSpecs.get(0);
        } else {
            return null;
        }
    }

    public List<GarbageTechSpecification> searchByAddress(String addressPart) {
        String value = rb.getString("search-techspec.address");
        parameters.add(addressPart + '%');
        PreparedStatement ps = Statement.getStatement(con, value, parameters);
        List<GarbageTechSpecification> techSpecs = getTechSpecFromDb(ps, parameters);
        parameters.clear();
        return techSpecs;
    }

    public void deleteById(String id) {
        parameters.add(id);
        String value = rb.getString("delete-techspec.id");
        Statement.executeStatement(con, value, parameters);
        parameters.clear();
    }

    private List<GarbageTechSpecification> getTechSpecFromDb(PreparedStatement ps, List<String> parameters) {
        List<GarbageTechSpecification> techSpecs = new ArrayList<>();
        GarbageTechSpecification techSpec;
        ResultSet rs = null;
        ResultSetMetaData rsmd;
        Map<String, String> parametersFromDb = new HashMap<>();
        Map<String, List<String>> garbageContainerParameters;

        try {
            rs = ps.getResultSet();
            rsmd = rs.getMetaData();
            while (rs.next()) {
                techSpec = new GarbageTechSpecification();
                for (int i = 1; i < rsmd.getColumnCount() + 1; i++) {
                    parametersFromDb.put(rsmd.getColumnName(i), rs.getString(i));
                }
                techSpec.setId(Integer.parseInt(parametersFromDb.get("ID")));
                techSpec.setAddress(parametersFromDb.get("ADDRESS"));
                techSpec.setRemovePerMonth(Integer.parseInt(parametersFromDb.get("PERMONTH")));
                garbageContainerParameters = calculateMapFromDb(parametersFromDb);
                techSpec.setGarbageContainerParameters(garbageContainerParameters);
                techSpecs.add(techSpec);
                parameters.clear();
            }
        } catch (SQLException e) {
            throw new DaoException();
        } finally {
            Statement.closeResultSet(rs);
            Statement.closeStatement(ps);
        }
        return techSpecs;
    }

    private Map<String, List<String>> calculateMapFromDb(Map<String, String> parametersFromDb) {
        Map<String, List<String>> garbageContainerParameters = new HashMap<>();
        List<String> numberAndCapacity;
        numberAndCapacity = new ArrayList<>();
        numberAndCapacity.add(parametersFromDb.get("EURONUMBER"));
        numberAndCapacity.add(GarbageContainerType.EURO.getContainerCapacity().toString());
        garbageContainerParameters.put(GarbageContainerType.EURO.toString(), numberAndCapacity);
        numberAndCapacity = new ArrayList<>();
        numberAndCapacity.add(parametersFromDb.get("STANDARDNUMBER"));
        numberAndCapacity.add(GarbageContainerType.STANDARD.getContainerCapacity().toString());
        garbageContainerParameters.put(GarbageContainerType.STANDARD.toString(), numberAndCapacity);
        numberAndCapacity = new ArrayList<>();
        numberAndCapacity.add(parametersFromDb.get("NONSTANDARDNUM1"));
        numberAndCapacity.add(parametersFromDb.get("NONSTANDARDCAPAS1"));
        garbageContainerParameters.put(GarbageContainerType.STANDARD.toString() + "0", numberAndCapacity);
        numberAndCapacity = new ArrayList<>();
        numberAndCapacity.add(parametersFromDb.get("NONSTANDARDNUM2"));
        numberAndCapacity.add(parametersFromDb.get("NONSTANDARDCAPAS2"));
        garbageContainerParameters.put(GarbageContainerType.STANDARD.toString() + "2", numberAndCapacity);
        numberAndCapacity = new ArrayList<>();
        numberAndCapacity.add(parametersFromDb.get("NONSTANDARDNUM3"));
        numberAndCapacity.add(parametersFromDb.get("NONSTANDARDCAPAS3"));
        garbageContainerParameters.put(GarbageContainerType.STANDARD.toString() + "4", numberAndCapacity);
        return garbageContainerParameters;
    }

    private void addContainer(GarbageTechSpecification techSpec, List<String> parameters, String container, Integer numberOrCapacity) {
        if (techSpec.getGarbageContainerParameters().get(container).get(numberOrCapacity) != null) {
            parameters.add(techSpec.getGarbageContainerParameters().get(container).get(numberOrCapacity));
        } else {
            parameters.add("0");
        }
    }
}
