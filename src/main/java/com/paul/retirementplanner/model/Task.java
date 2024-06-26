package com.paul.retirementplanner.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;


import java.sql.Date;

@Data
@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name", columnDefinition = "CHAR(50)")
    private String name;

    @Column(name = "comments", columnDefinition = "CHAR(1000)")
    private String comments;

    @Column(name = "last_completed_date", columnDefinition = "DATE")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd")
    private Date lastCompletedDate;

    @Column(name = "expiration_date", columnDefinition = "DATE")
    private Date expirationDate;

    @Column(name = "target_date", columnDefinition = "DATE")
    private Date targetDate;

    @Column(name = "repeat_cycle", columnDefinition = "INTEGER")
    private int repeatCycle;

    @Column(name = "creation_date", columnDefinition = "DATE")
    private Date creationDate;

    @Column(name = "completed", columnDefinition = "BOOLEAN")
    private boolean completed;


}
