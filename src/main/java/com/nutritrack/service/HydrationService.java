package com.nutritrack.service;

import com.nutritrack.domain.WaterEntry;
import com.nutritrack.repository.WaterEntryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class HydrationService {
    private final WaterEntryRepository repository;
    public HydrationService(WaterEntryRepository repository) { this.repository = repository; }
    @Transactional
    public WaterEntry add(int amountMl, LocalDate date, String notes) {
        if (amountMl < 1 || amountMl > 5000) throw new IllegalArgumentException("Water quantity must be between 1 and 5,000 ml");
        if (date == null) throw new IllegalArgumentException("Entry date is required");
        if (notes != null && notes.length() > 160) throw new IllegalArgumentException("Notes must be at most 160 characters");
        return repository.save(new WaterEntry(amountMl, date, notes == null ? "" : notes.trim()));
    }
    public List<WaterEntry> getForDate(LocalDate date) {
        if (date == null) throw new IllegalArgumentException("Date is required");
        return repository.findByEntryDateOrderByCreatedAtDesc(date);
    }
    public int totalForDate(LocalDate date) { return getForDate(date).stream().mapToInt(WaterEntry::getAmountMl).sum(); }
    @Transactional
    public void delete(Long id) {
        if (id == null || !repository.existsById(id)) throw new NoSuchElementException("Water entry not found: " + id);
        repository.deleteById(id);
    }
}
