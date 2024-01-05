package tn.esprit.infini.Pidev.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "Produit")


public class Produit implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double prix;
    private String titre;
    private int quantite;
    private String description;
    @Enumerated(EnumType.STRING)
    private Categorie categorie;



}
