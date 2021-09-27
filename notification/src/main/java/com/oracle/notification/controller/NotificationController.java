package com.oracle.notification.controller;

import com.oracle.notification.service.NotificationService;
//import com.oracle.notification.service.OutlookNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;
import com.oracle.notification.model.NotificationPayload;

@RestController
@EnableAutoConfiguration
@RequestMapping("/v1/notification")
public class NotificationController {
    @Autowired
    NotificationService notificationService;

    //@Autowired
    //OutlookNotificationService outlookNotificationService

    @RequestMapping(value="/welcome")
    @ResponseBody
    public String welcome(){
            return "Hello from Notification Service!!";

        }

    @PostMapping(value="/sendmail",consumes="application/json",produces="text/html")
    public String sendEmail(@RequestBody NotificationPayload payload){
        try{
            notificationService.sendEmail(payload);
            return "Email Sent Successfully  ";

        }catch(Exception exception){
            System.out.println(exception.getMessage());
            return "There is some error in sending message";
        }

    }

}
