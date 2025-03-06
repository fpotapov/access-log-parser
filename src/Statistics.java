import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.*;

public class Statistics {
    private Long totalTraffic = 0L;
    private OffsetDateTime minTime = null;
    private OffsetDateTime maxTime = null;

    // Коллекция для хранения адресов страниц с кодом ответа 200
    private final Set<String> pages = new HashSet<>();

    // Коллекция для подсчета частоты встречаемости операционных систем
    private final Map<String, Integer> osCounts = new HashMap<>();
    private Long totalOsCount = 0L; // Общее количество записей с операционными системами

    public void addEntry(LogEntry entry) {
        totalTraffic += entry.getDataSize();

        if (minTime == null || entry.getTimestamp().isBefore(minTime)) {
            minTime = entry.getTimestamp();
        }

        if (maxTime == null || entry.getTimestamp().isAfter(maxTime)) {
            maxTime = entry.getTimestamp();
        }

        // Добавляем страницу, если код ответа 200
        if (entry.getResponseCode() == 200) {
            pages.add(entry.getPath());
        }

        // Подсчитываем операционные системы
        String os = entry.getUserAgent().getOperatingSystem();
        if (os != null && !os.isEmpty()) {
            osCounts.put(os, osCounts.getOrDefault(os, 0) + 1);
            totalOsCount++;
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

    // Метод для получения списка всех страниц с кодом ответа 200
    public Set<String> getPages() {
        return Collections.unmodifiableSet(pages); // Возвращаем неизменяемую копию
    }

    // Метод для получения статистики операционных систем
    public Map<String, Double> getOsStatistics() {
        Map<String, Double> osStats = new HashMap<>();
        for (Map.Entry<String, Integer> entry : osCounts.entrySet()) {
            String os = entry.getKey();
            int count = entry.getValue();
            double share = (double) count / totalOsCount; // Доля операционной системы
            osStats.put(os, share);
        }
        return osStats;
    }
}