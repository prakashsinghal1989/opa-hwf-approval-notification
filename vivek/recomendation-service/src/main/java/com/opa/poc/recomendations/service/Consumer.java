package com.opa.poc.recomendations.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opa.poc.recomendations.model.AdditionalInvoiceDetails;
import com.opa.poc.recomendations.model.NotificationPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class Consumer {

    @Autowired
    private Producer producer;

    private ObjectMapper mapper = new ObjectMapper();
    @KafkaListener(topics = "mytopic", groupId = "mygroup1")
    public void consumeFromTopic(String payload){
        System.out.println(">>>>>>>>>>>>Consumer.consumeFromTopic::message:"+payload);
        //Get message construct payload and plugin the additional details
        NotificationPayload notificationPayload;
        try {
            notificationPayload = mapper.readValue(payload, NotificationPayload.class);
            AdditionalInvoiceDetails details = new AdditionalInvoiceDetails();
            details.setUserMessage("Recommendations coming soon. Hold tight!!!");
            notificationPayload.setAdditionalInvoiceDetails(details);
            payload = mapper.writeValueAsString(notificationPayload);
            System.out.println("Consumer.consumeFromTopic:: Sending to notification post adding recommendations");
        } catch (JsonProcessingException e) {
            System.out.println("Consumer.consumeFromTopic::Caught exception getting recommendations" +
                    "/parsing incoming payload. Sending msg as is to Notification service::"+e.getMessage());
            e.printStackTrace();
        }
        //push to notification topics
        producer.publishToTopic(payload);
        System.out.println("Consumer.consumeFromTopic:::Finished producing to notification topics");
    }
}
