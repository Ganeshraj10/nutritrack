package nutritrack.core;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Plain-Java business rules for food diary operations, independent of Spring/Maven. */
public final class FoodDiaryModule {
    private static final String[] MEAL_TYPES = {"Breakfast", "Lunch", "Dinner", "Snack"};
    private final List<NutritionRecord> records = new ArrayList<NutritionRecord>();
    private int nextId = 1;

    public NutritionRecord addEntry(String foodName, String mealType, double calories,
                                    double protein, double carbohydrates, double fat, LocalDate date) {
        validate(foodName, mealType, calories, protein, carbohydrates, fat, date);
        NutritionRecord entry = new NutritionRecord(nextId++, foodName.trim(), mealType,
                calories, protein, carbohydrates, fat, date);
        records.add(entry);
        return entry;
    }

    public List<NutritionRecord> getEntriesForDate(LocalDate date) {
        if (date == null) throw new IllegalArgumentException("Date is required");
        List<NutritionRecord> result = new ArrayList<NutritionRecord>();
        for (NutritionRecord record : records) if (record.getDate().equals(date)) result.add(record);
        return Collections.unmodifiableList(result);
    }

    public List<NutritionRecord> getAllEntries() {
        return Collections.unmodifiableList(new ArrayList<NutritionRecord>(records));
    }

    public boolean deleteEntry(int id) {
        for (int i = 0; i < records.size(); i++) {
            if (records.get(i).getId() == id) { records.remove(i); return true; }
        }
        return false;
    }

    public double caloriesForDate(LocalDate date) {
        double total = 0;
        for (NutritionRecord record : getEntriesForDate(date)) total += record.getCalories();
        return total;
    }

    private void validate(String name, String meal, double calories, double protein,
                          double carbs, double fat, LocalDate date) {
        if (name == null || name.trim().length() == 0 || name.trim().length() > 100)
            throw new IllegalArgumentException("Food name is required (max 100 characters)");
        boolean validMeal = false;
        for (String allowed : MEAL_TYPES) if (allowed.equals(meal)) validMeal = true;
        if (!validMeal) throw new IllegalArgumentException("Invalid meal category");
        checkRange("Calories", calories, 0, 10000);
        checkRange("Protein", protein, 0, 1000);
        checkRange("Carbohydrates", carbs, 0, 1500);
        checkRange("Fat", fat, 0, 1000);
        if (date == null) throw new IllegalArgumentException("Date is required");
    }

    private void checkRange(String label, double value, double min, double max) {
        if (Double.isNaN(value) || Double.isInfinite(value) || value < min || value > max)
            throw new IllegalArgumentException(label + " is outside the permitted range");
    }
}
