package com.paul.retirementplanner.controller;

import com.paul.retirementplanner.reports.ExpiringTaskReportGenerator;
import com.paul.retirementplanner.reports.RepeatableTaskReportGenerator;
import com.paul.retirementplanner.reports.ReportScheduler;
import com.paul.retirementplanner.service.ReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("retirementplanner/report")
@Slf4j
public class ReportController {

    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping("expiringTaskReport")
    public @ResponseBody
    String generateExpiringTaskReport(@RequestParam Boolean emailReport) {

        return reportService.generateExpiringTaskReport(emailReport);

    }

    @PostMapping("repeatableTaskReport")
    public @ResponseBody
    String generateRepeatableTaskReport(@RequestParam Boolean emailReport) {

        return reportService.generateRepeatableTaskReport(emailReport);

    }

    @PostMapping("oneOffTaskReport")
    public @ResponseBody
    String generateOneOffTaskReport(@RequestParam Boolean emailReport) {

        return reportService.generateOneOffTaskReport(emailReport);

    }
}

