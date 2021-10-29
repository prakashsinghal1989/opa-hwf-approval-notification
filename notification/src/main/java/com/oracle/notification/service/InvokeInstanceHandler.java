package com.oracle.notification.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import com.oracle.notification.Constants;

/**
 * Module to initate the deployed BPMN process.
 */
public class InvokeInstanceHandler {

    private final String processName;
    private final String payload;

    public InvokeInstanceHandler(final String processName, final String payload) {
        this.processName = processName;
        this.payload = payload.trim();
    }

    public void invokeInstance() throws Exception {
        //System.out.println("InvokeInstanceHandler.invokeInstance: payload::"+payload);
        URL url = new URL(Constants.CAMUNDA_BPMN_PROCESS_START);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("Content-Type", "application/json");
        con.setDoOutput(true);
        con.setRequestMethod("POST");
        OutputStream os = con.getOutputStream();
        OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");

        osw.write((payload));
        osw.flush();
        osw.close();
        os.close();  //don't forget to close the OutputStream
        con.connect();

        int status = con.getResponseCode();
        System.out.println(status);
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer content = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();
        System.out.println("Respone::" + content);

    }


}
