package nutritrack.tests;

import junit.framework.TestCase;
import nutritrack.core.FoodCatalogueModule;
import java.util.List;

public class FoodCatalogueModuleTest extends TestCase {
    private FoodCatalogueModule catalogue;
    protected void setUp() { catalogue = new FoodCatalogueModule(); }

    public void testCatalogueContainsSeededFoods() { assertEquals(10, catalogue.count()); }
    public void testSearchIsCaseInsensitive() {
        List<FoodCatalogueModule.Food> found = catalogue.search("bAnAnA", "", "name");
        assertEquals(1, found.size()); assertEquals("Banana", found.get(0).getName());
    }
    public void testFilterByCategory() {
        List<FoodCatalogueModule.Food> found = catalogue.search("", "Fruits", "name");
        assertEquals(2, found.size());
        for (FoodCatalogueModule.Food f : found) assertEquals("Fruits", f.getCategory());
    }
    public void testSortCaloriesAscending() {
        List<FoodCatalogueModule.Food> found = catalogue.search("", "", "calories");
        for (int i = 1; i < found.size(); i++) assertTrue(found.get(i - 1).getCalories() <= found.get(i).getCalories());
    }
    public void testSortProteinDescending() {
        List<FoodCatalogueModule.Food> found = catalogue.search("", "", "protein");
        for (int i = 1; i < found.size(); i++) assertTrue(found.get(i - 1).getProtein() >= found.get(i).getProtein());
    }
    public void testUnmatchedSearchReturnsEmptyList() { assertEquals(0, catalogue.search("not-a-food", "", "name").size()); }
    public void testCombinedQueryAndCategoryFilter() {
        List<FoodCatalogueModule.Food> found = catalogue.search("a", "Fruits", "name");
        assertEquals(2, found.size());
    }
}
