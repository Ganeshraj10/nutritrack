package com.nutritrack.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "nutrition_goals")
public class NutritionGoal {
    @Id private Long id;
    @Column(nullable=false) private int dailyCalories;
    @Column(nullable=false) private int proteinGrams;
    @Column(nullable=false) private int carbohydrateGrams;
    @Column(nullable=false) private int fatGrams;
    @Column(nullable=false) private int waterMl;
    protected NutritionGoal() {}
    public NutritionGoal(Long id, int dailyCalories, int proteinGrams, int carbohydrateGrams, int fatGrams, int waterMl) {
        this.id=id; this.dailyCalories=dailyCalories; this.proteinGrams=proteinGrams; this.carbohydrateGrams=carbohydrateGrams; this.fatGrams=fatGrams; this.waterMl=waterMl;
    }
    public Long getId(){return id;} public void setId(Long v){id=v;}
    public int getDailyCalories(){return dailyCalories;} public void setDailyCalories(int v){dailyCalories=v;}
    public int getProteinGrams(){return proteinGrams;} public void setProteinGrams(int v){proteinGrams=v;}
    public int getCarbohydrateGrams(){return carbohydrateGrams;} public void setCarbohydrateGrams(int v){carbohydrateGrams=v;}
    public int getFatGrams(){return fatGrams;} public void setFatGrams(int v){fatGrams=v;}
    public int getWaterMl(){return waterMl;} public void setWaterMl(int v){waterMl=v;}
}
