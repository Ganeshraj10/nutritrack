package com.nutritrack.repository;
import com.nutritrack.domain.PlannedMeal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
public interface PlannedMealRepository extends JpaRepository<PlannedMeal, Long> {
    List<PlannedMeal> findByPlanDateBetweenOrderByPlanDateAsc(LocalDate start, LocalDate end);
}
