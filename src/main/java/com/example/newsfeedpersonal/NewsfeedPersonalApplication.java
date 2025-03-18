package com.example.newsfeedpersonal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class NewsfeedPersonalApplication {

    public static void main(String[] args) {
        SpringApplication.run(NewsfeedPersonalApplication.class, args);
    }

}
