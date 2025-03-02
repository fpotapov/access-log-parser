import java.io.*;
import java.util.Scanner;

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
                LogEntry entry = new LogEntry(line);
                statistics.addEntry(entry);
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        }

        System.out.printf("Средний объём трафика за час: %.2f байт%n", statistics.getTrafficRate());
    }
}