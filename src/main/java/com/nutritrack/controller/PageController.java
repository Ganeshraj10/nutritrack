package com.nutritrack.controller;

import com.nutritrack.domain.*;
import com.nutritrack.repository.PlannedMealRepository;
import com.nutritrack.service.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDate;
import java.util.List;

@Controller
public class PageController {
    private final FoodDiaryService diary;
    private final FoodCatalogueService catalogue;
    private final NutritionGoalService goals;
    private final HydrationService hydration;
    private final NutritionAnalyticsService analytics;
    private final PlannedMealRepository plans;

    public PageController(FoodDiaryService diary, FoodCatalogueService catalogue, NutritionGoalService goals,
                          HydrationService hydration, NutritionAnalyticsService analytics, PlannedMealRepository plans) {
        this.diary=diary; this.catalogue=catalogue; this.goals=goals; this.hydration=hydration; this.analytics=analytics; this.plans=plans;
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        LocalDate today=LocalDate.now();
        var summary=analytics.dailySummary(today); var weekly=analytics.trend(today,7); var goal=goals.getGoals();
        int water=hydration.totalForDate(today);
        model.addAttribute("summary",summary); addSummaryModel(model, summary); model.addAttribute("remainingCalories",Math.max(0, Math.round(summary.calorieGoal()-summary.calories()))); model.addAttribute("weekly",weekly);
        model.addAttribute("weeklyMax",Math.max(1,weekly.stream().mapToDouble(DailyTrendPoint::calories).max().orElse(1)));
        model.addAttribute("goals",goal); model.addAttribute("waterTotal",water);
        model.addAttribute("waterPercent",Math.min(100,Math.round(water*100.0/Math.max(1,goal.getWaterMl()))));
        model.addAttribute("recentEntries",diary.getForDate(today)); model.addAttribute("today",today);
        return "dashboard";
    }

    @GetMapping("/diary")
    public String diary(@RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate date, Model model) {
        LocalDate selected=date==null?LocalDate.now():date;
        model.addAttribute("selectedDate",selected); model.addAttribute("entries",diary.getForDate(selected));
        DailySummary daily=analytics.dailySummary(selected); model.addAttribute("summary",daily); addSummaryModel(model,daily); model.addAttribute("editingEntry",null);
        return "diary";
    }
    @GetMapping("/diary/edit/{id}")
    public String editDiary(@PathVariable Long id, Model model, RedirectAttributes redirect) {
        try {
            FoodEntry entry=diary.getAll().stream().filter(e->e.getId().equals(id)).findFirst().orElseThrow();
            LocalDate selected=entry.getEntryDate();
            model.addAttribute("selectedDate",selected); model.addAttribute("entries",diary.getForDate(selected));
            DailySummary daily=analytics.dailySummary(selected); model.addAttribute("summary",daily); addSummaryModel(model,daily); model.addAttribute("editingEntry",entry);
            return "diary";
        } catch (Exception ex) { redirect.addFlashAttribute("errorMessage","That food entry could not be found."); return "redirect:/diary"; }
    }
    @PostMapping("/diary/add")
    public String addFood(@RequestParam String name,@RequestParam String mealType,@RequestParam double quantity,
                          @RequestParam String unit,@RequestParam double calories,@RequestParam double protein,
                          @RequestParam double carbohydrates,@RequestParam double fat,
                          @RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate entryDate,
                          @RequestParam(required=false) String notes, RedirectAttributes redirect) {
        try { diary.add(name,mealType,quantity,unit,calories,protein,carbohydrates,fat,entryDate==null?LocalDate.now():entryDate,notes); redirect.addFlashAttribute("successMessage","Food entry added to your diary."); }
        catch (Exception ex) { redirect.addFlashAttribute("errorMessage",safeMessage(ex)); }
        return "redirect:/diary";
    }
    @PostMapping("/diary/update/{id}")
    public String updateFood(@PathVariable Long id,@RequestParam String name,@RequestParam String mealType,
                             @RequestParam double quantity,@RequestParam String unit,@RequestParam double calories,
                             @RequestParam double protein,@RequestParam double carbohydrates,@RequestParam double fat,
                             @RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate entryDate,
                             @RequestParam(required=false) String notes,RedirectAttributes redirect) {
        try { diary.update(id,name,mealType,quantity,unit,calories,protein,carbohydrates,fat,entryDate,notes); redirect.addFlashAttribute("successMessage","Food entry updated."); }
        catch (Exception ex) { redirect.addFlashAttribute("errorMessage",safeMessage(ex)); }
        return "redirect:/diary";
    }
    @PostMapping("/diary/delete/{id}")
    public String deleteFood(@PathVariable Long id,RedirectAttributes redirect) {
        try { diary.delete(id); redirect.addFlashAttribute("successMessage","Food entry removed."); }
        catch(Exception ex){redirect.addFlashAttribute("errorMessage",safeMessage(ex));}
        return "redirect:/diary";
    }

