package tn.esprit.infini.Pidev.Services.user_management;


import tn.esprit.infini.Pidev.entities.PasswordResetToken;
import tn.esprit.infini.Pidev.entities.User;

public interface PasswordResetTokenService {
    PasswordResetToken generateTokenForUser(User user);

    PasswordResetToken findByToken(String tokenString);

    void delete(PasswordResetToken token);

//    void sendPasswordResetEmail(String email, String token);
}
