package nutritrack.tests;

import junit.framework.TestCase;
import nutritrack.core.FoodDiaryModule;
import nutritrack.core.NutritionRecord;
import java.time.LocalDate;

public class FoodDiaryModuleTest extends TestCase {
    private FoodDiaryModule diary;
    private LocalDate today;
    protected void setUp() { diary = new FoodDiaryModule(); today = LocalDate.of(2026, 10, 20); }

    public void testAddValidEntryTrimsFoodName() {
        NutritionRecord r = diary.addEntry("  Oats  ", "Breakfast", 150, 5, 27, 3, today);
        assertEquals("Oats", r.getFoodName()); assertEquals(150.0, r.getCalories(), 0.001);
    }
    public void testRejectBlankFoodName() {
        try { diary.addEntry("   ", "Lunch", 100, 1, 10, 1, today); fail("Expected validation error"); }
        catch (IllegalArgumentException expected) { assertEquals(0, diary.getAllEntries().size()); }
    }
    public void testRejectNegativeCalories() {
        try { diary.addEntry("Rice", "Lunch", -1, 1, 10, 1, today); fail("Expected validation error"); }
        catch (IllegalArgumentException expected) { assertEquals(0, diary.getAllEntries().size()); }
    }
    public void testRejectInvalidMealType() {
        try { diary.addEntry("Rice", "Midnight", 100, 1, 10, 1, today); fail("Expected validation error"); }
        catch (IllegalArgumentException expected) { assertTrue(expected.getMessage().indexOf("meal") >= 0); }
    }
    public void testDailyCaloriesIncludeOnlySelectedDate() {
        diary.addEntry("Oats", "Breakfast", 150, 5, 27, 3, today);
        diary.addEntry("Banana", "Snack", 105, 1, 27, 0, today);
        diary.addEntry("Soup", "Dinner", 90, 2, 8, 2, today.plusDays(1));
        assertEquals(255.0, diary.caloriesForDate(today), 0.001);
    }
    public void testDeleteExistingEntry() {
        NutritionRecord r = diary.addEntry("Apple", "Snack", 95, 0.5, 25, 0.3, today);
        assertTrue(diary.deleteEntry(r.getId())); assertEquals(0, diary.getAllEntries().size());
    }
    public void testDeleteMissingEntryReturnsFalse() { assertFalse(diary.deleteEntry(999)); }
    public void testRejectNullDate() {
        try { diary.addEntry("Rice", "Lunch", 200, 4, 40, 1, null); fail("Expected validation error"); }
        catch (IllegalArgumentException expected) { assertEquals(0, diary.getAllEntries().size()); }
    }
}
