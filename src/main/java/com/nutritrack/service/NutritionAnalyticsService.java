package com.nutritrack.service;

import com.nutritrack.domain.FoodEntry;
import com.nutritrack.domain.NutritionGoal;
import com.nutritrack.repository.FoodEntryRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class NutritionAnalyticsService {
    private final FoodEntryRepository foodRepository;
    private final NutritionGoalService goalService;
    public NutritionAnalyticsService(FoodEntryRepository foodRepository, NutritionGoalService goalService) {
        this.foodRepository = foodRepository; this.goalService = goalService;
    }
    public DailySummary dailySummary(LocalDate date) {
        if (date == null) throw new IllegalArgumentException("Date is required");
        List<FoodEntry> entries = foodRepository.findByEntryDateOrderByCreatedAtDesc(date);
        double calories = entries.stream().mapToDouble(FoodEntry::getCalories).sum();
        double protein = entries.stream().mapToDouble(FoodEntry::getProtein).sum();
        double carbs = entries.stream().mapToDouble(FoodEntry::getCarbohydrates).sum();
        double fat = entries.stream().mapToDouble(FoodEntry::getFat).sum();
        NutritionGoal goals = goalService.getGoals();
        double percent = goals.getDailyCalories() == 0 ? 0 : calories / goals.getDailyCalories() * 100.0;
        return new DailySummary(date, round(calories), round(protein), round(carbs), round(fat), goals.getDailyCalories(),
            goals.getProteinGrams(), goals.getCarbohydrateGrams(), goals.getFatGrams(), round(percent), entries.size());
    }
    public List<DailyTrendPoint> trend(LocalDate ending, int days) {
        if (ending == null) throw new IllegalArgumentException("End date is required");
        if (days < 1 || days > 90) throw new IllegalArgumentException("Trend range must be between 1 and 90 days");
        List<DailyTrendPoint> points = new ArrayList<>();
        for (int offset = days - 1; offset >= 0; offset--) {
            LocalDate date = ending.minusDays(offset);
            double calories = foodRepository.findByEntryDateOrderByCreatedAtDesc(date).stream().mapToDouble(FoodEntry::getCalories).sum();
            points.add(new DailyTrendPoint(date, date.getDayOfWeek().getDisplayName(TextStyle.SHORT, Locale.ENGLISH), round(calories)));
        }
        return points;
    }
    public double averageCalories(LocalDate start, LocalDate end) {
        if (start == null || end == null || end.isBefore(start)) throw new IllegalArgumentException("A valid date range is required");
        double total = foodRepository.findByEntryDateBetweenOrderByCreatedAtDesc(start, end).stream().mapToDouble(FoodEntry::getCalories).sum();
        long days = java.time.temporal.ChronoUnit.DAYS.between(start, end) + 1;
        return round(total / days);
    }
    private double round(double value) { return Math.round(value * 10.0) / 10.0; }
}
