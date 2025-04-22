import java.io.*;
import java.util.*;

public class FileReader {
    private static final String CONTACTS_FILE = "contacts.csv";
    private static final String TEMPLATE_FILE = "contacts.txt";
    private final Validator validator;
    private final Logger logger;

    public FileReader() {
        this.validator = new Validator();
        this.logger = new Logger();
    }

    public List<Contact> readContactsFile() throws IOException {
        List<Contact> contacts = new ArrayList<>();
        File file = new File(CONTACTS_FILE);

        if (!file.exists()) {
            throw new FileNotFoundException("Contacts file not found: " + CONTACTS_FILE);
        }

        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(file))) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                try {
                    Contact contact = parseContactLine(line);
                    if (contact != null) {
                        contacts.add(contact);
                        logger.log("Successfully parsed contact: " + contact.getEmailAddress());
                    }
                } catch (IllegalArgumentException e) {
                    logger.logError("Error parsing contact line: " + e.getMessage());
                }
            }
        }
        return contacts;
    }

    private Contact parseContactLine(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }

        String[] parts = line.split(",");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid contact format. Expected: firstName,lastName,email");
        }

        String firstName = parts[0].trim();
        String lastName = parts[1].trim();
        String email = parts[2].trim();

        if (!validator.validateName(firstName)) {
            throw new IllegalArgumentException("Invalid first name: " + firstName);
        }
        if (!validator.validateName(lastName)) {
            throw new IllegalArgumentException("Invalid last name: " + lastName);
        }
        if (!validator.validateEmail(email)) {
            throw new IllegalArgumentException("Invalid email address: " + email);
        }

        return new Contact(firstName, lastName, email);
    }

    public String readTemplateFile() throws IOException {
        File file = new File(TEMPLATE_FILE);

        if (!file.exists()) {
            throw new FileNotFoundException("Template file not found: " + TEMPLATE_FILE);
        }

        StringBuilder template = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                template.append(line).append("\n");
            }
        }

        String templateContent = template.toString();
        if (!validator.validateTemplate(templateContent)) {
            throw new IllegalArgumentException("Invalid template format. Missing required placeholders.");
        }

        return templateContent;
    }
}