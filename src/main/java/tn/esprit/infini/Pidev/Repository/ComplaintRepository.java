package tn.esprit.infini.Pidev.Repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import tn.esprit.infini.Pidev.entities.Complaint;
import org.springframework.data.repository.query.Param;
import tn.esprit.infini.Pidev.entities.Complaint;
import tn.esprit.infini.Pidev.entities.Stateofcomplaint;

import java.util.List;

public interface ComplaintRepository extends CrudRepository<Complaint,Long> {
    @Query("SELECT c.typecomplaint, COUNT(c) FROM Complaint c GROUP BY c.typecomplaint")
     List<Object[]> getComplaintsByType();

    @Query("select i From Complaint i where i.user.id = :iddonne")
    List<Complaint> getComplaintsByUser(@Param("iddonne") int id);
 //  List<Complaint> findByStateOfComplaint(Stateofcomplaint stateofcomplaint);
 @Query("SELECT c FROM Complaint c WHERE c.stateofcomplaint = :state")
 List<Complaint> findComplaintsByState(@Param("state") Stateofcomplaint state);
    @Query("SELECT c FROM Complaint c WHERE c.stateofcomplaint = 'complaintresolved'")
    List<Complaint> getResolvedComplaints();

}

   /*  Long findByTypecomplaint(Typecomplaint typecomplaint); */
   /* List<Complaint>findAllByTypecomplaint(String type);
    int countByTypecomplaint();
    */

