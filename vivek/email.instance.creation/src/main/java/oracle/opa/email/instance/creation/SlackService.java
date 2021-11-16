package oracle.opa.slack.service;

import com.slack.api.methods.SlackApiException;
import com.slack.api.methods.response.chat.ChatPostMessageResponse;
import org.springframework.stereotype.Service;
import com.slack.api.Slack;

import java.io.IOException;

@Service
public class SlackService {


    public void postMessageToSlackChannel() {
        try {
            Slack slack = Slack.getInstance();
            ChatPostMessageResponse response = slack.methods("xoxb-2659600556197-2663476119430-bbeLsqM6qIkPcLNfwhw1rBM0").chatPostMessage(req -> req
                    .channel("C02K1SRNSLX")
                    .text("Invoice Amount: 8888\n" +
                            "Invoice Title : Airfare Reimburement\n" +
                            "Invoice Date: 2021-10-27\n" +
                            "Currency: INR\n" +
                            "Invoice Description: Nov month charges\n" +
                            "CreatorId: Sid\n" +
                            "ExpenseType: Food"));
        }catch(Exception exception){
            System.out.println(exception.getMessage());
        }



    }

    public void readMessageFromSlackChannel(){

    }
}
