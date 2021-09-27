package com.opa.camunda.poc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class Producer {
    public static final String topic = "mytopic";

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void publishToTopic(String message){
        System.out.println(">>>>>>>>>>>Publishing to topic::"+message);
        this.kafkaTemplate.send(topic, message);
        System.out.println("Producer.publishToTopic::: Completed");
    }
}
