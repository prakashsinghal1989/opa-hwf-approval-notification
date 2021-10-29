package com.oracle.notification.service;

import com.oracle.notification.EmailContentReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.*;
import javax.mail.search.FlagTerm;
import java.util.Properties;
import javax.mail.internet.InternetAddress;


/**
 * Engine listen to the inbox and send reply after correct invocation.
 */
@Service
public class EmailMessageReceiverEngine  {


    int noOfMessages = 0;

    @Value("${spring.mail.host}")
    private String host;

    @Value("${spring.mail.username}")
    private String user;

    @Value("${spring.mail.password}")
    private String password;

    @Value("${spring.mail.port}")
    private String port;

    @PostConstruct
    public void start() {
        while(true)

        {
            try {
                System.out.println("Reading...");

                checkEmailAndCreateInstance();
                System.out.println("Sleeping...");
                Thread.sleep(5000);
            } catch (Exception e) {

            }
        }
    }

    // function to make connection and get mails from server known as "Pop" server
    public void checkEmailAndCreateInstance() {
        try {

            //create properties field
            Properties properties = new Properties();

            properties.put("mail.smtp.host", host);
            properties.put("mail.smtp.port", port);
            properties.put("mail.smtp.starttls.enable", "true");
            Session emailSession = Session.getDefaultInstance(properties);


            //create the POP3 store object and connect with the pop server
            Store store = emailSession.getStore("imaps");
            store.connect(host, user, password);

            //create the folder object and open it
            Folder emailFolder = store.getFolder("Inbox");

            emailFolder.open(Folder.READ_WRITE);


            Message[] messages = emailFolder.search(
                    new FlagTerm(new Flags(Flags.Flag.SEEN), false));

            System.out.println("New Email Messages::" + messages.length);

            for (int i = 0, n = messages.length; i < n; i++) {
                Message message = messages[i];


                Object obj = message.getContent();
                if (obj instanceof Multipart) {
                    Multipart mp = (Multipart) obj;
                    BodyPart bp = mp.getBodyPart(0);
                    if(message.getSubject() !=null || message.getSubject().contains("ExpenseReport")) {

                        System.out.println("---------------------------------");
                        System.out.println("Subject: " + message.getSubject());
//                        System.out.println("Body: " + bp.getContent().toString());

                        EmailContentReader reader = new EmailContentReader();
                        String p = reader.getExpenseReportPayload(bp.getContent().toString());
//                        System.out.println("PPP::"+ p);
////                        System.out.println("MyBody::" + c.getExpenseReportPayload(bp.getContent().toString()));
//                        System.exit(-1);
                        InvokeInstanceHandler invoiceInstance = new InvokeInstanceHandler("DemoBPM",p);
                        invoiceInstance.invokeInstance();
                        SendMail mailsender = new SendMail();

                        Address[] froms =message.getFrom();

                        String email = froms == null ? null : ((InternetAddress) froms[0]).getAddress();
//                        InternetAddress address = (InternetAddress) froms[0];
//                        String person = address.getPersonal();
                        mailsender.sendEmail(emailSession, email, user, message.getSubject() , bp.getContent().toString());

                    }
                }
            }

            //close the store and folder objects
            emailFolder.close(false);
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

