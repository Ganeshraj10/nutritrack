package com.nutritrack.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "food_entries")
public class FoodEntry {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false, length = 20)
    private String mealType;
    @Column(nullable = false)
    private double quantity;
    @Column(nullable = false, length = 20)
    private String unit;
    @Column(nullable = false)
    private double calories;
    @Column(nullable = false)
    private double protein;
    @Column(nullable = false)
    private double carbohydrates;
    @Column(nullable = false)
    private double fat;
    @Column(nullable = false)
    private LocalDate entryDate;
    @Column(length = 500)
    private String notes;
    @Column(nullable = false)
    private LocalDateTime createdAt;

    protected FoodEntry() {}
    public FoodEntry(String name, String mealType, double quantity, String unit, double calories,
                     double protein, double carbohydrates, double fat, LocalDate entryDate, String notes) {
        this.name = name; this.mealType = mealType; this.quantity = quantity; this.unit = unit;
        this.calories = calories; this.protein = protein; this.carbohydrates = carbohydrates; this.fat = fat;
        this.entryDate = entryDate; this.notes = notes; this.createdAt = LocalDateTime.now();
    }
    @PrePersist void beforeInsert() { if (createdAt == null) createdAt = LocalDateTime.now(); }
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getMealType() { return mealType; }
    public void setMealType(String mealType) { this.mealType = mealType; }
    public double getQuantity() { return quantity; }
    public void setQuantity(double quantity) { this.quantity = quantity; }
    public String getUnit() { return unit; }
    public void setUnit(String unit) { this.unit = unit; }
    public double getCalories() { return calories; }
    public void setCalories(double calories) { this.calories = calories; }
    public double getProtein() { return protein; }
    public void setProtein(double protein) { this.protein = protein; }
    public double getCarbohydrates() { return carbohydrates; }
    public void setCarbohydrates(double carbohydrates) { this.carbohydrates = carbohydrates; }
    public double getFat() { return fat; }
    public void setFat(double fat) { this.fat = fat; }
    public LocalDate getEntryDate() { return entryDate; }
    public void setEntryDate(LocalDate entryDate) { this.entryDate = entryDate; }
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
