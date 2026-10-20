package nutritrack.core;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/** Aggregates diary records into daily and date-range analytics. */
public final class NutritionAnalyticsModule {
    public double dailyCalories(List<NutritionRecord> records, LocalDate date) {
        if (records == null || date == null) throw new IllegalArgumentException("Records and date are required");
        double total = 0;
        for (NutritionRecord record : records) if (date.equals(record.getDate())) total += record.getCalories();
        return total;
    }
    /** Average across all calendar days in the inclusive range; days with no entries count as zero. */
    public double averageDailyCalories(List<NutritionRecord> records, LocalDate start, LocalDate end) {
        if (records == null || start == null || end == null || end.isBefore(start))
            throw new IllegalArgumentException("A valid inclusive date range is required");
        long days = ChronoUnit.DAYS.between(start, end) + 1;
        double sum = 0;
        for (NutritionRecord record : records) if (!record.getDate().isBefore(start) && !record.getDate().isAfter(end)) sum += record.getCalories();
        return sum / days;
    }
    public double highestDailyCalories(List<NutritionRecord> records, LocalDate start, LocalDate end) {
        validateRange(records, start, end); double max = 0;
        for (LocalDate d = start; !d.isAfter(end); d = d.plusDays(1)) max = Math.max(max, dailyCalories(records, d));
        return max;
    }
    public double lowestDailyCalories(List<NutritionRecord> records, LocalDate start, LocalDate end) {
        validateRange(records, start, end); double min = Double.MAX_VALUE;
        for (LocalDate d = start; !d.isAfter(end); d = d.plusDays(1)) min = Math.min(min, dailyCalories(records, d));
        return min == Double.MAX_VALUE ? 0 : min;
    }
    private void validateRange(List<NutritionRecord> records, LocalDate start, LocalDate end) {
        if (records == null || start == null || end == null || end.isBefore(start)) throw new IllegalArgumentException("A valid date range is required");
    }
}
