package com.opa.camunda.poc.util;

import java.util.HashMap;
import java.util.Map;

public class HwfUtil {

    private static Map<String, String> userLevelMap = new HashMap<String, String>(){
        {
            put("", "Manager");
            put("Vivek", "Employee");
            put("Atul", "Employee");
            put("Ravi", "Manager");
            put("Rajiv", "SrManager");
            put("Lary", "CEO");
        }
    };
    public static String getUserLevel(String user){
        if(userLevelMap.containsKey(user))
            return userLevelMap.get(user);
        else
            return "Employee";
    }
}
