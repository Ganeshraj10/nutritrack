package com.nutritrack.config;

import com.nutritrack.domain.*;
import com.nutritrack.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.time.LocalDate;
import java.util.List;

@Configuration
public class DemoDataInitializer {
    @Bean
    CommandLineRunner seedDemoData(FoodItemRepository foodItems, NutritionGoalRepository goals,
                                   FoodEntryRepository diary, WaterEntryRepository water, PlannedMealRepository plans) {
        return args -> {
            if (foodItems.count() == 0) {
                List<FoodItem> foods = List.of(
                    f("Idli","South Indian","2 pieces",130,4,27,0.5), f("Plain dosa","South Indian","1 medium",168,4,29,4),
                    f("Masala dosa","South Indian","1 medium",280,6,39,11), f("Cooked rice","Grains","1 cup",205,4.3,45,0.4),
                    f("Oats","Grains","40 g",152,5.3,27,3), f("Whole wheat chapati","Grains","1 piece",104,3.5,18,2.5),
                    f("Boiled egg","Protein","1 large",78,6.3,0.6,5.3), f("Scrambled egg","Protein","2 eggs",182,12,2,14),
                    f("Chicken breast","Protein","100 g",165,31,0,3.6), f("Tofu","Protein","100 g",76,8,1.9,4.8),
                    f("Paneer","Protein","100 g",265,18,1.2,20), f("Cooked lentils","Legumes","1 cup",230,18,40,0.8),
                    f("Chickpeas","Legumes","1 cup",269,14.5,45,4.2), f("Greek yogurt","Dairy","170 g",100,17,6,0.7),
                    f("Milk","Dairy","250 ml",122,8,12,5), f("Curd","Dairy","150 g",92,5,7,4),
                    f("Banana","Fruit","1 medium",105,1.3,27,0.4), f("Apple","Fruit","1 medium",95,0.5,25,0.3),
                    f("Orange","Fruit","1 medium",62,1.2,15.4,0.2), f("Mango","Fruit","1 cup",99,1.4,25,0.6),
                    f("Almonds","Nuts & seeds","28 g",164,6,6,14), f("Peanut butter","Nuts & seeds","1 tbsp",94,4,3,8),
                    f("Spinach","Vegetables","1 cup",23,2.9,3.6,0.4), f("Broccoli","Vegetables","1 cup",55,3.7,11,0.6),
                    f("Carrot","Vegetables","1 medium",25,0.6,6,0.1), f("Tomato","Vegetables","1 medium",22,1.1,4.8,0.2),
                    f("Avocado","Fruit","half fruit",120,1.5,6.4,11), f("Whole wheat bread","Grains","2 slices",160,8,28,2),
                    f("Salmon","Protein","100 g",208,20,0,13), f("Tuna","Protein","100 g",132,29,0,1),
                    f("Sweet potato","Vegetables","1 medium",112,2,26,0.1), f("Quinoa","Grains","1 cup",222,8,39,3.6),
                    f("Brown rice","Grains","1 cup",216,5,45,1.8), f("Mixed nuts","Nuts & seeds","30 g",180,5,6,16),
                    f("Strawberries","Fruit","1 cup",49,1,12,0.5), f("Blueberries","Fruit","1 cup",84,1.1,21,0.5),
                    f("Cucumber","Vegetables","1 cup",16,0.7,3.8,0.1), f("Sweet corn","Vegetables","1 cup",143,5,31,2.2),
                    f("Idiyappam","South Indian","2 pieces",150,2,34,0.4), f("Sambar","South Indian","1 cup",110,5,17,2)
                );
                foodItems.saveAll(foods);
            }
            if (!goals.existsById(1L)) goals.save(new NutritionGoal(1L, 2200, 120, 250, 70, 2500));
            if (diary.count() == 0) {
                LocalDate today = LocalDate.now();
                diary.saveAll(List.of(
                    new FoodEntry("Oats with milk","Breakfast",1,"bowl",320,15,48,8,today,"Demo entry"),
                    new FoodEntry("Banana","Snack",1,"piece",105,1.3,27,0.4,today,"Demo entry"),
                    new FoodEntry("Rice, dal and vegetables","Lunch",1,"plate",510,18,82,12,today,"Demo entry"),
                    new FoodEntry("Egg dosa","Breakfast",1,"serving",270,11,32,10,today.minusDays(1),"Demo entry"),
                    new FoodEntry("Paneer rice bowl","Lunch",1,"bowl",560,24,70,18,today.minusDays(2),"Demo entry"),
                    new FoodEntry("Chicken and quinoa","Dinner",1,"plate",470,38,45,12,today.minusDays(3),"Demo entry"),
                    new FoodEntry("Idli and sambar","Breakfast",1,"serving",260,9,45,4,today.minusDays(4),"Demo entry"),
                    new FoodEntry("Salmon with vegetables","Dinner",1,"plate",530,34,40,22,today.minusDays(5),"Demo entry"),
                    new FoodEntry("Lentil bowl","Lunch",1,"bowl",430,22,63,9,today.minusDays(6),"Demo entry")
                ));
            }
            if (water.count() == 0) {
                LocalDate today = LocalDate.now();
                water.saveAll(List.of(new WaterEntry(300,today,"Morning"),new WaterEntry(250,today,"After breakfast"),new WaterEntry(400,today,"Afternoon"),new WaterEntry(350,today.minusDays(1),"Demo history")));
            }
            if (plans.count() == 0) {
                LocalDate today = LocalDate.now();
                plans.saveAll(List.of(new PlannedMeal(today,"Breakfast","Oats with milk",320,15,48,8),new PlannedMeal(today,"Lunch","Rice, dal and vegetables",510,18,82,12),new PlannedMeal(today.plusDays(1),"Breakfast","Idli and sambar",260,9,45,4)));
            }
        };
    }
    private FoodItem f(String name,String category,String serving,double kcal,double protein,double carbs,double fat){return new FoodItem(name,category,serving,kcal,protein,carbs,fat);}
}
