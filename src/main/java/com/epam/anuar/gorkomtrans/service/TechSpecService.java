package com.epam.anuar.gorkomtrans.service;

import com.epam.anuar.gorkomtrans.dao.DaoException;
import com.epam.anuar.gorkomtrans.dao.DaoFactory;
import com.epam.anuar.gorkomtrans.dao.TechSpecDao;
import com.epam.anuar.gorkomtrans.entity.GarbageContainerType;
import com.epam.anuar.gorkomtrans.entity.GarbageTechSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.epam.anuar.gorkomtrans.util.IdGenerator.generateID;

public class TechSpecService {
    private final static Logger log = LoggerFactory.getLogger(TechSpecService.class);
    private DaoFactory dao;
    private TechSpecDao techSpecDao;

    public TechSpecService() {
        dao = DaoFactory.getInstance();
        techSpecDao = dao.getTechSpecDao();
    }

    public GarbageTechSpecification getNewTechSpec(String address, Map<String, List<String>> garbageParameters, String perMonth) throws ServiceException {
        Integer id = generateID(techSpecDao);
        GarbageTechSpecification techSpecification = new GarbageTechSpecification(id, address, garbageParameters, Integer.parseInt(perMonth));
        try {
            techSpecDao.insert(techSpecification);
        } catch (DaoException e) {
            log.warn("Can't insert new technical specification");
            throw new ServiceException();
        } finally {
            dao.close();
        }
        return techSpecification;
    }

    public List<GarbageTechSpecification> getTechSpecByAddressPart(String addressPart) throws ServiceException {
        List<GarbageTechSpecification> techSpecs;
        try {
            techSpecs = techSpecDao.searchByAddress(addressPart);
        } catch (DaoException e) {
            log.warn("Can't search technical specification by address");
            throw new ServiceException();
        } finally {
            dao.close();
        }
        return techSpecs;
    }

    public Map<String, List<String>> createGarbageContainerParameters(String euroNumber, String standardNumber, List<String> parameters) {
        Map<String, List<String>> techSpecParameters = new HashMap<>();
        GarbageContainerType tempType = GarbageContainerType.EURO;
        techSpecParameters = fillParameters(tempType.toString(), euroNumber, tempType.getContainerCapacity().toString(), techSpecParameters);
        tempType = GarbageContainerType.STANDARD;
        techSpecParameters = fillParameters(tempType.toString(), standardNumber, tempType.getContainerCapacity().toString(), techSpecParameters);
        for (Integer i = 0; i < parameters.size(); i += 2) {
            tempType = GarbageContainerType.NON_STANDARD;
            techSpecParameters = fillParameters(tempType.toString() + i.toString(), parameters.get(i), parameters.get(i+1), techSpecParameters);
        }
        return techSpecParameters;
    }

    private Map<String, List<String>> fillParameters(String typeString, String containerNumber, String containerCapacity, Map<String, List<String>> techSpecParameters) {
        List<String> numberAndCapacity = new ArrayList<>();
        numberAndCapacity.add(containerNumber);
        numberAndCapacity.add(containerCapacity);
        techSpecParameters.put(typeString, numberAndCapacity);
        return techSpecParameters;
    }

    public void deleteTechSpecById(String id) throws ServiceException {
        try {
            techSpecDao.deleteById(id);
        } catch (DaoException e) {
            log.warn("Can't delete technical specification by id");
            throw new ServiceException();
        } finally {
            dao.close();
        }
    }
}
