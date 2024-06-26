package com.paul.retirementplanner.service;

import com.paul.retirementplanner.model.Task;
import com.paul.retirementplanner.repository.TaskRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Data
@Slf4j
@Service
public class TaskService {


    @Autowired
    private TaskRepository taskRepository;

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public List<Task> findRepeatableTasks() {
        return taskRepository.findRepeatableTasks();
    }

    public List<Task> findExpiryTasks() {
        return taskRepository.findExpiryTasks();
    }

    public List<Task> findOneOffTasks() {
        return taskRepository.findOneOffTasks();
    }

    public Optional<Task> findTaskById(Long id) {
        return taskRepository.findById(id);
    }

    public Task createTask(Task task) {

        if (task.getCreationDate() == null) {
           log.debug("Task creation date is null using today");
           task.setCreationDate(new Date(Calendar.getInstance().getTimeInMillis()));
        }

        Task existingTask = taskRepository.findByName(task.getName());
        if (existingTask != null) {
            String error = "Error : Task already exists for " + task.getName();
            log.debug(error);
            return null;
        }

        Task result = taskRepository.save(task);

        return result;
    }

    public Task updateTask(Task task) {


        LocalDate ld = task.getLastCompletedDate().toLocalDate();

        if (task.getRepeatCycle() > 0) {
            task.setTargetDate(Date.valueOf(ld.plusDays(task.getRepeatCycle())));
        }


        Task result = taskRepository.save(task);
        return result;
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }


}
