package dk.kea.dat18i.teamsix.biotrio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BioTrioApplication {

    public static void main(String[] args) {
        SpringApplication.run(BioTrioApplication.class, args);
        System.out.println("Let's inspect the beans provided by Spring Boot:");

    }

}
