package com.opa.camunda.poc.controller;

import com.fasterxml.jackson.core.JsonParser;
import com.opa.camunda.poc.util.HwfUtil;
import org.apache.tomcat.util.json.JSONParser;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@RequestMapping("/hwf")
public class HWFController {

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping("/{processDefName}/start")
    @CrossOrigin(origins = "http://localhost:3000")
    public String startProcess(@PathVariable("processDefName") String processDefName, @RequestBody String payload) {
        System.out.println("Inside predictTime of RecommendationController payload::"+payload.toString());
        String baseSrvUrl = "http://camunda-hwf-engine-svc:8080/engine-rest/";
        String format = "http://camunda-hwf-engine-svc:8080/engine-rest/process-definition/key/%s/start";
        String url = String.format(format, processDefName);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> entity = new HttpEntity<String>(payload, headers);
        ResponseEntity e = restTemplate.postForEntity(url, entity, String.class);
        System.out.println("e.getStatusCode() = " + e.getStatusCode());
        String response = e.getBody().toString();
        System.out.println("HWFController.startProcess:: Process Started::" + response);
        try {
            JSONObject object = new JSONObject(response);
            return object.getString("id");
        } catch (JSONException ex) {
            ex.printStackTrace();
            return "Completed";
        }
    }

    @GetMapping("/preference/{userName}")
    public String getUserPreference(@PathVariable("userName") String username){
        return HwfUtil.getUserPreference(username);
    }

    @PostMapping("/preference")
    public void updateUserPreference(@RequestBody String payload){
        System.out.println(">>>>>>>>>Trying to update UserPref ::"+payload);
        JSONObject object = new JSONObject(payload);
        String userName = object.getString("userName");
        String enableAutoApprove = object.getString("enableAutoApprove");
        System.out.println("userName:enableAutoApprove = " +userName +":"+ enableAutoApprove);
        HwfUtil.setUserPreference(userName, enableAutoApprove);
    }

    @GetMapping("/preference/print")
    public Map printPreference(){
        return HwfUtil.printPreference();
    }
}
