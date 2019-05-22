package dk.kea.dat18i.teamsix.biotrio;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.time.LocalDateTime;

@SpringBootApplication
public class BioTrioApplication {
    private final static String ACCOUNT_SID = "ACb148f0ade1dc904031cb2964c9ee73e3";
    private final static String AUTH_ID = "cdb308c91105ee45db4d94a9262a5dfe";

    static {
        Twilio.init(ACCOUNT_SID, AUTH_ID);
    }
    public static void main(String[] args) {
        SpringApplication.run(BioTrioApplication.class,args);
/*        String date = "2013-09-18T20:40:00";

        DateTimeZone timeZone = DateTimeZone.forID( "Europe/Copenhagen" );
        DateTime dateTime = new DateTime( date, timeZone );
        DateTime dateTimeUtc = dateTime.withZone( DateTimeZone.UTC );

        System.out.println("dateTime NOW: " + LocalDateTime.now());
        System.out.println( "dateTime: " + dateTime );
        System.out.println( "dateTimeUtc: " + dateTimeUtc );*/
        }

}
