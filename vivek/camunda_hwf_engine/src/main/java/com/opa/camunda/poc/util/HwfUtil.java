package com.opa.camunda.poc.util;

import java.util.HashMap;
import java.util.Map;

public class HwfUtil {

    private static Map<String, String> userLevelMap = new HashMap<String, String>(){
        {
            put("", "-1");
            put("Vivek", "1");
            put("Atul", "1");
            put("Sid", "1");
            put("Ravi", "2");
            put("Rajiv", "3");
            put("Lary", "4");
        }
    };

    private static Map<String, String> userManagerMap = new HashMap<String, String>(){
        {
            put("Vivek", "Ravi");
            put("Atul", "Rajiv");
            put("Sid", "Ravi");
            put("Ravi", "Rajiv");
            put("Rajiv", "Lary");
            put("Lary", "");
        }
    };
    public static String getUserLevel(String user){
        if(userLevelMap.containsKey(user))
            return userLevelMap.get(user);
        else
            return "1";
    }
    public static String getManager(String user){
        if(userManagerMap.containsKey(user))
            return userManagerMap.get(user);
        else
            return "Ravi";
    }

    private static Map<String, String> userPreferenceMap = new HashMap<String, String>(){
        {
            put("Vivek", "false");
            put("Atul", "true");
            put("Sid", "true");
            put("Ravi", "true");
            put("Rajiv", "false");
            put("Lary", "true");
        }
    };

    public static String getUserPreference(String username){
        String pref = userPreferenceMap.get(username);
        return  pref != null ? pref : "false" ;
    }

    public static void setUserPreference(String username, String pref){
        userPreferenceMap.put(username, pref);
    }

    public static Map printPreference(){
        for(String key : userPreferenceMap.keySet())
            System.out.println("user:preference = " + key+":"+userPreferenceMap.get(key));
        return userPreferenceMap;
    }
}
