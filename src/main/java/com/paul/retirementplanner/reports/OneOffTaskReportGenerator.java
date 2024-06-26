package com.paul.retirementplanner.reports;

import com.paul.retirementplanner.model.Task;
import com.paul.retirementplanner.repository.TaskRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

@Component
public class OneOffTaskReportGenerator {



    @Autowired
    private TaskRepository taskRepository;

    private List<ImmutablePair<Integer, String>> reportLines = new ArrayList<>();

    private static final int UPCOMING_WARNING = 10;
    Instant today = Instant.now();

    private static final int HEADER_SORT_VALUE = 1;
    private static final int OVERDUE_SORT_VALUE = 2;
    private static final int UPCOMING_SORT_VALUE = 3;
    private static final int EVERYTHING_ELSE = 4;  // for testing

    public String generate() {

        reportLines.clear();

        StringBuffer report = new StringBuffer();
        formatReportLines();
        Collections.sort(reportLines);
        for (ImmutablePair reportLine : reportLines) {

            report.append(reportLine.right + "\n");

        }

        return report.toString();

    }

    private void formatReportLines() {


        addHeader();


        List<Task> tasks =  taskRepository.findOneOffTasks();
        for (Task task : tasks) {
            processTask(task);
        }


    }

    private void processTask(Task task) {


        StringBuffer reportLine = new StringBuffer();
        reportLine.append(addField(task.getName(), 30));
        reportLine.append(addField(task.getTargetDate().toString(), 10));

        reportLines.add(new ImmutablePair<>(OVERDUE_SORT_VALUE, reportLine.toString()));

    }

    private void addHeader() {

        StringBuffer reportLine = new StringBuffer();
        reportLine.append(addField("Task", 30));
        reportLine.append(addField("Target", 10));

        reportLines.add(new ImmutablePair<>(HEADER_SORT_VALUE, reportLine.toString()));
    }


    private String addField(String field, int length) {

        String formattedField;

        formattedField = String.format("%-" + length + "s", field) + "  ";
        return formattedField;
    }
}
