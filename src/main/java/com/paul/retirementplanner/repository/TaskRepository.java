package com.paul.retirementplanner.repository;

import com.paul.retirementplanner.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {


    @Query("SELECT task FROM Task task WHERE task.name = ?1")
    Task findByName(String name);

    @Query("SELECT task FROM Task task " +
            "WHERE task.completed <> true " +
            "AND task.repeatCycle > 0 " +
            "order by task.repeatCycle")
    List<Task> findRepeatableTasks();

    @Query("SELECT task FROM Task task " +
            "WHERE task.expirationDate IS NOT NULL " +
            "AND task.repeatCycle = 0 " +
            "order by task.expirationDate")
    List<Task> findExpiryTasks();

    @Query("SELECT task FROM Task task " +
            "WHERE task.expirationDate IS NULL " +
            "AND task.targetDate IS NOT NULL " +
            "AND task.repeatCycle = 0 " +
            "order by task.targetDate")
    List<Task> findOneOffTasks();

/*    @Query("SELECT task FROM Task task " +
            "WHERE task.expirationDate IS NOT null " +
            "AND task.completed <> TRUE " +
            "order by task.expirationDate desc")
    List<Task> findTasksThatExpire();*/

    @Query("SELECT task FROM Task task WHERE task.repeatCycle > 0")
    List<Task> findUpcomingRepeatable();
}
