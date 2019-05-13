package dk.kea.dat18i.teamsix.biotrio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@SpringBootApplication
public class BioTrioApplication {



    public static void main(String[] args) {
            SpringApplication.run(BioTrioApplication.class,args);
        }

}
