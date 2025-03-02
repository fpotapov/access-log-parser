import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LogEntry {
    private final String ipAddress;
    private final OffsetDateTime timestamp;
    private final HttpMethod method;
    private final String path;
    private final int responseCode;
    private final int dataSize;
    private final String referer;
    private final UserAgent userAgent;

    public LogEntry(String logLine) {
        // Разделяем строку на части
        String[] parts = logLine.split("\"");

        // Извлекаем IP-адрес и дату
        String headerPart = parts[0].trim();
        this.ipAddress = headerPart.split("\\s+")[0];

        // Извлекаем полную строку даты, включая часовой пояс
        int dateStartIndex = headerPart.indexOf('[');
        int dateEndIndex = headerPart.indexOf(']');
        if (dateStartIndex == -1 || dateEndIndex == -1) {
            throw new IllegalArgumentException("Некорректный формат даты в строке: " + logLine);
        }
        String fullDate = headerPart.substring(dateStartIndex + 1, dateEndIndex);

        // Парсим дату
        this.timestamp = parseTimestamp(fullDate);

        // Извлекаем метод запроса и путь
        String[] requestParts = parts[1].split("\\s+");
        this.method = HttpMethod.valueOf(requestParts[0]);
        this.path = requestParts[1];

        // Извлекаем код ответа и размер данных
        String[] responseParts = parts[2].trim().split("\\s+");
        this.responseCode = Integer.parseInt(responseParts[0]);
        this.dataSize = responseParts[1].equals("-") ? 0 : Integer.parseInt(responseParts[1]);

        // Извлекаем referer и User-Agent
        this.referer = parts[3].isEmpty() ? "-" : parts[3];
        this.userAgent = new UserAgent(parts[5]);
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public OffsetDateTime getTimestamp() {
        return timestamp;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public int getResponseCode() {
        return responseCode;
    }

    public int getDataSize() {
        return dataSize;
    }

    public String getReferer() {
        return referer;
    }

    public UserAgent getUserAgent() {
        return userAgent;
    }

    private OffsetDateTime parseTimestamp(String timestampString) {
        // Форматтер для парсинга даты из лог-файла
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss Z", Locale.ENGLISH);

        // Парсим строку с датой и временем
        return OffsetDateTime.parse(timestampString, formatter);
    }
}