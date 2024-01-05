package tn.esprit.infini.Pidev.Service.user_management;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import tn.esprit.infini.Pidev.entities.Email;
import tn.esprit.infini.Pidev.entities.PasswordResetToken;


@Service
public class EmailServiceImpl implements EmailService {

    private JavaMailSender mailSender;

    @Autowired
    private PasswordResetTokenService passwordResetTokenService ;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    @Override
    public void sendPasswordResetEmail(String email, PasswordResetToken token) {
        MimeMessage message = mailSender.createMimeMessage();

        try {

            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom("camping-project@picloud.com");
            helper.setTo(email);
            helper.setSubject("Password Reset Request");
            helper.setText("Please use the following link to reset your password: " +
                    "http://localhost:8090/reset-password?token=" + token.getToken() , true);
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send password reset email", e);
        }
    }




    public String sendEmail(Email details) {


        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("nadabaabaa1@gmail.com");
        message.setTo(details.getRecipient());
        message.setText(details.getMsgBody());
        message.setSubject(details.getSubject());

        try {
            mailSender.send(message);
            return "Mail Sent Successfully...";
        } catch (MailException e) {
            return "Error while Sending Mail";
        }
    }
}
