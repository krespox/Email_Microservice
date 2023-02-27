package Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Value;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@RestController
@RequestMapping("/email")
public class EmailController {

    private static final Logger integrationLog = LoggerFactory.getLogger("integration");
    private static final Logger generalLog = LoggerFactory.getLogger(EmailController.class);

    @Autowired
    private EmailRepository emailRepository;

    // Injecting values from application.properties
    @Value("${spring.mail.host}")
    private String emailHost;

    @Value("${spring.mail.port}")
    private String emailPort;

    @Value("${spring.mail.username}")
    private String emailUsername;

    @Value("${spring.mail.password}")
    private String emailPassword;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private String emailAuth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private String emailStartTls;

    @GetMapping("/{id}")
    public Email getEmail(@PathVariable("id") Long id) {
        integrationLog.info("Received request to get email with id {}", id);
        return emailRepository.findById(id).orElse(null);
    }

    @GetMapping("/all")
    public List<Email> getAllEmails() {
        integrationLog.info("Received request to get all emails");
        return emailRepository.findAll();
    }

    @PostMapping("/add")
    public Email addEmail(@RequestBody Email email) {
        integrationLog.info("Received request to add email: {}", email);
        return emailRepository.save(email);
    }

    @PutMapping("/{id}")
    public Email updateEmail(@PathVariable("id") Long id, @RequestBody Email email) {
        integrationLog.info("Received request to update email with id {} to: {}", id, email);
        Optional<Email> emailToUpdate = emailRepository.findById(id);
        if (emailToUpdate.isPresent()) {
            email.setId(id);
            return emailRepository.save(email);
        } else {
            return null;
        }
    }

    @DeleteMapping("/{id}")
    public void deleteEmail(@PathVariable("id") Long id) {
        integrationLog.info("Received request to delete email with id {}", id);
        emailRepository.deleteById(id);
    }

    @PostMapping("/send")
    public void sendEmail(@RequestParam("subject") String subject,
                          @RequestParam("message") String message) throws MessagingException {
        integrationLog.info("Received request to send email with subject: {} and message: {}", subject, message);

        Properties props = new Properties();
        props.put("mail.smtp.auth", emailAuth);
        props.put("mail.smtp.starttls.enable", emailStartTls);
        props.put("mail.smtp.host", emailHost);
        props.put("mail.smtp.port", emailPort);

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(emailUsername, emailPassword);
            }
        });

        List<String> emailAddresses = emailRepository.findEmailAddresses();
        for (String emailAddress : emailAddresses) {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(emailUsername, false));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailAddress));
            msg.setSubject(subject);
            msg.setText(message);
            Transport.send(msg);
        }

        generalLog.info("Sent email with subject: {} and message: {}", subject, message);
    }
}
