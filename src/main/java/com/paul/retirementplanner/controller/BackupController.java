package com.paul.retirementplanner.controller;

import com.paul.retirementplanner.model.Meal;
import com.paul.retirementplanner.model.Task;
import com.paul.retirementplanner.service.BackupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("retirementplanner")
@Slf4j
public class BackupController {

    private final BackupService backupService;

    public BackupController(BackupService backupService1) {
        this.backupService = backupService1;
    }

    @GetMapping("backup")
    public @ResponseBody
    List<String> backup() throws IOException {

       String fileName = backupService.backup();

       List<String> result = new ArrayList<>();
       result.add("Backup complete - " + fileName);
       return result;
    }


}

