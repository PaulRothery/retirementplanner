package com.paul.retirementplanner.controller;

import com.paul.retirementplanner.model.Meal;
import com.paul.retirementplanner.service.MealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("retirementplanner")
@Slf4j
public class MealController {

    private final MealService mealService;

    public MealController(MealService mealService) {
        this.mealService = mealService;
    }

    @GetMapping("meals")
    public @ResponseBody
    List<Meal> getAllMeal() {
        return mealService.findAll();


    }

    @GetMapping("meal/{id}")
    public @ResponseBody
    ResponseEntity<?> getMeal(@PathVariable Long id) {
        Optional<Meal> meal = mealService.findMealById(id);
/*        if (meal == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Meal Not Found");
        }*/

        return meal.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PostMapping("meal")
    public @ResponseBody
    ResponseEntity<Meal>  createMeal(@RequestBody Meal meal) throws URISyntaxException {
        Meal result =  mealService.createMeal(meal);

        return ResponseEntity.created(new URI("/api/group/" + result.getId()))
                .body(result);
    }


    @PutMapping("meal/{id}")
    public @ResponseBody
    ResponseEntity<Meal>  updateMeal(@RequestBody Meal meal) {
        log.info("Update for meal " + meal);

        Meal result =  mealService.updateMeal(meal);

        return ResponseEntity.ok().body(result);

    }


    @DeleteMapping("meal/{id}")
    public ResponseEntity<?> deleteMeal(@PathVariable Long id) {

        log.info("Request to delete meal: {}", id);
        mealService.deleteMeal(id);
        return ResponseEntity.ok().build();

    }
}

