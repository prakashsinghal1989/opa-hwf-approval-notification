package oracle.opa.email.instance.creation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
@RequestMapping("/v1/slack")
public class SlackController {
    @Autowired
    SlackService slackService;


    @RequestMapping(value="/welcome")
    @ResponseBody
    public String welcome(){
        slackService.postMessageToSlackChannel();
        return "Hello from Notification Service!!";

    }



}