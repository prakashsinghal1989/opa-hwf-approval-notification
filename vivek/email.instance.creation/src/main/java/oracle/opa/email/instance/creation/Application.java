package oracle.opa.email.instance.creation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * Start the application and Engine which listen to the email inbox.
 *
 */
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		EmailMessageRecieverEngine emailMessageRecieverEngine = new EmailMessageRecieverEngine();
		System.out.println("Email Recieve Engine started.");
		emailMessageRecieverEngine.start();
	}


}
