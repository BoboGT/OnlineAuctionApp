package com.sda.onlineAuctionApp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    @Scheduled(fixedDelay = 5000)
    public void cronJob() {
        System.out.println("Running cron job at " + LocalDateTime.now());
    }

}
