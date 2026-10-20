package nutritrack.core;

/** Goal validation and progress calculations for a single demo user. */
public final class NutritionGoalModule {
    private int calorieGoal = 2000;
    private double proteinGoal = 100, carbohydrateGoal = 250, fatGoal = 65;

    public int getCalorieGoal() { return calorieGoal; }
    public double getProteinGoal() { return proteinGoal; }
    public double getCarbohydrateGoal() { return carbohydrateGoal; }
    public double getFatGoal() { return fatGoal; }

    public void setCalorieGoal(int value) {
        if (value < 500 || value > 10000) throw new IllegalArgumentException("Calorie goal must be between 500 and 10000");
        calorieGoal = value;
    }
    public void setMacroGoals(double protein, double carbohydrates, double fat) {
        validateMacro("Protein", protein); validateMacro("Carbohydrates", carbohydrates); validateMacro("Fat", fat);
        proteinGoal = protein; carbohydrateGoal = carbohydrates; fatGoal = fat;
    }
    public double calorieProgressPercent(double consumedCalories) {
        if (Double.isNaN(consumedCalories) || Double.isInfinite(consumedCalories) || consumedCalories < 0)
            throw new IllegalArgumentException("Consumed calories must be non-negative");
        return consumedCalories / calorieGoal * 100.0;
    }
    public void resetDefaults() { calorieGoal = 2000; proteinGoal = 100; carbohydrateGoal = 250; fatGoal = 65; }
    private void validateMacro(String label, double value) {
        if (Double.isNaN(value) || Double.isInfinite(value) || value < 0 || value > 1000)
            throw new IllegalArgumentException(label + " goal must be between 0 and 1000 g");
    }
}
