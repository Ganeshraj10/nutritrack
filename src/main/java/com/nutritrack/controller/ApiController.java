package com.nutritrack.controller;
import com.nutritrack.domain.FoodItem;
import com.nutritrack.service.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api")
public class ApiController {
    private final NutritionAnalyticsService analytics; private final FoodCatalogueService catalogue; private final FoodDiaryService diary;
    public ApiController(NutritionAnalyticsService analytics,FoodCatalogueService catalogue,FoodDiaryService diary){this.analytics=analytics;this.catalogue=catalogue;this.diary=diary;}
    @GetMapping("/health") public Map<String,String> health(){return Map.of("status","UP","application","NutriTrack Pro");}
    @GetMapping("/summary") public DailySummary summary(@RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate date){return analytics.dailySummary(date==null?LocalDate.now():date);}
    @GetMapping("/foods") public List<FoodItem> foods(@RequestParam(defaultValue="") String q,@RequestParam(defaultValue="") String category,@RequestParam(defaultValue="name") String sort){return catalogue.search(q,category,sort);}
}
