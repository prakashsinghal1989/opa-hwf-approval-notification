package com.opa.poc.recomendations.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {
    public static final String topic = "notificationtopic";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void publishToTopic(String payload){
        System.out.println(">>>>>>>>>>>Publishing to topic::"+payload);
        this.kafkaTemplate.send(topic, payload);
    }
}
