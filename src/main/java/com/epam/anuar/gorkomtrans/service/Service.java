package com.epam.anuar.gorkomtrans.service;

import com.epam.anuar.gorkomtrans.entity.Contract;
import com.epam.anuar.gorkomtrans.entity.GarbageContainerType;
import com.epam.anuar.gorkomtrans.entity.User;

import java.util.*;

public class Service {

    public static final Comparator<User> USER_ID_COMPARATOR = (o1, o2) -> o1.getId().compareTo(o2.getId());
    public static final Comparator<Contract> CONTRACT_ID_COMPARATOR = (o1, o2) -> o1.getId().compareTo(o2.getId());

    public static Map<String, List<String>> createGarbageContainerParameters(String euroNumber, String standardNumber, List<String> parameters) {
        Map<String, List<String>> techSpecParameters = new HashMap<>();
        GarbageContainerType tempType = GarbageContainerType.EURO;
        techSpecParameters = fillMapParameters(tempType.toString(), euroNumber, tempType.getContainerCapacity().toString(), techSpecParameters);
        tempType = GarbageContainerType.STANDARD;
        techSpecParameters = fillMapParameters(tempType.toString(), standardNumber, tempType.getContainerCapacity().toString(), techSpecParameters);
        for (Integer i = 0; i < parameters.size(); i += 2) {
            tempType = GarbageContainerType.NON_STANDARD;
            techSpecParameters = fillMapParameters(tempType.toString() + i.toString(), parameters.get(i), parameters.get(i+1), techSpecParameters);
        }
        return techSpecParameters;
    }

    private static Map<String, List<String>> fillMapParameters(String typeString, String containerNumber, String containerCapacity,  Map<String, List<String>> techSpecParameters) {
        List<String> numberAndCapacity = new ArrayList<>();
        numberAndCapacity.add(containerNumber);
        numberAndCapacity.add(containerCapacity);
        techSpecParameters.put(typeString, numberAndCapacity);
        return techSpecParameters;
    }
}
