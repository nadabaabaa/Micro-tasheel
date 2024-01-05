package tn.esprit.infini.Pidev.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.infini.Pidev.entities.Bill;
@Repository
public interface BillRepository extends JpaRepository<Bill,Long> {
}
