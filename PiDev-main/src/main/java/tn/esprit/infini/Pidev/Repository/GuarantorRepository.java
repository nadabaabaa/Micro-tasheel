package tn.esprit.infini.Pidev.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.infini.Pidev.entities.Guarantor;
import org.springframework.stereotype.Repository;
import tn.esprit.infini.Pidev.entities.Credit;
import tn.esprit.infini.Pidev.entities.Guarantor;

import java.util.List;

@Repository

public interface GuarantorRepository extends JpaRepository<Guarantor, Integer> {

    List<Credit> findByCredit(Credit credit);
}
