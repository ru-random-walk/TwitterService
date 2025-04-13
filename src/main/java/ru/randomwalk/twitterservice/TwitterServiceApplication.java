package ru.randomwalk.twitterservice;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        servers = {
                @Server(url = "/twitter", description = "Twitter service url")
        }
)
public class TwitterServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(TwitterServiceApplication.class, args);
    }

}
