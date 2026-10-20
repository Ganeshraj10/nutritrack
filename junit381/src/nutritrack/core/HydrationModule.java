package nutritrack.core;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/** Water-intake tracking rules for the standalone legacy-JUnit module. */
public final class HydrationModule {
    public static final class WaterEntry {
        private final int id, millilitres;
        private final LocalDate date;
        private WaterEntry(int id, int millilitres, LocalDate date) { this.id = id; this.millilitres = millilitres; this.date = date; }
        public int getId() { return id; }
        public int getMillilitres() { return millilitres; }
        public LocalDate getDate() { return date; }
    }
    private final List<WaterEntry> entries = new ArrayList<WaterEntry>();
    private int nextId = 1;

    public WaterEntry addWater(int millilitres, LocalDate date) {
        if (millilitres <= 0 || millilitres > 5000) throw new IllegalArgumentException("Water amount must be between 1 and 5000 ml");
        if (date == null) throw new IllegalArgumentException("Date is required");
        WaterEntry entry = new WaterEntry(nextId++, millilitres, date);
        entries.add(entry); return entry;
    }
    public boolean removeWater(int id) {
        for (int i = 0; i < entries.size(); i++) if (entries.get(i).getId() == id) { entries.remove(i); return true; }
        return false;
    }
    public int dailyTotal(LocalDate date) {
        if (date == null) throw new IllegalArgumentException("Date is required");
        int total = 0; for (WaterEntry entry : entries) if (entry.getDate().equals(date)) total += entry.getMillilitres();
        return total;
    }
    public double progressPercent(LocalDate date, int goalMillilitres) {
        if (goalMillilitres <= 0) throw new IllegalArgumentException("Water goal must be greater than zero");
        return dailyTotal(date) * 100.0 / goalMillilitres;
    }
}
