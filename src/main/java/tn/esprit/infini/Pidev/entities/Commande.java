package tn.esprit.infini.Pidev.entities;
import javax.persistence.*;

import lombok.*;
import java.io.Serializable;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class   Commande implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate Date_cmd;
    private LocalDate date_livraison;
    private Double montant;
    private String lieuxlivraison;
    private ModePaiement modePaiement;
    private Double prixapreslivraison;
    private String commentaire_cmd;
    private Statut statut=Statut.EN_ATTENTE;
    @ManyToOne
    private User user;
    @OneToOne
    private Panier panier;

}
