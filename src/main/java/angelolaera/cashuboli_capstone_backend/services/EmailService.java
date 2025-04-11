package angelolaera.cashuboli_capstone_backend.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    private final JavaMailSender mailSender;



    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendWelcomeEmail(String toEmail, String userName, String welcomeLink) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);


            helper.setTo(toEmail);
            helper.setSubject("🎉 Benvenuto in Cashuboli, " + userName + "!");
            String emailContent = "<div style='font-family: Sanchez, sans-serif; color: #590f18; padding: 20px;'>"
                    + "<h2 style='color: #4CAF50;'>Ciao " + userName + " 👋</h2>"
                    + "<p>Grazie per esserti unito a <strong>Cashuboli</strong>! Siamo felici di averti con noi 🌟</p>"
                    + "<p>Da oggi puoi accedere al tuo account e iniziare a scoprire tutte le funzionalità che abbiamo pensato per te.</p>"
                    + "<p style='margin-top: 30px;'>"
                    + "Accedi al tuo account</a></p>"
                    + "<p style='margin-top: 40px;'>Esplora il nostro mondo,<br><strong>Vivi Cashuboli!</strong></p>"
                    + "</div>";

            helper.setText(emailContent, true);
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
