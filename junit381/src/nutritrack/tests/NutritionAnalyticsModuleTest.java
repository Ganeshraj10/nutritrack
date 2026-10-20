package nutritrack.tests;

import junit.framework.TestCase;
import nutritrack.core.FoodDiaryModule;
import nutritrack.core.NutritionAnalyticsModule;
import nutritrack.core.NutritionRecord;
import java.time.LocalDate;
import java.util.List;

public class NutritionAnalyticsModuleTest extends TestCase {
    private FoodDiaryModule diary;
    private NutritionAnalyticsModule analytics;
    private LocalDate day1, day2, day3;
    protected void setUp() {
        diary = new FoodDiaryModule(); analytics = new NutritionAnalyticsModule();
        day1 = LocalDate.of(2026, 10, 18); day2 = day1.plusDays(1); day3 = day2.plusDays(1);
        diary.addEntry("Oats", "Breakfast", 300, 10, 40, 5, day1);
        diary.addEntry("Rice", "Lunch", 500, 8, 90, 4, day1);
        diary.addEntry("Egg", "Breakfast", 150, 12, 1, 10, day3);
    }
    public void testDailyCaloriesSumRecordsForDate() { assertEquals(800.0, analytics.dailyCalories(diary.getAllEntries(), day1), 0.001); }
    public void testAverageIncludesZeroIntakeDays() {
        assertEquals(950.0 / 3.0, analytics.averageDailyCalories(diary.getAllEntries(), day1, day3), 0.001);
    }
    public void testDateRangeExcludesRecordsOutsideRange() {
        assertEquals(150.0, analytics.averageDailyCalories(diary.getAllEntries(), day3, day3), 0.001);
    }
    public void testHighestDailyCalories() { assertEquals(800.0, analytics.highestDailyCalories(diary.getAllEntries(), day1, day3), 0.001); }
    public void testLowestDailyCaloriesRecognizesEmptyDay() { assertEquals(0.0, analytics.lowestDailyCalories(diary.getAllEntries(), day1, day3), 0.001); }
    public void testEmptyListReturnsZeroAverage() { assertEquals(0.0, analytics.averageDailyCalories(java.util.Collections.<NutritionRecord>emptyList(), day1, day3), 0.001); }
    public void testRejectInvalidDateRange() {
        try { analytics.averageDailyCalories(diary.getAllEntries(), day3, day1); fail("Expected validation error"); }
        catch (IllegalArgumentException expected) { assertTrue(true); }
    }
    public void testSingleDayMinimum() { assertEquals(150.0, analytics.lowestDailyCalories(diary.getAllEntries(), day3, day3), 0.001); }
}
