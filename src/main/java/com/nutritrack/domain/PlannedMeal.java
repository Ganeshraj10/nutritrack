package com.nutritrack.domain;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="planned_meals")
public class PlannedMeal {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private LocalDate planDate;
    @Column(nullable=false,length=20) private String mealType;
    @Column(nullable=false,length=100) private String foodName;
    @Column(nullable=false) private double calories;
    @Column(nullable=false) private double protein;
    @Column(nullable=false) private double carbohydrates;
    @Column(nullable=false) private double fat;
    protected PlannedMeal() {}
    public PlannedMeal(LocalDate planDate,String mealType,String foodName,double calories,double protein,double carbohydrates,double fat){this.planDate=planDate;this.mealType=mealType;this.foodName=foodName;this.calories=calories;this.protein=protein;this.carbohydrates=carbohydrates;this.fat=fat;}
    public Long getId(){return id;} public void setId(Long v){id=v;}
    public LocalDate getPlanDate(){return planDate;} public void setPlanDate(LocalDate v){planDate=v;}
    public String getMealType(){return mealType;} public void setMealType(String v){mealType=v;}
    public String getFoodName(){return foodName;} public void setFoodName(String v){foodName=v;}
    public double getCalories(){return calories;} public void setCalories(double v){calories=v;}
    public double getProtein(){return protein;} public void setProtein(double v){protein=v;}
    public double getCarbohydrates(){return carbohydrates;} public void setCarbohydrates(double v){carbohydrates=v;}
    public double getFat(){return fat;} public void setFat(double v){fat=v;}
}
