package com.teeqee;

import com.teeqee.norak.GameServerBootstrap;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import xyz.noark.game.Noark;

@SpringBootApplication
@EnableScheduling
public class GameTeamApplication implements CommandLineRunner {


    public static void main(String[] args) {
        SpringApplication.run(GameTeamApplication.class, args);
        Noark.run(GameServerBootstrap.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}