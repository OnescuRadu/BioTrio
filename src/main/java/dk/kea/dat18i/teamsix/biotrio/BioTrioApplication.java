package dk.kea.dat18i.teamsix.biotrio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.twilio.Twilio;

@SpringBootApplication
public class BioTrioApplication {
    private final static String ACCOUNT_SID = "ACb148f0ade1dc904031cb2964c9ee73e3"; //Twilio Account SID
    private final static String AUTH_ID = "cdb308c91105ee45db4d94a9262a5dfe"; //Twilio Auth ID

    //Initializing the service for sending sms
    static {
        Twilio.init(ACCOUNT_SID, AUTH_ID);
    }

    //Running the application
    public static void main(String[] args) {
        SpringApplication.run(BioTrioApplication.class,args);

        }

}
