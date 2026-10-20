package com.nutritrack.repository;
import com.nutritrack.domain.WaterEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;
public interface WaterEntryRepository extends JpaRepository<WaterEntry, Long> {
    List<WaterEntry> findByEntryDateOrderByCreatedAtDesc(LocalDate date);
}
