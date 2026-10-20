package com.nutritrack.repository;
import com.nutritrack.domain.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
public interface FoodItemRepository extends JpaRepository<FoodItem, Long> {}
