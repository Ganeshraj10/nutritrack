package com.nutritrack.repository;
import com.nutritrack.domain.FoodEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
public interface FoodEntryRepository extends JpaRepository<FoodEntry, Long> {
    List<FoodEntry> findByEntryDateOrderByCreatedAtDesc(LocalDate date);
    List<FoodEntry> findByEntryDateBetweenOrderByCreatedAtDesc(LocalDate start, LocalDate end);
}
