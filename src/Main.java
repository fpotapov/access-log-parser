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
        System.out.printf("Среднее количество посещений за час: %.2f%n", statistics.getAverageVisitsPerHour());
        System.out.printf("Среднее количество ошибочных запросов за час: %.2f%n", statistics.getAverageErrorRequestsPerHour());
        System.out.printf("Средняя посещаемость одним пользователем: %.2f%n", statistics.getAverageVisitsPerUser());
    }
}