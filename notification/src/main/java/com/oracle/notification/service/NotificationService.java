package com.oracle.notification.service;

import com.github.jknack.handlebars.Handlebars;
import com.github.jknack.handlebars.Template;
import com.github.jknack.handlebars.cache.GuavaTemplateCache;
import com.github.jknack.handlebars.io.ClassPathTemplateLoader;
import com.github.jknack.handlebars.io.TemplateLoader;
import com.github.jknack.handlebars.io.TemplateSource;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.oracle.notification.model.NotificationPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


@Service
public class NotificationService {

    @Autowired
    private JavaMailSender javaMailSender;


    public void sendEmail(NotificationPayload notificationPayload) throws IOException, MessagingException {
        MimeMessage msg=javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, "utf-8");
        try{
            MimeMultipart content = new MimeMultipart();
            MimeBodyPart html = new MimeBodyPart();

            TemplateLoader loader = new ClassPathTemplateLoader("/templates", ".hbs");
            final Cache<TemplateSource, Template> templateCache = CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.MINUTES).maximumSize(1000).build();
            Handlebars handlebars=new Handlebars(loader).with(new GuavaTemplateCache(templateCache));
            Template template = handlebars.compile("invoice-approval-email");

            String templateString=renderEmailContent(notificationPayload,template);
            html.setContent(templateString, "text/html");
            html.setHeader("MIME-Version" , "1.0" );
            html.setHeader("Content-Type" , "text/html" );
            content.addBodyPart(html);
            msg.setContent(content,"text/html");
            helper.setTo(notificationPayload.getAssignee().getEmail());
            helper.setSubject("Approval Request for Task "+notificationPayload.getInvoiceTitle());
            helper.setText(templateString,true);
            javaMailSender.send(msg);

        }catch(Exception e){
            System.out.println("Error in send"+e.getMessage());
            throw e;
        }

    }

    private String renderEmailContent(NotificationPayload notificationPayload,Template template) throws IOException {
        Map<String, Object> parameterMap = new HashMap<>();
        Map<String,String> assigneeMap=new HashMap<>();
        assigneeMap.put("name",notificationPayload.getAssignee().getName());
        parameterMap.put("assignee",assigneeMap);
        Map<String,String> invoiceCreatorMap=new HashMap<>();
        invoiceCreatorMap.put("name",notificationPayload.getInvoiceCreator().getName());
        parameterMap.put("invoiceCreator",invoiceCreatorMap);

        Map<String,String> additionalDetailsMap=new HashMap<>();
        additionalDetailsMap.put("userMessage",notificationPayload.getAdditionalInvoiceDetails().getUserMessage());
        parameterMap.put("additionalInvoiceDetails",additionalDetailsMap);
        parameterMap.put("taskUrl", notificationPayload.getTaskUrl());
        parameterMap.put("invoiceAmount", notificationPayload.getInvoiceAmount());
        parameterMap.put("currency", notificationPayload.getCurrency());
        parameterMap.put("invoiceTitle", notificationPayload.getInvoiceTitle());
        parameterMap.put("invoiceDescription", notificationPayload.getInvoiceDescription());
        parameterMap.put("invoiceDate", notificationPayload.getInvoiceDate());
        parameterMap.put("invoiceCreator.name", notificationPayload.getInvoiceCreator().getName());
        parameterMap.put("assignee.name", notificationPayload.getAssignee().getName());
        parameterMap.put("additionalInvoiceDetails.userMessage", notificationPayload.getAdditionalInvoiceDetails().getUserMessage());
        return template.apply(parameterMap);
    }


}
