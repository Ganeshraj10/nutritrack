package com.nutritrack.service;

import com.nutritrack.domain.FoodItem;
import com.nutritrack.repository.FoodItemRepository;
import org.springframework.stereotype.Service;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class FoodCatalogueService {
    private final FoodItemRepository repository;
    public FoodCatalogueService(FoodItemRepository repository) { this.repository = repository; }
    public List<FoodItem> search(String query, String category, String sort) {
        String q = query == null ? "" : query.trim().toLowerCase();
        String c = category == null ? "" : category.trim();
        Comparator<FoodItem> comparator = switch (sort == null ? "name" : sort) {
            case "calories" -> Comparator.comparingDouble(FoodItem::getCalories);
            case "protein" -> Comparator.comparingDouble(FoodItem::getProtein).reversed();
            default -> Comparator.comparing(FoodItem::getName, String.CASE_INSENSITIVE_ORDER);
        };
        return repository.findAll().stream()
            .filter(food -> q.isEmpty() || food.getName().toLowerCase().contains(q))
            .filter(food -> c.isEmpty() || food.getCategory().equalsIgnoreCase(c))
            .sorted(comparator).toList();
    }
    public List<String> categories() { return repository.findAll().stream().map(FoodItem::getCategory).distinct().sorted().toList(); }
    public FoodItem getById(Long id) { return repository.findById(id).orElseThrow(() -> new NoSuchElementException("Food catalogue item not found: " + id)); }
    public long count() { return repository.count(); }
}
