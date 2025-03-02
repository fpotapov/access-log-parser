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

            // Если файл существует и это файл, а не папка
            try {
                // Переменные для хранения результатов
                int totalLines = 0;       // Общее количество строк
                int maxLength = Integer.MIN_VALUE; // Длина самой длинной строки
                int minLength = Integer.MAX_VALUE; // Длина самой короткой строки

                // Чтение файла построчно
                FileReader fileReader = new FileReader(file);
                BufferedReader reader = new BufferedReader(fileReader);

                String line;
                while ((line = reader.readLine()) != null) {
                    totalLines++;

                    int length = line.length();

                    // Проверка длины строки
                    if (length > 1024) {
                        throw new LineTooLongException("Строка длиннее 1024 символов: " + line);
                    }

                    // Обновление максимальной и минимальной длины
                    if (length > maxLength) {
                        maxLength = length;
                    }
                    if (length < minLength) {
                        minLength = length;
                    }
                }

                // Закрываем ресурсы
                reader.close();
                fileReader.close();

                // Вывод результатов
                System.out.println("Общее количество строк: " + totalLines);
                System.out.println("Длина самой длинной строки: " + maxLength);
                System.out.println("Длина самой короткой строки: " + minLength);

            } catch (FileNotFoundException e) {
                System.out.println("Файл не найден: " + e.getMessage());
            } catch (LineTooLongException e) {
                System.out.println("Ошибка: " + e.getMessage());
            } catch (IOException e) {
                System.out.println("Ошибка ввода-вывода: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Произошла неизвестная ошибка: " + e.getMessage());
                e.printStackTrace();
            }
        }

        scanner.close(); // Закрываем Scanner
    }
}