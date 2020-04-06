package com.teeqee;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ChaoChongServer implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication.run(GameTeamApplication.class, args);
        System.setProperty("tomcat.util.http.parser.HttpParser.requestTargetAllow", "{}[]|");
    }

    @Override
    public void run(String... args) throws Exception {

    }
}