package com.nutritrack.service;

import com.nutritrack.domain.NutritionGoal;
import com.nutritrack.repository.NutritionGoalRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NutritionGoalService {
    public static final long GOAL_ID = 1L;
    private final NutritionGoalRepository repository;
    public NutritionGoalService(NutritionGoalRepository repository) { this.repository = repository; }
    @Transactional
    public NutritionGoal getGoals() {
        return repository.findById(GOAL_ID).orElseGet(() -> repository.save(new NutritionGoal(GOAL_ID, 2200, 120, 250, 70, 2500)));
    }
    @Transactional
    public NutritionGoal update(int calories, int protein, int carbohydrates, int fat, int waterMl) {
        if (calories < 500 || calories > 10000) throw new IllegalArgumentException("Daily calories must be between 500 and 10,000");
        if (protein < 0 || protein > 600) throw new IllegalArgumentException("Protein target must be between 0 and 600 g");
        if (carbohydrates < 0 || carbohydrates > 1000) throw new IllegalArgumentException("Carbohydrate target must be between 0 and 1,000 g");
        if (fat < 0 || fat > 400) throw new IllegalArgumentException("Fat target must be between 0 and 400 g");
        if (waterMl < 250 || waterMl > 10000) throw new IllegalArgumentException("Water target must be between 250 and 10,000 ml");
        NutritionGoal goal = getGoals();
        goal.setDailyCalories(calories); goal.setProteinGrams(protein); goal.setCarbohydrateGrams(carbohydrates);
        goal.setFatGrams(fat); goal.setWaterMl(waterMl);
        return repository.save(goal);
    }
}
