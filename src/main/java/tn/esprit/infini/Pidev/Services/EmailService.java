package tn.esprit.infini.Pidev.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import tn.esprit.infini.Pidev.entities.Email;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender javaMailSender;

    public String sendEmail(Email details) {
   /*     SimpleMailMessage message = new SimpleMailMessage();

        try {
            message.setFrom("nadabaabaa1@gmail.com");
            message.setTo(details.getRecipient());
            message.setText(details.getMsgBody());
            message.setSubject(details.getSubject());

            javaMailSender.send(message);
            return "Mail Sent Successfully...";
        } catch (Exception e) {
            return "Error while Sending Mail";
        }

    }
} */

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("nadabaabaa1@gmail.com");
        message.setTo(details.getRecipient());
        message.setText(details.getMsgBody());
        message.setSubject(details.getSubject());

        try {
            javaMailSender.send(message);
            return "Mail Sent Successfully...";
        } catch (MailException e) {
            return "Error while Sending Mail";
        }
    }
}
