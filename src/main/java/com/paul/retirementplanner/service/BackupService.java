package com.paul.retirementplanner.service;

import com.paul.retirementplanner.model.Meal;
import com.paul.retirementplanner.model.Task;
import com.paul.retirementplanner.repository.MealRepository;
import com.paul.retirementplanner.repository.TaskRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Stream;


@Data
@Slf4j
@Service
public class BackupService {


    private String fileName = "/Users/paulrothery/develop/projects/RetirementPlanner/db/backup/";
    private String pattern = "yyyy-MM-dd:hh-mm-ss";
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);


    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private MealRepository mealRepository;


    public String backup() throws IOException {

        Path backupFile = getBackupFile();

        List<String> sqlStatements = new ArrayList<>();

        List<Meal> meals = mealRepository.findAll();
        sqlStatements = processMealsIntoSQL(meals);

        List<Task> tasks = taskRepository.findAll();
        sqlStatements = Stream.concat(sqlStatements.stream(), processTasksIntoSQL(tasks).stream()).toList();

        Files.write(backupFile,sqlStatements, Charset.defaultCharset());
        return backupFile.toString();
    }



    /**
     * create backup file from currnet datetime
     * @return backup file
     * @throws IOException
     */
    private Path getBackupFile() throws IOException {


        String date = simpleDateFormat.format(new Date());
        log.info("Backing up data for "  + date + ".txt");

        Path file = Paths.get(fileName + date + ".txt");
    //    file.createNewFile();
        return file;

    }


    private List<String> processMealsIntoSQL(List<Meal> meals) {

        List<String> sqlStatements = new ArrayList<>();
        for (Meal meal : meals) {
            StringBuffer sql = new StringBuffer();

            sql.append("insert into meal values (");
            sql.append(meal.getId() + ", ");
            sql.append("'" + meal.getName() + "', ");
            sql.append("'" + meal.getReference() + "', ");
            sql.append("'" + meal.getInstructions() + "', ");
            sql.append(meal.getRating() + ", ");
            sql.append(meal.getEaseOfPrep() + ", ");
            sql.append("'" + meal.getLastUsedDate() + "');");
            sqlStatements.add(sql.toString());

        }
        return sqlStatements;
    }

    private List<String> processTasksIntoSQL(List<Task> tasks) {

        List<String> sqlStatements = new ArrayList<>();
        for (Task task : tasks) {
            StringBuffer sql = new StringBuffer();

            sql.append("insert into task values (");
            sql.append(task.getId() + ", ");
            sql.append("'" + task.getName() + "', ");
            sql.append("'" + task.getComments() + "', ");
            sql.append("'" + task.getLastCompletedDate() + "', ");
            sql.append("'" + task.getExpirationDate() + "' ,");
            sql.append("'" + task.getTargetDate() + "', ");
            sql.append(task.getRepeatCycle() + ", ");
            sql.append("'" + task.getCreationDate() + "', ");
            sql.append(task.isCompleted() + ");");
            sqlStatements.add(sql.toString());

        }
        return sqlStatements;
    }
}
