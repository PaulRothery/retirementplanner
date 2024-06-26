package com.paul.retirementplanner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j

@SpringBootApplication
public class RetirementPlannerApplication {

	public static void main(String[] args) {

		log.info("Starting RetirementPlanner...");
		SpringApplication.run(RetirementPlannerApplication.class, args);

		log.info("RetirementPlanner Complete");

	}

}
