// Класс исключения для строк длиннее 1024 символов
public class LineTooLongException extends RuntimeException {
    public LineTooLongException(String message) {
        super(message);
    }
}