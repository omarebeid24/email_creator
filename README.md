# Email Creator & Sender System

A modular Java application designed for generating and sending personalized emails using contact and template files. The application supports email validation, logging, error handling, SMTP-based email sending, and unit testing.

---

## 📌 Features

- ✅ Reads contact details from a CSV file.
- 📄 Loads email templates with placeholders (`{first_name}`, `{email}`, etc.).
- 📧 Generates personalized email content.
- 🚀 Sends emails through an SMTP server.
- 🧪 Includes basic test coverage and result reporting.
- 📝 Logs system activity and errors to a file.

---

## 📂 File Structure

```
.
├── EmailCreator.java           # Main entry point
├── EmailCreatorTest.java      # Test cases for generation
├── EmailGenerator.java        # Core logic for email templating
├── EmailSender.java           # Sends emails using SMTP
├── Email.java                 # Email entity
├── Contact.java               # Contact entity
├── Configuration.java         # Loads config from file
├── FileReader.java            # Reads contacts.csv and contacts.txt
├── Validator.java             # Validation methods for input data
├── Logger.java                # Logging utility
├── contacts.csv               # Contact list file
├── contacts.txt               # Email template file
├── email-config.properties    # Configuration file
└── email_creator.log          # Application logs
```

---

## ⚙️ Configuration

Settings are loaded from `email-config.properties`. Defaults are auto-generated on first run.

```properties
smtp.host=smtp.gmail.com
smtp.port=587
smtp.auth=true
smtp.starttls.enable=true
smtp.username=your_email@gmail.com
smtp.password=your_password
email.from=noreply@deals.com
email.subject.template=Exclusive Offer for {first_name}!
template.path=contacts.txt
contacts.path=contacts.csv
```

Update this file to match your SMTP credentials and desired settings.

---

## 🧪 Testing

Run `EmailCreatorTest.java` to perform unit tests on:

- Valid email generation
- Invalid templates
- Empty contact list
- Null template input

Test output includes success rate and details for each case.

---

## 🚀 Running the Application

1. Add contacts to `contacts.csv` with format:
   ```
   FirstName,LastName,Email
   John,Doe,john.doe@example.com
   ```

2. Create your email template in `contacts.txt`:
   ```text
   [PLAIN_TEXT]
   Dear {first_name},
   Your email is {email}
   [HTML]
   <div>
       <h1>Welcome, {first_name}!</h1>
       <p>Your email is {email}</p>
   </div>
   ```

3. Run `EmailCreator.java` to generate and print emails.
4. Optional: Use `EmailSender.java` to send them through SMTP.

---

## 🛡️ Validation & Logging

The app uses `Validator.java` to ensure:

- Valid email format
- Non-empty templates with required placeholders
- Valid names and existing files

All activities and errors are logged in `email_creator.log`.

---

## 💡 Future Improvements

- Add GUI support using JavaFX or Swing
- Extend template engine with markdown/HTML rendering
- Enable sending HTML emails with styled formatting
- Import contacts from external sources (e.g., Google Contacts)
- Schedule batch email campaigns with throttling

---


