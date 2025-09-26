package com.scm.utility;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class DailyBatchJob {
    @Scheduled(cron = "0 */10 * * * *", zone = "Asia/Kolkata")
    public void runDailyJob() {
        System.out.println("Cron Job started at: " +
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
}
