import java.time.Duration;
import java.time.OffsetDateTime;

public class Statistics {
    private int totalTraffic = 0;
    private OffsetDateTime minTime = null;
    private OffsetDateTime maxTime = null;

    public void addEntry(LogEntry entry) {
        totalTraffic += entry.getDataSize();

        if (minTime == null || entry.getTimestamp().isBefore(minTime)) {
            minTime = entry.getTimestamp();
        }

        if (maxTime == null || entry.getTimestamp().isAfter(maxTime)) {
            maxTime = entry.getTimestamp();
        }
    }

    public double getTrafficRate() {
        if (minTime == null || maxTime == null) {
            return 0;
        }

        long hours = Duration.between(minTime, maxTime).toHours();
        if (hours == 0) {
            hours = 1; // Чтобы избежать деления на ноль
        }

        return (double) totalTraffic / hours;
    }
}