    @GetMapping("/foods")
    public String foods(@RequestParam(defaultValue="") String q,@RequestParam(defaultValue="") String category,
                       @RequestParam(defaultValue="name") String sort,Model model) {
        model.addAttribute("foods",catalogue.search(q,category,sort)); model.addAttribute("q",q);
        model.addAttribute("category",category); model.addAttribute("sort",sort); model.addAttribute("categories",catalogue.categories());
        return "foods";
    }
    @PostMapping("/foods/add-to-diary")
    public String addCatalogueFood(@RequestParam Long foodId,@RequestParam(defaultValue="1") double quantity,
                                   @RequestParam(defaultValue="Snack") String mealType,RedirectAttributes redirect) {
        try {
            FoodItem item=catalogue.getById(foodId);
            if(!Double.isFinite(quantity)||quantity<=0||quantity>20) throw new IllegalArgumentException("Serving quantity must be between 0 and 20");
            diary.add(item.getName(),mealType,quantity,"serving",round(item.getCalories()*quantity),round(item.getProtein()*quantity),
                round(item.getCarbohydrates()*quantity),round(item.getFat()*quantity),LocalDate.now(),"Added from food catalogue");
            redirect.addFlashAttribute("successMessage",item.getName()+" added to your diary.");
        } catch(Exception ex){redirect.addFlashAttribute("errorMessage",safeMessage(ex));}
        return "redirect:/foods";
    }

    @GetMapping("/goals") public String goals(Model model){model.addAttribute("goals",goals.getGoals());return "goals";}
    @PostMapping("/goals")
    public String updateGoals(@RequestParam int dailyCalories,@RequestParam int proteinGrams,@RequestParam int carbohydrateGrams,
                              @RequestParam int fatGrams,@RequestParam int waterMl,RedirectAttributes redirect){
        try{goals.update(dailyCalories,proteinGrams,carbohydrateGrams,fatGrams,waterMl);redirect.addFlashAttribute("successMessage","Your daily targets have been updated.");}
        catch(Exception ex){redirect.addFlashAttribute("errorMessage",safeMessage(ex));}
        return "redirect:/goals";
    }

    @GetMapping("/hydration")
    public String hydration(@RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate date,Model model){
        LocalDate selected=date==null?LocalDate.now():date;int total=hydration.totalForDate(selected);NutritionGoal goal=goals.getGoals();
        model.addAttribute("selectedDate",selected);model.addAttribute("entries",hydration.getForDate(selected));model.addAttribute("waterTotal",total);
        model.addAttribute("waterGoal",goal.getWaterMl());model.addAttribute("waterPercent",Math.min(100,Math.round(total*100.0/Math.max(1,goal.getWaterMl()))));return "hydration";
    }
    @PostMapping("/hydration/add")
    public String addWater(@RequestParam int amountMl,@RequestParam(required=false) @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate entryDate,
                           @RequestParam(required=false) String notes,RedirectAttributes redirect){
        try{hydration.add(amountMl,entryDate==null?LocalDate.now():entryDate,notes);redirect.addFlashAttribute("successMessage","Water intake recorded.");}
        catch(Exception ex){redirect.addFlashAttribute("errorMessage",safeMessage(ex));}return "redirect:/hydration";
    }
    @PostMapping("/hydration/delete/{id}")
    public String deleteWater(@PathVariable Long id,RedirectAttributes redirect){try{hydration.delete(id);redirect.addFlashAttribute("successMessage","Water entry removed.");}catch(Exception ex){redirect.addFlashAttribute("errorMessage",safeMessage(ex));}return "redirect:/hydration";}

    @GetMapping("/analytics")
    public String analytics(@RequestParam(defaultValue="7") int range,Model model){
        int days=range==30?30:7;LocalDate end=LocalDate.now();LocalDate start=end.minusDays(days-1);
        var points=analytics.trend(end,days);double max=points.stream().mapToDouble(DailyTrendPoint::calories).max().orElse(1);
        model.addAttribute("range",days);model.addAttribute("points",points);model.addAttribute("chartMax",Math.max(max,1));
        model.addAttribute("averageCalories",analytics.averageCalories(start,end)); DailySummary daily=analytics.dailySummary(end); model.addAttribute("summary",daily); addSummaryModel(model,daily);
        model.addAttribute("days",days);model.addAttribute("startDate",start);model.addAttribute("endDate",end);return "analytics";
    }
    @GetMapping("/analytics/export.csv")
    @ResponseBody
    public String exportAnalytics(){
        StringBuilder out=new StringBuilder("date,food,meal,calories,protein_g,carbohydrates_g,fat_g\n");
        diary.getAll().stream().sorted((a,b)->a.getEntryDate().compareTo(b.getEntryDate())).forEach(e->out.append(csv(e.getEntryDate().toString())).append(',').append(csv(e.getName())).append(',').append(csv(e.getMealType())).append(',').append(e.getCalories()).append(',').append(e.getProtein()).append(',').append(e.getCarbohydrates()).append(',').append(e.getFat()).append('\n'));
        return out.toString();
    }

