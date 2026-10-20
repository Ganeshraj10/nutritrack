package com.nutritrack.repository;
import com.nutritrack.domain.NutritionGoal;
import org.springframework.data.jpa.repository.JpaRepository;
public interface NutritionGoalRepository extends JpaRepository<NutritionGoal, Long> {}
