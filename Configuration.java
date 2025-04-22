import java.io.*;
import java.util.Properties;

public class Configuration {
    private static Configuration instance;
    private final Properties properties;
    private final Logger logger;
    private static final String DEFAULT_CONFIG_FILE = "email-config.properties";

    private Configuration() {
        this.properties = new Properties();
        this.logger = new Logger();
        loadDefaultConfiguration();
    }

    public static Configuration getInstance() {
        if (instance == null) {
            instance = new Configuration();
        }
        return instance;
    }

    private void loadDefaultConfiguration() {
        properties.setProperty("smtp.host", "smtp.gmail.com");
        properties.setProperty("smtp.port", "587");
        properties.setProperty("smtp.auth", "true");
        properties.setProperty("smtp.starttls.enable", "true");
        properties.setProperty("email.from", "noreply@deals.com");
        properties.setProperty("email.subject.template", "Exclusive Offer for {first_name}!");
        properties.setProperty("template.path", "contacts.txt");
        properties.setProperty("contacts.path", "contacts.csv");

        File configFile = new File(DEFAULT_CONFIG_FILE);
        if (configFile.exists()) {
            try (FileInputStream fis = new FileInputStream(configFile)) {
                properties.load(fis);
                logger.log("Configuration loaded from " + DEFAULT_CONFIG_FILE);
            } catch (IOException e) {
                logger.logError("Error loading configuration file: " + e.getMessage());
            }
        } else {
            createDefaultConfigFile();
        }
    }

    private void createDefaultConfigFile() {
        try (FileOutputStream fos = new FileOutputStream(DEFAULT_CONFIG_FILE)) {
            properties.store(fos, "Email Creator Default Configuration");
            logger.log("Created default configuration file: " + DEFAULT_CONFIG_FILE);
        } catch (IOException e) {
            logger.logError("Could not create default configuration file: " + e.getMessage());
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public void setProperty(String key, String value) {
        properties.setProperty(key, value);
    }

    public void saveConfiguration() {
        try (FileOutputStream fos = new FileOutputStream(DEFAULT_CONFIG_FILE)) {
            properties.store(fos, "Email Creator Configuration");
            logger.log("Configuration saved to " + DEFAULT_CONFIG_FILE);
        } catch (IOException e) {
            logger.logError("Could not save configuration: " + e.getMessage());
        }
    }
}