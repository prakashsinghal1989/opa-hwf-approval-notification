package com.opa.camunda.poc.controller;

import com.fasterxml.jackson.core.JsonParser;
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
}
