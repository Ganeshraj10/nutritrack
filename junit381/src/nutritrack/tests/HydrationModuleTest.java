package nutritrack.tests;

import junit.framework.TestCase;
import nutritrack.core.HydrationModule;
import java.time.LocalDate;

public class HydrationModuleTest extends TestCase {
    private HydrationModule hydration;
    private LocalDate today;
    protected void setUp() { hydration = new HydrationModule(); today = LocalDate.of(2026, 10, 20); }

    public void testAddWaterEntry() {
        HydrationModule.WaterEntry entry = hydration.addWater(250, today);
        assertEquals(250, entry.getMillilitres()); assertEquals(250, hydration.dailyTotal(today));
    }
    public void testRejectZeroWater() {
        try { hydration.addWater(0, today); fail("Expected validation error"); }
        catch (IllegalArgumentException expected) { assertEquals(0, hydration.dailyTotal(today)); }
    }
    public void testRejectNegativeWater() {
        try { hydration.addWater(-100, today); fail("Expected validation error"); }
        catch (IllegalArgumentException expected) { assertEquals(0, hydration.dailyTotal(today)); }
    }
    public void testRejectUnreasonableWaterEntry() {
        try { hydration.addWater(5001, today); fail("Expected validation error"); }
        catch (IllegalArgumentException expected) { assertEquals(0, hydration.dailyTotal(today)); }
    }
    public void testDailyTotalSeparatesDates() {
        hydration.addWater(250, today); hydration.addWater(400, today); hydration.addWater(300, today.plusDays(1));
        assertEquals(650, hydration.dailyTotal(today)); assertEquals(300, hydration.dailyTotal(today.plusDays(1)));
    }
    public void testProgressPercentage() {
        hydration.addWater(500, today); assertEquals(25.0, hydration.progressPercent(today, 2000), 0.001);
    }
    public void testRemoveExistingEntry() {
        HydrationModule.WaterEntry entry = hydration.addWater(350, today);
        assertTrue(hydration.removeWater(entry.getId())); assertEquals(0, hydration.dailyTotal(today));
    }
    public void testMissingEntryCannotBeRemoved() { assertFalse(hydration.removeWater(999)); }
    public void testRejectZeroGoal() {
        try { hydration.progressPercent(today, 0); fail("Expected validation error"); }
        catch (IllegalArgumentException expected) { assertTrue(true); }
    }
}
