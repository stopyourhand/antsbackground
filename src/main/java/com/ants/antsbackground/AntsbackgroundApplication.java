package com.ants.antsbackground;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.ants.antsbackground.*")
@SpringBootApplication
public class AntsbackgroundApplication {

    public static void main(String[] args) {
        SpringApplication.run(AntsbackgroundApplication.class, args);
    }

}
