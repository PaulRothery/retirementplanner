package com.paul.retirementplanner.service;

import com.paul.retirementplanner.model.Meal;
import com.paul.retirementplanner.repository.MealRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Data
@Slf4j
@Service
public class MealService {


    @Autowired
    private MealRepository mealRepository;

    public List<Meal> findAll() {
        return mealRepository.findAll();
    }

    public Optional<Meal> findMealById(Long id) {
        return mealRepository.findById(id);
    }

    public Meal createMeal(Meal meal) {

        Meal existingMeal = mealRepository.findByName(meal.getName());
        if (existingMeal != null) {
            String error = "Error : Meal already exists for " + meal.getName();
            log.debug(error);
            return null;
        }

        Meal result = mealRepository.save(meal);

        return result;
    }

    public Meal updateMeal(Meal meal) {
        Meal result = mealRepository.save(meal);
        return result;
    }

    public void deleteMeal(Long id) {
        mealRepository.deleteById(id);
    }


}
