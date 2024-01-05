package tn.esprit.infini.Pidev.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.infini.Pidev.entities.Produit;


@Repository
public interface IProduitRep extends JpaRepository<Produit,Long> {

}
