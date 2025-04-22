import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.*;

public class Logger {
    private static final DateTimeFormatter formatter =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final String LOG_FILE = "email_creator.log";
    private final PrintWriter fileWriter;

    public Logger() {
        try {
            this.fileWriter = new PrintWriter(
                    new FileWriter(LOG_FILE, true),
                    true
            );
        } catch (IOException e) {
            throw new RuntimeException("Failed to initialize logger", e);
        }
    }

    public void log(String message) {
        String logMessage = formatLogMessage("INFO", message);
        System.out.println(logMessage);
        fileWriter.println(logMessage);
    }

    public void logError(String error) {
        String logMessage = formatLogMessage("ERROR", error);
        System.err.println(logMessage);
        fileWriter.println(logMessage);
    }

    private String formatLogMessage(String level, String message) {
        String timestamp = LocalDateTime.now().format(formatter);
        return String.format("%s [%s] %s", timestamp, level, message);
    }

    public void close() {
        if (fileWriter != null) {
            fileWriter.close();
        }
    }


}