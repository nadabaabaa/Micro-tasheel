package tn.esprit.infini.Pidev.Repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.infini.Pidev.entities.Commande;


@Repository
public interface ICommandeRep extends JpaRepository<Commande,Long> {

}
