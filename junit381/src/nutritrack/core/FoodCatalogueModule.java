package nutritrack.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/** Plain-Java in-memory food catalogue and search rules for the JUnit 3.8.1 exercise. */
public final class FoodCatalogueModule {
    public static final class Food {
        private final String name, category;
        private final double calories, protein;
        public Food(String name, String category, double calories, double protein) {
            this.name = name; this.category = category; this.calories = calories; this.protein = protein;
        }
        public String getName() { return name; }
        public String getCategory() { return category; }
        public double getCalories() { return calories; }
        public double getProtein() { return protein; }
    }

    private final List<Food> foods = new ArrayList<Food>();
    public FoodCatalogueModule() {
        foods.add(new Food("Oats", "Grains", 150, 5.0));
        foods.add(new Food("Brown Rice", "Grains", 216, 5.0));
        foods.add(new Food("Banana", "Fruits", 105, 1.3));
        foods.add(new Food("Apple", "Fruits", 95, 0.5));
        foods.add(new Food("Boiled Egg", "Protein", 78, 6.3));
        foods.add(new Food("Tofu", "Protein", 144, 17.0));
        foods.add(new Food("Milk", "Dairy", 122, 8.0));
        foods.add(new Food("Yogurt", "Dairy", 100, 5.7));
        foods.add(new Food("Spinach", "Vegetables", 23, 2.9));
        foods.add(new Food("Carrot", "Vegetables", 41, 0.9));
    }

    public List<Food> search(String query, String category, String sortBy) {
        final String q = query == null ? "" : query.trim().toLowerCase();
        final String c = category == null ? "" : category.trim();
        List<Food> result = new ArrayList<Food>();
        for (Food food : foods) {
            if (!q.isEmpty() && !food.getName().toLowerCase().contains(q)) continue;
            if (!c.isEmpty() && !food.getCategory().equalsIgnoreCase(c)) continue;
            result.add(food);
        }
        if ("calories".equalsIgnoreCase(sortBy)) {
            Collections.sort(result, new Comparator<Food>() {
                public int compare(Food a, Food b) { return Double.compare(a.getCalories(), b.getCalories()); }
            });
        } else if ("protein".equalsIgnoreCase(sortBy)) {
            Collections.sort(result, new Comparator<Food>() {
                public int compare(Food a, Food b) { return Double.compare(b.getProtein(), a.getProtein()); }
            });
        } else {
            Collections.sort(result, new Comparator<Food>() {
                public int compare(Food a, Food b) { return a.getName().compareToIgnoreCase(b.getName()); }
            });
        }
        return Collections.unmodifiableList(result);
    }

    public int count() { return foods.size(); }
}
