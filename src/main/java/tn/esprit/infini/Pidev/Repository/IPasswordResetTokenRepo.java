package tn.esprit.infini.Pidev.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.infini.Pidev.entities.PasswordResetToken;

@Repository
public interface IPasswordResetTokenRepo extends JpaRepository<PasswordResetToken,Long> {
    public PasswordResetToken findByToken(String tokenString);
}
