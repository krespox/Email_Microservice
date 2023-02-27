package com.example.EmailService;

import com.example.EmailService.Email;
import com.example.EmailService.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailRepository emailRepository;

    @GetMapping("/{id}")
    public Email getEmail(@PathVariable("id") Long id) {
        return emailRepository.findById(id).orElse(null);
    }

    @GetMapping("/all")
    public List<Email> getAllEmails() {
        return emailRepository.findAll();
    }

    @PostMapping("/add")
    public Email addEmail(@RequestBody Email email) {
        return emailRepository.save(email);
    }

    @PutMapping("/{id}")
    public Email updateEmail(@PathVariable("id") Long id, @RequestBody Email email) {
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
        emailRepository.deleteById(id);
    }

    @PostMapping("/send")
    public void sendEmail(@RequestParam("subject") String subject,
                          @RequestParam("message") String message) throws MessagingException {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("krystiankaaluzny@gmail.com", "vtzodjjwjkcxdsxv");

            }
        });

        List<String> emailAddresses = emailRepository.findEmailAddresses();
        for (String emailAddress : emailAddresses) {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("krystiankaaluzny@gmail.com", false));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailAddress));
            msg.setSubject(subject);
            msg.setText(message);
            Transport.send(msg);
        }
    }
}
