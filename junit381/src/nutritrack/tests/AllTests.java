package nutritrack.tests;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/** Aggregate suite recognized by the classic JUnit Swing GUI runner. */
public class AllTests extends TestCase {
    public static Test suite() {
        TestSuite suite = new TestSuite("NutriTrack Pro - JUnit 3.8.1 Module Tests");
        suite.addTestSuite(FoodDiaryModuleTest.class);
        suite.addTestSuite(FoodCatalogueModuleTest.class);
        suite.addTestSuite(NutritionGoalModuleTest.class);
        suite.addTestSuite(HydrationModuleTest.class);
        suite.addTestSuite(NutritionAnalyticsModuleTest.class);
        return suite;
    }
}
