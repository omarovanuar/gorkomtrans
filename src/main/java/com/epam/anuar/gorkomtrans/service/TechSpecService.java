package com.epam.anuar.gorkomtrans.service;

import com.epam.anuar.gorkomtrans.action.ActionResult;
import com.epam.anuar.gorkomtrans.dao.DaoFactory;
import com.epam.anuar.gorkomtrans.dao.TechSpecDao;
import com.epam.anuar.gorkomtrans.entity.GarbageTechSpecification;
import com.epam.anuar.gorkomtrans.util.Validator;
import com.epam.anuar.gorkomtrans.util.Violation;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static com.epam.anuar.gorkomtrans.util.IdGenerator.generateID;

public class TechSpecService {
    private static DaoFactory dao = DaoFactory.getInstance();

    public static ActionResult fillTechSpec(String euro, String standard, String nonStandardNumber, HttpServletRequest req) {
        Validator.checkUnlogged(req);
        Violation emptyTechSpec = Validator.isEmptyTechSpec(euro, standard, nonStandardNumber, req);
        if (emptyTechSpec != null) {
            req.setAttribute("violation", emptyTechSpec.getViolation());
            return new ActionResult("services");
        }
        if (euro == null) euro = "0";
        if (standard == null) standard = "0";
        req.setAttribute("euro", euro);
        req.setAttribute("standard", standard);
        req.setAttribute("nonStandardNumber", nonStandardNumber);
        return new ActionResult("tech-spec");
    }

    public static GarbageTechSpecification createTechSpec(String address, String euroNumber, String standardNumber,
                                                          List<String> parameters, String perMonth, HttpServletRequest req) {
        Validator.checkUnlogged(req);
        TechSpecDao techSpecDao = dao.getTechSpecDao();
        Integer id = generateID(techSpecDao);
        Map<String, List<String>> garbageParameters = Service.createGarbageContainerParameters(euroNumber, standardNumber, parameters);
        GarbageTechSpecification techSpecification = new GarbageTechSpecification(id, address, garbageParameters, Integer.parseInt(perMonth));
        techSpecDao.insert(techSpecification);
        dao.close();
        return techSpecification;
    }
}
