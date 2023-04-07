package snapp.trip.com.snapptripurlshortenerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class SnappTripUrlShortenerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SnappTripUrlShortenerServiceApplication.class, args);
    }

}
