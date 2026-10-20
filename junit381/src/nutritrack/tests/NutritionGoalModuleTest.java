package nutritrack.tests;

import junit.framework.TestCase;
import nutritrack.core.NutritionGoalModule;

public class NutritionGoalModuleTest extends TestCase {
    private NutritionGoalModule goals;
    protected void setUp() { goals = new NutritionGoalModule(); }

    public void testDefaultCalorieGoal() { assertEquals(2000, goals.getCalorieGoal()); }
    public void testAcceptMinimumCalorieGoal() { goals.setCalorieGoal(500); assertEquals(500, goals.getCalorieGoal()); }
    public void testAcceptMaximumCalorieGoal() { goals.setCalorieGoal(10000); assertEquals(10000, goals.getCalorieGoal()); }
    public void testRejectGoalBelowMinimum() {
        try { goals.setCalorieGoal(499); fail("Expected validation error"); }
        catch (IllegalArgumentException expected) { assertEquals(2000, goals.getCalorieGoal()); }
    }
    public void testRejectGoalAboveMaximum() {
        try { goals.setCalorieGoal(10001); fail("Expected validation error"); }
        catch (IllegalArgumentException expected) { assertEquals(2000, goals.getCalorieGoal()); }
    }
    public void testCalculateCalorieProgress() { goals.setCalorieGoal(2000); assertEquals(25.0, goals.calorieProgressPercent(500), 0.001); }
    public void testProgressMayExceedOneHundredPercent() { assertEquals(125.0, goals.calorieProgressPercent(2500), 0.001); }
    public void testRejectNegativeConsumedCalories() {
        try { goals.calorieProgressPercent(-1); fail("Expected validation error"); }
        catch (IllegalArgumentException expected) { assertTrue(true); }
    }
    public void testResetDefaults() {
        goals.setCalorieGoal(3000); goals.setMacroGoals(120, 300, 75); goals.resetDefaults();
        assertEquals(2000, goals.getCalorieGoal()); assertEquals(100.0, goals.getProteinGoal(), 0.001);
        assertEquals(250.0, goals.getCarbohydrateGoal(), 0.001); assertEquals(65.0, goals.getFatGoal(), 0.001);
    }
}
