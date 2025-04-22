import java.util.*;

public class EmailCreatorTest {
    private static EmailGenerator emailGenerator;
    private static Contact testContact;
    private static String testTemplate;
    private static int testsPassed = 0;
    private static int totalTests = 0;

    public static void main(String[] args) {
        setUp();
        runTests();
        printResults();
    }

    private static void setUp() {
        emailGenerator = new EmailGenerator();
        testContact = new Contact("John", "Doe", "john.doe@example.com");
        testTemplate = """
            [PLAIN_TEXT]
            Dear {first_name},
            Your email is {email}
            [HTML]
            <div>
                <h1>Welcome, {first_name}!</h1>
                <p>Your email is {email}</p>
            </div>
            """;
    }

    private static void runTests() {
        testEmailGeneration();
        testInvalidTemplate();
        testEmptyContact();
        testNullTemplate();

    }

    private static void testEmailGeneration() {
        System.out.println("\nRunning Email Generation Test...");
        totalTests++;
        try {
            List<Contact> contacts = Collections.singletonList(testContact);
            List<Email> emails = emailGenerator.generateEmails(contacts, testTemplate);

            boolean test1 = !emails.isEmpty();
            boolean test2 = emails.get(0).getTo().equals("john.doe@example.com");
            boolean test3 = emails.get(0).getContent().contains("Dear John");

            if (test1 && test2 && test3) {
                System.out.println("✅ Email Generation Test Passed");
                testsPassed++;
            } else {
                System.out.println("❌ Email Generation Test Failed");
            }
        } catch (Exception e) {
            System.out.println("❌ Email Generation Test Failed with exception: " + e.getMessage());
        }
    }

    private static void testInvalidTemplate() {
        System.out.println("\nRunning Invalid Template Test...");
        totalTests++;
        try {
            List<Contact> contacts = Collections.singletonList(testContact);
            String invalidTemplate = "Invalid template without placeholders";
            try {
                emailGenerator.generateEmails(contacts, invalidTemplate);
                System.out.println("❌ Invalid Template Test Failed - Should have thrown exception");
            } catch (IllegalArgumentException e) {
                System.out.println("✅ Invalid Template Test Passed");
                testsPassed++;
            }
        } catch (Exception e) {
            System.out.println("❌ Invalid Template Test Failed with unexpected exception: " + e.getMessage());
        }
    }

    private static void testEmptyContact() {
        System.out.println("\nRunning Empty Contact List Test...");
        totalTests++;
        try {
            List<Contact> contacts = Collections.emptyList();
            try {
                emailGenerator.generateEmails(contacts, testTemplate);
                System.out.println("❌ Empty Contact List Test Failed - Should have thrown exception");
            } catch (IllegalArgumentException e) {
                System.out.println("✅ Empty Contact List Test Passed");
                testsPassed++;
            }
        } catch (Exception e) {
            System.out.println("❌ Empty Contact List Test Failed with unexpected exception: " + e.getMessage());
        }
    }

    private static void testNullTemplate() {
        System.out.println("\nRunning Null Template Test...");
        totalTests++;
        try {
            List<Contact> contacts = Collections.singletonList(testContact);
            try {
                emailGenerator.generateEmails(contacts, null);
                System.out.println("❌ Null Template Test Failed - Should have thrown exception");
            } catch (IllegalArgumentException e) {
                System.out.println("✅ Null Template Test Passed");
                testsPassed++;
            }
        } catch (Exception e) {
            System.out.println("❌ Null Template Test Failed with unexpected exception: " + e.getMessage());
        }
    }





    private static void printResults() {
        System.out.println("\n=== Test Results ===");
        System.out.println("Total Tests: " + totalTests);
        System.out.println("Tests Passed: " + testsPassed);
        System.out.println("Tests Failed: " + (totalTests - testsPassed));
        System.out.println("Success Rate: " + (testsPassed * 100 / totalTests) + "%");
    }
}