package tn.esprit.infini.Pidev.Repository;

import org.springframework.data.repository.CrudRepository;
import tn.esprit.infini.Pidev.entities.Complaint;
import tn.esprit.infini.Pidev.entities.ComplaintHistory;

public interface ComplaintHistoryRepository extends CrudRepository<ComplaintHistory,Long> {
}
