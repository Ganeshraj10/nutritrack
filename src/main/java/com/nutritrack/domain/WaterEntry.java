package com.nutritrack.domain;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "water_entries")
public class WaterEntry {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY) private Long id;
    @Column(nullable=false) private int amountMl;
    @Column(nullable=false) private LocalDate entryDate;
    @Column(length=160) private String notes;
    @Column(nullable=false) private LocalDateTime createdAt;
    protected WaterEntry() {}
    public WaterEntry(int amountMl, LocalDate entryDate, String notes) { this.amountMl=amountMl; this.entryDate=entryDate; this.notes=notes; this.createdAt=LocalDateTime.now(); }
    @PrePersist void beforeInsert(){if(createdAt==null)createdAt=LocalDateTime.now();}
    public Long getId(){return id;} public void setId(Long v){id=v;}
    public int getAmountMl(){return amountMl;} public void setAmountMl(int v){amountMl=v;}
    public LocalDate getEntryDate(){return entryDate;} public void setEntryDate(LocalDate v){entryDate=v;}
    public String getNotes(){return notes;} public void setNotes(String v){notes=v;}
    public LocalDateTime getCreatedAt(){return createdAt;} public void setCreatedAt(LocalDateTime v){createdAt=v;}
}
