package com.paul.retirementplanner.controller;

import com.paul.retirementplanner.model.Task;
import com.paul.retirementplanner.service.TaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("retirementplanner")
@Slf4j
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("tasks")
    public @ResponseBody
    List<Task> getAllTask() {
        return taskService.findAll();


    }

    @GetMapping("tasks/repeatable")
    public @ResponseBody
    List<Task> getRepeatableTasks() {
        return taskService.findRepeatableTasks();

    }

    @GetMapping("tasks/expiry")
    public @ResponseBody
    List<Task> getExpiryTasks() {
        return taskService.findExpiryTasks();

    }

    @GetMapping("tasks/oneoff")
    public @ResponseBody
    List<Task> getOneOffTasks() {
        return taskService.findOneOffTasks();

    }

    @GetMapping("task/{id}")
    public @ResponseBody
    ResponseEntity<?> getTask(@PathVariable Long id) {
        Optional<Task> task = taskService.findTaskById(id);
/*        if (task == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task Not Found");
        }*/

        return task.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PostMapping("task")
    public @ResponseBody
    ResponseEntity<Task>  createTask(@RequestBody Task task) throws URISyntaxException {
        Task result =  taskService.createTask(task);

        return ResponseEntity.created(new URI("/api/group/" + result.getId()))
                .body(result);
    }


    @PutMapping("task/{id}")
    public @ResponseBody
    ResponseEntity<Task>  updateTask(@RequestBody Task task) {
        log.info("Update for task " + task);

        Task result =  taskService.updateTask(task);

        return ResponseEntity.ok().body(result);

    }


    @DeleteMapping("task/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {

        log.info("Request to delete task: {}", id);
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();

    }
}

