import java.util.List;

public class EmailCreator {
    private final FileReader fileReader;
    private final EmailGenerator emailGenerator;
    private final Configuration config;
    private final Logger logger;

    public EmailCreator() {
        this.config = Configuration.getInstance();
        this.logger = new Logger();
        this.fileReader = new FileReader();
        this.emailGenerator = new EmailGenerator();
    }

    public static void main(String[] args) {
        EmailCreator creator = new EmailCreator();
        creator.run();
    }

    public void run() {
        try {
            List<Contact> contacts = fileReader.readContactsFile();
            String template = fileReader.readTemplateFile();

            List<Email> emails = emailGenerator.generateEmails(contacts, template);
            logger.log("Generated " + emails.size() + " emails");

            for (Email email : emails) {
                System.out.println(email.toString());
            }

            logger.log("Email generation completed successfully");
        } catch (Exception e) {
            logger.logError("Error during email processing: " + e.getMessage());
            e.printStackTrace();
        }
    }
}