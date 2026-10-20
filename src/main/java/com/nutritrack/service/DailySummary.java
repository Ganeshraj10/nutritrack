package com.nutritrack.service;
import java.time.LocalDate;
public record DailySummary(LocalDate date, double calories, double protein, double carbohydrates, double fat,
                           int calorieGoal, int proteinGoal, int carbohydrateGoal, int fatGoal,
                           double caloriePercent, int foodCount) {}
