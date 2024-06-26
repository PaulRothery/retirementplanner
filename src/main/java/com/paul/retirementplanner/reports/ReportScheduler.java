package com.paul.retirementplanner.reports;

import com.paul.retirementplanner.service.EmailService;
import jakarta.annotation.PostConstruct;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


// @Scheduled(cron = "0 1 1 * * ?")
//  Below you can find the example patterns from the spring forum:
//
// "0 0 * * * *" = the top of every hour of every day.
// "*/10 * * * * *" = every ten seconds.
// "0 0 8-10 * * *" = 8, 9 and 10 o'clock of every day.
// "0 0 8,10 * * *" = 8 and 10 o'clock of every day.
// "0 0/30 8-10 * * *" = 8:00, 8:30, 9:00, 9:30 and 10 o'clock every day.
// "0 0 9-17 * * MON-FRI" = on the hour nine-to-five weekdays
// "0 0 0 25 12 ?" = every Christmas Day at midnight
//
//Cron expression is represented by six fields:
//        second, minute, hour, day of month, month, day(s) of week

@Component
@EnableScheduling
public class ReportScheduler {

    private final ExpiringTaskReportGenerator expiringTaskReportGenerator;
    private final RepeatableTaskReportGenerator repeatableTaskReportGenerator;
    private final OneOffTaskReportGenerator oneOffTaskReportGenerator;
    private final EmailService emailService;


    public ReportScheduler(ExpiringTaskReportGenerator expiringTaskReportGenerator, RepeatableTaskReportGenerator repeatableTaskReportGenerator, OneOffTaskReportGenerator oneOffTaskReportGenerator, EmailService emailService) {
        this.expiringTaskReportGenerator = expiringTaskReportGenerator;
        this.repeatableTaskReportGenerator = repeatableTaskReportGenerator;
        this.oneOffTaskReportGenerator = oneOffTaskReportGenerator;
        this.emailService = emailService;
    }

    @Scheduled(cron = "0 0 6 * * *")
    public void emailExpiringTaskReport() {

        System.out.println("emailing Expiring Task Report...");
        String report = expiringTaskReportGenerator.generate();
        emailService.sendSimpleMessage("Expiring Task Report", report);

    }

    @Scheduled(cron = "0 0 6 * * *")
    public void emailRepeatableTaskReport() {

        System.out.println("emailing Repeatable Task Report...");
        String report = repeatableTaskReportGenerator.generate();
        emailService.sendSimpleMessage("Repeatable Task Report", report);

    }

    @Scheduled(cron = "0 0 6 * * *")
    public void emailOneOffTaskReport() {

        System.out.println("emailing One Off Task Report...");
        String report = oneOffTaskReportGenerator.generate();
        emailService.sendSimpleMessage("One Off Task Report", report);

    }

    @PostConstruct
    public void init() {
        emailExpiringTaskReport();
        emailRepeatableTaskReport();
        emailOneOffTaskReport();
    }

}
