import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Введите путь к файлу (или введите 'exit' для выхода): ");
            String path = scanner.nextLine();

            // Проверка на выход из программы
            if (path.equalsIgnoreCase("exit")) {
                System.out.println("Программа завершена.");
                break; // Завершает цикл и программу
            }

            // Создаем объект File для проверки пути
            File file = new File(path);

            // Проверяем, существует ли файл
            if (!file.exists()) {
                System.out.println("Файл не существует. Попробуйте снова.");
                continue;
            }

            // Проверяем, является ли путь именно файлом, а не папкой
            if (file.isDirectory()) {
                System.out.println("Указанный путь ведет не к файлу, а к папке. Попробуйте снова.");
                continue;
            }

            try {
                // Переменные для подсчета
                int totalRequests = 0;      // Общее количество запросов
                int googlebotCount = 0;     // Количество запросов от Googlebot
                int yandexBotCount = 0;     // Количество запросов от YandexBot

                // Чтение файла построчно
                FileReader fileReader = new FileReader(file);
                BufferedReader reader = new BufferedReader(fileReader);

                String line;
                while ((line = reader.readLine()) != null) {
                    totalRequests++;

                    // Проверка длины строки
                    if (line.length() > 1024) {
                        throw new RuntimeException("Строка длиннее 1024 символов: " + line);
                    }

                    // Разбор строки на составляющие
                    String[] parts = line.split("\"");
                    if (parts.length < 6) {
                        System.out.println("Некорректная строка: " + line);
                        continue;
                    }

                    // Извлечение User-Agent
                    String userAgent = parts[5].trim();

                    // Анализ User-Agent
                    String botName = extractBotName(userAgent);
                    if ("Googlebot".equals(botName)) {
                        googlebotCount++;
                    } else if ("YandexBot".equals(botName)) {
                        yandexBotCount++;
                    }
                }

                // Закрываем ресурсы
                reader.close();
                fileReader.close();

                // Вывод результатов
                System.out.println("Общее количество запросов: " + totalRequests);
                System.out.printf("Доля запросов от Googlebot: %.2f%%%n", (double) googlebotCount / totalRequests * 100);
                System.out.printf("Доля запросов от YandexBot: %.2f%%%n", (double) yandexBotCount / totalRequests * 100);

            } catch (FileNotFoundException e) {
                System.out.println("Файл не найден: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("Ошибка ввода-вывода: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Произошла ошибка: " + e.getMessage());
                e.printStackTrace();
            }
        }

        scanner.close(); // Закрываем Scanner
    }

    /**
     * Метод для извлечения имени бота из User-Agent.
     */
    private static String extractBotName(String userAgent) {
        // Выделяем часть в первых скобках
        int startIndex = userAgent.indexOf('(');
        int endIndex = userAgent.indexOf(')');
        if (startIndex == -1 || endIndex == -1) {
            return "";
        }

        String firstBrackets = userAgent.substring(startIndex + 1, endIndex);

        // Разделяем по точке с запятой
        String[] parts = firstBrackets.split(";");
        if (parts.length < 2) {
            return "";
        }

        // Берем второй фрагмент и очищаем от пробелов
        String fragment = parts[1].trim();

        // Отделяем часть до слэша
        int slashIndex = fragment.indexOf('/');
        if (slashIndex == -1) {
            return fragment;
        }

        return fragment.substring(0, slashIndex);
    }
}