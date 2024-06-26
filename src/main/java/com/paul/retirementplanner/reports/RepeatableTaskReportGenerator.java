package com.paul.retirementplanner.reports;

import com.paul.retirementplanner.model.Task;
import com.paul.retirementplanner.repository.TaskRepository;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static java.time.temporal.ChronoUnit.DAYS;

@Component
public class RepeatableTaskReportGenerator {



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


        List<Task> tasks =  taskRepository.findUpcomingRepeatable();
        for (Task task : tasks) {
            processTask(task);
        }


    }

    private void processTask(Task task) {


        StringBuffer reportLine = new StringBuffer();
        reportLine.append(addField(task.getName(), 30));

        LocalDate lastCompleted;
        LocalDate nextDeadline;
        if (task.getLastCompletedDate() == null) {
            nextDeadline = LocalDate.now();
            lastCompleted = LocalDate.now();
        } else {
            nextDeadline = task.getLastCompletedDate().toLocalDate();
            lastCompleted = task.getLastCompletedDate().toLocalDate();
        }

        nextDeadline = nextDeadline.plusDays(task.getRepeatCycle());

        reportLine.append(addField(lastCompleted.toString(), 10));
        reportLine.append(addField(nextDeadline.toString(), 10));
        //reportLine.append(addField(String.valueOf(task.getRepeatCycle()), 3));

        // Days until next deadline
        long daysUntilNextDeadline = DAYS.between(LocalDate.now(), nextDeadline);
        //reportLine.append(addField(String.valueOf(daysUntilNextDeadline), 6));

        int upcomingWarning = Math.min(task.getRepeatCycle(), UPCOMING_WARNING);

        if (!LocalDate.now().isBefore(nextDeadline)) {
            reportLine.append("OVERDUE  ");
            reportLines.add(new ImmutablePair<>(OVERDUE_SORT_VALUE, reportLine.toString()));


        } else if (upcomingWarning > daysUntilNextDeadline) {

            // send out a warning if a task is coming due either in the upcoming warning
            // number of days or the repeat cycle if it is less
            reportLine.append("UPCOMING ");
            reportLines.add(new ImmutablePair<>(UPCOMING_SORT_VALUE, reportLine.toString()));
        } else {
            // this is just for testing
            //reportLines.add(new ImmutablePair<>(EVERYTHING_ELSE, reportLine.toString()));
        }


    }

    private void addHeader() {

        StringBuffer reportLine = new StringBuffer();
        reportLine.append(addField("Task", 30));
        reportLine.append(addField("Last Comp", 10));
        reportLine.append(addField("Next", 10));
        //  reportLine.append(addField("RC", 3));
        //  reportLine.append(addField("DTN", 6));

        reportLines.add(new ImmutablePair<>(HEADER_SORT_VALUE, reportLine.toString()));
    }


    private String addField(String field, int length) {

        String formattedField;

        formattedField = String.format("%-" + length + "s", field) + "  ";
        return formattedField;
    }
}
