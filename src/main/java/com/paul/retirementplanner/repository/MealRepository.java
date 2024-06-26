package com.paul.retirementplanner.repository;

import com.paul.retirementplanner.model.Meal;
import com.paul.retirementplanner.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MealRepository extends JpaRepository<Meal, Long> {


    @Query("SELECT meal FROM Meal meal WHERE meal.name = ?1")
    Meal findByName(String name);
}
