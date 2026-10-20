package com.nutritrack.service;
import java.time.LocalDate;
public record DailyTrendPoint(LocalDate date, String label, double calories) {}
