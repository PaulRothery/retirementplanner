package com.paul.retirementplanner.model;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;


@Data
@Entity
@Table(name = "meal")
public class Meal {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name", columnDefinition = "CHAR(50)")
    private String name;

    @Column(name = "reference", columnDefinition = "CHAR(200)")
    private String reference;

    @Column(name = "instructions", columnDefinition = "CHAR(1000)")
    private String instructions;

    @Column(name = "rating", columnDefinition = "INTEGER")
    private int rating;

    @Column(name = "ease_of_prep", columnDefinition = "INTEGER")
    private int easeOfPrep;

    @Column(name = "last_used_date", columnDefinition = "DATE")
    private Date lastUsedDate;




}
