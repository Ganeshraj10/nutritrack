package com.nutritrack.service;

import com.nutritrack.domain.FoodEntry;
import com.nutritrack.repository.FoodEntryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class FoodDiaryService {
    private static final Set<String> MEALS = Set.of("Breakfast", "Lunch", "Dinner", "Snack");
    private final FoodEntryRepository repository;
    public FoodDiaryService(FoodEntryRepository repository) { this.repository = repository; }

    @Transactional
    public FoodEntry add(String name, String mealType, double quantity, String unit, double calories,
                         double protein, double carbohydrates, double fat, LocalDate date, String notes) {
        validate(name, mealType, quantity, unit, calories, protein, carbohydrates, fat, date);
        return repository.save(new FoodEntry(name.trim(), mealType, quantity, unit.trim(), calories, protein, carbohydrates, fat, date,
            notes == null ? "" : notes.trim()));
    }
    @Transactional
    public FoodEntry update(Long id, String name, String mealType, double quantity, String unit, double calories,
                            double protein, double carbohydrates, double fat, LocalDate date, String notes) {
        validate(name, mealType, quantity, unit, calories, protein, carbohydrates, fat, date);
        FoodEntry item = repository.findById(id).orElseThrow(() -> new NoSuchElementException("Food entry not found: " + id));
        item.setName(name.trim()); item.setMealType(mealType); item.setQuantity(quantity); item.setUnit(unit.trim());
        item.setCalories(calories); item.setProtein(protein); item.setCarbohydrates(carbohydrates); item.setFat(fat);
        item.setEntryDate(date); item.setNotes(notes == null ? "" : notes.trim());
        return repository.save(item);
    }
    public List<FoodEntry> getForDate(LocalDate date) {
        if (date == null) throw new IllegalArgumentException("Date is required");
        return repository.findByEntryDateOrderByCreatedAtDesc(date);
    }
    public List<FoodEntry> getAll() { return repository.findAll(); }
    @Transactional
    public void delete(Long id) {
        if (id == null || !repository.existsById(id)) throw new NoSuchElementException("Food entry not found: " + id);
        repository.deleteById(id);
    }
    private void validate(String name, String mealType, double quantity, String unit, double calories,
                          double protein, double carbohydrates, double fat, LocalDate date) {
        if (name == null || name.isBlank() || name.trim().length() > 100) throw new IllegalArgumentException("Food name is required and must be at most 100 characters");
        if (mealType == null || !MEALS.contains(mealType)) throw new IllegalArgumentException("Choose a valid meal category");
        if (!Double.isFinite(quantity) || quantity <= 0 || quantity > 1000) throw new IllegalArgumentException("Quantity must be greater than 0 and no more than 1000");
        if (unit == null || unit.isBlank() || unit.trim().length() > 20) throw new IllegalArgumentException("Serving unit is required");
        validateNutrient("Calories", calories, 0, 10000);
        validateNutrient("Protein", protein, 0, 1000);
        validateNutrient("Carbohydrates", carbohydrates, 0, 1500);
        validateNutrient("Fat", fat, 0, 1000);
        if (date == null) throw new IllegalArgumentException("Entry date is required");
    }
    private void validateNutrient(String label, double value, double min, double max) {
        if (!Double.isFinite(value) || value < min || value > max) throw new IllegalArgumentException(label + " must be between " + (int)min + " and " + (int)max);
    }
}
