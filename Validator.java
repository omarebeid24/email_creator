public class Validator {
    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

    public boolean validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return email.matches(EMAIL_REGEX);
    }

    public boolean validateTemplate(String template) {
        if (template == null || template.trim().isEmpty()) {
            return false;
        }
        return template.contains("{email}") &&
                template.contains("{first_name}");
    }

    public boolean validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return false;
        }
        return name.matches("^[A-Za-z\\s-']+$");
    }

    public boolean validateFile(String filePath) {
        if (filePath == null || filePath.trim().isEmpty()) {
            return false;
        }
        return new java.io.File(filePath).exists();
    }
}