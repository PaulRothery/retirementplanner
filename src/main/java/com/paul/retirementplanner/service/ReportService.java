package com.paul.retirementplanner.service;

import com.paul.retirementplanner.reports.ExpiringTaskReportGenerator;
import com.paul.retirementplanner.reports.OneOffTaskReportGenerator;
import com.paul.retirementplanner.reports.RepeatableTaskReportGenerator;
import com.paul.retirementplanner.reports.ReportScheduler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ReportService {

    private final ExpiringTaskReportGenerator expiringTaskReportGenerator;
    private final RepeatableTaskReportGenerator repeatableTaskReportGenerator;
    private final OneOffTaskReportGenerator oneOffTaskReportGenerator;

    private final EmailService emailService;

    public ReportService(ExpiringTaskReportGenerator expiringTaskReportGenerator, RepeatableTaskReportGenerator repeatableTaskReportGenerator, OneOffTaskReportGenerator oneOffTaskReportGenerator, ReportScheduler reportScheduler, EmailService emailService) {
        this.expiringTaskReportGenerator = expiringTaskReportGenerator;
        this.repeatableTaskReportGenerator = repeatableTaskReportGenerator;
        this.oneOffTaskReportGenerator = oneOffTaskReportGenerator;
        this.emailService = emailService;
    }



    public String generateExpiringTaskReport(Boolean emailReport) {

        String report = expiringTaskReportGenerator.generate();

        setEmailRequirement(emailReport, "Expiring", report);
        return report;

    }



    public String generateRepeatableTaskReport(Boolean emailReport) {

        String report = repeatableTaskReportGenerator.generate();

        setEmailRequirement(emailReport, "Repeatable", report);
        return report;
    }

    public String generateOneOffTaskReport(Boolean emailReport) {

        String report = oneOffTaskReportGenerator.generate();

        setEmailRequirement(emailReport, "OneOff", report);
        return report;
    }

    private void setEmailRequirement(Boolean emailReport, String reportName, String report) {

        reportName = reportName + " Task Report ";
        String emailRequirement = (emailReport == false ? "without" : "with");
        log.debug("Generating " + reportName + emailRequirement + " email");

        if (emailReport) {
            emailService.sendSimpleMessage(reportName, report);
        }
    }
}

