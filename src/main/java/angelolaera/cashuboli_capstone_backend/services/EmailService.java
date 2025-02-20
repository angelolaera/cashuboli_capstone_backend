package angelolaera.cashuboli_capstone_backend.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String fromEmail;

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationEmail(String toEmail, String userName, String verificationLink) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Conferma la tua email per Cashuboli");
            helper.setText("<h1>Ciao " + userName + "!</h1>"
                    + "<p>Grazie per la registrazione! Clicca il link qui sotto per verificare il tuo account:</p>"
                    + "<p><a href='" + verificationLink + "'>Verifica Email</a></p>"
                    + "<p>Il link è valido per 24 ore.</p>", true);

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
