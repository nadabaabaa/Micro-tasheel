package tn.esprit.infini.Pidev.Service.user_management;


import tn.esprit.infini.Pidev.entities.Email;
import tn.esprit.infini.Pidev.entities.PasswordResetToken;

public interface EmailService {
    void sendPasswordResetEmail(String email, PasswordResetToken token);

    //void sendPasswordResetEmail(String email, PasswordResetToken token);
    public String sendEmail(Email details);

}
