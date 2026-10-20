package nutritrack.core;

import java.time.LocalDate;

/** Lightweight nutrition diary record used by the standalone JUnit 3.8.1 exercise. */
public final class NutritionRecord {
    private final int id;
    private final String foodName;
    private final String mealType;
    private final double calories;
    private final double protein;
    private final double carbohydrates;
    private final double fat;
    private final LocalDate date;

    NutritionRecord(int id, String foodName, String mealType, double calories,
                    double protein, double carbohydrates, double fat, LocalDate date) {
        this.id = id;
        this.foodName = foodName;
        this.mealType = mealType;
        this.calories = calories;
        this.protein = protein;
        this.carbohydrates = carbohydrates;
        this.fat = fat;
        this.date = date;
    }

    public int getId() { return id; }
    public String getFoodName() { return foodName; }
    public String getMealType() { return mealType; }
    public double getCalories() { return calories; }
    public double getProtein() { return protein; }
    public double getCarbohydrates() { return carbohydrates; }
    public double getFat() { return fat; }
    public LocalDate getDate() { return date; }
}
