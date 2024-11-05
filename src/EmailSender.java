import java.util.Properties;
import java.util.regex.Pattern;
import javax.mail.*;
import javax.mail.internet.*;
import java.security.SecureRandom;

public class EmailSender {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@?";

    // SecureRandom instance for generating random values
    private static final SecureRandom random = new SecureRandom();
    public static String generateRandomString() {
        StringBuilder randomString = new StringBuilder(5); // Length of 5 characters

        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(CHARACTERS.length());
            randomString.append(CHARACTERS.charAt(index));
        }

        return randomString.toString();
    }
    // Method to validate email using a regular expression
    public static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    // Method to send email
    public static void sendEmail(String recipientEmail, String subject, String body) throws MessagingException {
        // Sender's email credentials
        final String senderEmail = "";  // Change with your sender email
        final String senderPassword = "";  // Change with your email password 

        // Email server properties (Using Gmail's SMTP)
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        // Create session
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(senderEmail, senderPassword);
            }
        });

        // Compose the message
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress(senderEmail));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
        message.setSubject(subject);
        message.setText(body);

        // Send the message
        Transport.send(message);
        System.out.println("Email sent successfully!");
    }

    
}
