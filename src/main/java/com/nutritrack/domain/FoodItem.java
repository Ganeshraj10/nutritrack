package com.nutritrack.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "food_catalogue")
public class FoodItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @Column(nullable = false, length = 100) private String name;
    @Column(nullable = false, length = 40) private String category;
    @Column(nullable = false, length = 30) private String servingSize;
    @Column(nullable = false) private double calories;
    @Column(nullable = false) private double protein;
    @Column(nullable = false) private double carbohydrates;
    @Column(nullable = false) private double fat;
    protected FoodItem() {}
    public FoodItem(String name, String category, String servingSize, double calories, double protein, double carbohydrates, double fat) {
        this.name=name; this.category=category; this.servingSize=servingSize; this.calories=calories;
        this.protein=protein; this.carbohydrates=carbohydrates; this.fat=fat;
    }
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getName(){return name;} public void setName(String v){name=v;}
    public String getCategory(){return category;} public void setCategory(String v){category=v;}
    public String getServingSize(){return servingSize;} public void setServingSize(String v){servingSize=v;}
    public double getCalories(){return calories;} public void setCalories(double v){calories=v;}
    public double getProtein(){return protein;} public void setProtein(double v){protein=v;}
    public double getCarbohydrates(){return carbohydrates;} public void setCarbohydrates(double v){carbohydrates=v;}
    public double getFat(){return fat;} public void setFat(double v){fat=v;}
}
