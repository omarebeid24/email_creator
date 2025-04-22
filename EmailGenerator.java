import java.util.*;

public class EmailGenerator {
    private final Validator validator;
    private final Logger logger;

    public EmailGenerator() {
        this.validator = new Validator();
        this.logger = new Logger();
    }

    public List<Email> generateEmails(List<Contact> contacts, String template) {
        if (contacts == null || contacts.isEmpty()) {
            throw new IllegalArgumentException("No contacts provided for email generation");
        }
        if (!validator.validateTemplate(template)) {
            throw new IllegalArgumentException("Invalid email template");
        }

        List<Email> emails = new ArrayList<>();
        for (Contact contact : contacts) {
            try {
                Email email = generateEmail(contact, template);
                emails.add(email);
                logger.log("Generated email for: " + contact.getEmailAddress());
            } catch (Exception e) {
                logger.logError("Failed to generate email for " + contact.getEmailAddress() + ": " + e.getMessage());
            }
        }
        return emails;
    }

    private Email generateEmail(Contact contact, String template) {
        if (!validator.validateEmail(contact.getEmailAddress())) {
            throw new IllegalArgumentException("Invalid email address: " + contact.getEmailAddress());
        }

        String processedContent = processTemplate(template, contact);
        return new Email(
                contact.getEmailAddress().toLowerCase(),
                "noreply@deals.com",
                "Deals!",
                processedContent
        );
    }

    private String processTemplate(String template, Contact contact) {
        return template
                .replace("{email}", contact.getEmailAddress().toLowerCase())
                .replace("{first_name}", formatName(contact.getFirstName()))
                .replace("{last_name}", formatName(contact.getLastName()))
                .replace("{full_name}", contact.getFormattedName());
    }

    private String formatName(String name) {
        if (name == null || name.isEmpty()) {
            return "";
        }
        return name.substring(0, 1).toUpperCase() +
                name.substring(1).toLowerCase();
    }
}