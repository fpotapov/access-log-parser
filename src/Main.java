import java.io.File;
import java.util.Scanner;

public class Main {
    public static void main (String[] args) {
        Scanner scanner = new Scanner(System.in);
        int fileCount = 0;

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
            boolean fileExists = file.exists();

            // Проверяем, является ли путь именно файлом, а не папкой
            boolean isDirectory = file.isDirectory();

            // Обрабатываем результат
            if (!fileExists) {
                System.out.println("Файл не существует. Попробуйте снова.");
                continue;
            }

            if (isDirectory) {
                System.out.println("Указанный путь ведет не к файлу, а к папке. Попробуйте снова.");
                continue;
            }

            // Если файл существует и это файл, а не папка
            fileCount++;
            System.out.println("Путь указан верно. Это файл номер " + fileCount);
        }

        scanner.close(); // Закрываем Scanner
    }
}
