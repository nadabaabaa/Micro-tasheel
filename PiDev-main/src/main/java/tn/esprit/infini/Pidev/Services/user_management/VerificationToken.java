package tn.esprit.infini.Pidev.Services.user_management;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import tn.esprit.infini.Pidev.entities.User;


import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "verification_tokens")
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "token")
    private String token;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "expiry_date")
    private LocalDateTime expiryDate;

    public VerificationToken() {
        super();
    }

    public VerificationToken(String token, User user) {
        super();
        this.token = token;
        this.user = user;
        this.expiryDate = LocalDateTime.now().plusDays(1); // Set the token expiry to 1 day from now
    }

    // Getters and setters
}
