package com.oracle.notification.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oracle.notification.NotificationApplication;
import com.oracle.notification.model.NotificationPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;

@Service
public class Consumer {

    @Autowired
    private NotificationService notificationService;

    private ObjectMapper mapper = new ObjectMapper();
    @KafkaListener(topics = "notificationtopic", groupId = "mygroup2")
    public void consumeFromTopic(String payload){
        System.out.println(">>>>>>>>>>>>Consumer.consumeFromTopic::message:"+payload);
        //Get message construct payload and plugin the additional details
        NotificationPayload notificationPayload;
        try {
            notificationPayload = mapper.readValue(payload, NotificationPayload.class);
            System.out.println("Consumer.consumeFromTopic:: Sending to payload for email");
            notificationService.sendEmail(notificationPayload);
            System.out.println("Consumer.consumeFromTopic:::Finished sending Email");
        } catch (JsonProcessingException e) {
            System.out.println("Consumer.consumeFromTopic::Caught exception coverting payload to NotificationPayload::"+e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Consumer.consumeFromTopic::Caught exception IO::"+e.getMessage());
            e.printStackTrace();
        } catch (MessagingException e) {
            System.out.println("Consumer.consumeFromTopic::Caught exception messaging::"+e.getMessage());
            e.printStackTrace();
        }

    }
}
