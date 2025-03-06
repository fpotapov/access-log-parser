import java.io.*;
import java.util.Scanner;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Введите путь к файлу: ");
        String filePath = scanner.nextLine();

        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("Файл не существует.");
            return;
        }

        Statistics statistics = new Statistics();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                try {
                    LogEntry entry = new LogEntry(line);
                    statistics.addEntry(entry);
                } catch (IllegalArgumentException e) {
                    System.out.println("Ошибка при обработке строки: " + e.getMessage());
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }

        // Вывод результатов
        System.out.printf("Средний объём трафика за час: %.2f байт%n", statistics.getTrafficRate());

        System.out.println("Список страниц с кодом ответа 200:");
        for (String page : statistics.getPages()) {
            System.out.println("- " + page);
        }

        System.out.println("Статистика операционных систем:");
        Map<String, Double> osStats = statistics.getOsStatistics();
        for (Map.Entry<String, Double> entry : osStats.entrySet()) {
            System.out.printf("- %s: %.2f%%%n", entry.getKey(), entry.getValue() * 100);
        }
    }
}