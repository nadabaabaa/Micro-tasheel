package tn.esprit.infini.Pidev.entities;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder

public class Panier implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double montant;
    @OneToOne(mappedBy = "panier")
    private Commande commande;
    @OneToMany
    private List<Produit> produits;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
