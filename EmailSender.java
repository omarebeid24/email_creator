import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

public class EmailSender {
    private final Logger logger;
    private final Configuration config;
    private final Session session;

    public EmailSender() {
        this.logger = new Logger();
        this.config = Configuration.getInstance();

        Properties props = new Properties();
        props.put("mail.smtp.auth", config.getProperty("smtp.auth", "true"));
        props.put("mail.smtp.starttls.enable", config.getProperty("smtp.starttls.enable", "true"));
        props.put("mail.smtp.host", config.getProperty("smtp.host", "smtp.gmail.com"));
        props.put("mail.smtp.port", config.getProperty("smtp.port", "587"));

        session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                        config.getProperty("smtp.username"),
                        config.getProperty("smtp.password")
                );
            }
        });

        session.setDebug(true);
    }


    public void sendEmail(Email email) {
        int maxRetries = 3;
        for (int attempt = 1; attempt <= maxRetries; attempt++) {
            try {
                send(email);
                logger.log("✅ Email successfully sent to: " + email.getTo());
                return;
            } catch (MessagingException e) {
                logger.logError("❌ Attempt " + attempt + " failed to send email to: " + email.getTo() + ". Error: " + e.getMessage());
                if (attempt == maxRetries) {
                    logger.logError("🚨 Giving up after " + maxRetries + " failed attempts.");
                } else {
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                        break;
                    }
                }
            }
        }
    }

    private void send(Email email) throws MessagingException {
        MimeMessage message = new MimeMessage(session);

        String authenticatedEmail = config.getProperty("smtp.username");
        message.setFrom(new InternetAddress(authenticatedEmail));

        message.setReplyTo(new Address[] { new InternetAddress("noreply@deals.com") });

        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.getTo()));
        message.setSubject(email.getSubject());
        message.setText(email.getContent());

        Transport.send(message);
    }


    public void shutdown() {
        logger.log("EmailSender shutting down.");
    }
}