    @GetMapping("/planner")
    public String planner(Model model){LocalDate start=LocalDate.now();var weeklyPlans=plans.findByPlanDateBetweenOrderByPlanDateAsc(start,start.plusDays(6));model.addAttribute("startDate",start);model.addAttribute("endDate",start.plusDays(6));model.addAttribute("plans",weeklyPlans);model.addAttribute("plannedDates",weeklyPlans.stream().map(PlannedMeal::getPlanDate).collect(java.util.stream.Collectors.toSet()));return "planner";}
    @PostMapping("/planner/add")
    public String addPlan(@RequestParam @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) LocalDate planDate,@RequestParam String mealType,
                          @RequestParam String foodName,@RequestParam double calories,@RequestParam double protein,
                          @RequestParam double carbohydrates,@RequestParam double fat,RedirectAttributes redirect){
        try{
            if(planDate.isBefore(LocalDate.now())||planDate.isAfter(LocalDate.now().plusDays(30)))throw new IllegalArgumentException("Choose a plan date from today through the next 30 days");
            if(foodName==null||foodName.isBlank()||foodName.trim().length()>100)throw new IllegalArgumentException("Food name is required");
            if(!List.of("Breakfast","Lunch","Dinner","Snack").contains(mealType))throw new IllegalArgumentException("Choose a valid meal category");
            if(!Double.isFinite(calories)||calories<0||calories>10000||!Double.isFinite(protein)||protein<0||protein>1000||!Double.isFinite(carbohydrates)||carbohydrates<0||carbohydrates>1500||!Double.isFinite(fat)||fat<0||fat>1000)throw new IllegalArgumentException("Nutrient values are outside the accepted range");
            plans.save(new PlannedMeal(planDate,mealType,foodName.trim(),calories,protein,carbohydrates,fat));redirect.addFlashAttribute("successMessage","Meal added to your plan.");
        }catch(Exception ex){redirect.addFlashAttribute("errorMessage",safeMessage(ex));}return "redirect:/planner";
    }
    @PostMapping("/planner/delete/{id}") public String deletePlan(@PathVariable Long id,RedirectAttributes redirect){if(plans.existsById(id)){plans.deleteById(id);redirect.addFlashAttribute("successMessage","Planned meal removed.");}else redirect.addFlashAttribute("errorMessage","Planned meal not found.");return "redirect:/planner";}
    @GetMapping("/settings") public String settings(){return "settings";}

    private void addSummaryModel(Model model, DailySummary summary) {
        double proteinEnergy=summary.protein()*4.0, carbEnergy=summary.carbohydrates()*4.0, fatEnergy=summary.fat()*9.0;
        double total=proteinEnergy+carbEnergy+fatEnergy;
        int p=total==0?0:(int)Math.round(proteinEnergy*100.0/total);
        int c=total==0?0:(int)Math.round(carbEnergy*100.0/total);
        int f=total==0?0:Math.max(0,100-p-c);
        model.addAttribute("macroTotal",Math.round(total)); model.addAttribute("proteinShare",p); model.addAttribute("carbShare",c); model.addAttribute("fatShare",f); model.addAttribute("carbsEnd",p+c);
        model.addAttribute("proteinPct",summary.proteinGoal()==0?0:Math.min(100,Math.round(summary.protein()*100.0/summary.proteinGoal())));
        model.addAttribute("carbPct",summary.carbohydrateGoal()==0?0:Math.min(100,Math.round(summary.carbohydrates()*100.0/summary.carbohydrateGoal())));
        model.addAttribute("fatPct",summary.fatGoal()==0?0:Math.min(100,Math.round(summary.fat()*100.0/summary.fatGoal())));
    }
    private String safeMessage(Exception ex){String message=ex.getMessage();return message==null||message.isBlank()?"The request could not be completed. Check the values and try again.":message;}
    private double round(double n){return Math.round(n*10.0)/10.0;}
    private String csv(String value){return "\""+(value==null?"":value.replace("\"","\"\""))+"\"";}
}
