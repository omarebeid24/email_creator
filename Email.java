public class Email {
    private final String to;
    private final String from;
    private final String subject;
    private final String content;
    private final Configuration config;

    public Email(String to, String from, String subject, String content) {
        this.to = to;
        this.from = from;
        this.subject = subject;
        this.content = content;
        this.config = Configuration.getInstance();
    }

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }

    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        String separator = config.getProperty("email.separator",
                "================================================================");

        StringBuilder sb = new StringBuilder()
                .append(separator).append("\n")
                .append("To: ").append(to).append("\n")
                .append("From: ").append(from).append("\n")
                .append("Subject: ").append(subject).append("\n\n")
                .append(content).append("\n");

        if ("plain".equalsIgnoreCase(config.getProperty("email.format", "plain"))) {
            sb.append(separator).append("\n");
        }

        return sb.toString();
    }
}