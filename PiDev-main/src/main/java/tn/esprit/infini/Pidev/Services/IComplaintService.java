package tn.esprit.infini.Pidev.Services;

import tn.esprit.infini.Pidev.entities.Complaint;
import tn.esprit.infini.Pidev.entities.ComplaintHistory;
import tn.esprit.infini.Pidev.entities.Typecomplaint;

import java.util.HashMap;
import java.util.List;

public interface IComplaintService {
    List<Complaint> retrieveAllcomplaintsuser(int id);

    List<Complaint> retrieveAllcomplaints();


    /*  boolean complaintContainsBadWords(Complaint complaint); */

    /*  @Override
      public Complaint addComplaint(Complaint c,Integer idUser) {
          User user=userRepository.findById(idUser).get();
          c.setUser(user);
          return complaintRepository.save(c);

      }

     */
     // public Complaintresponse addComplaint(Complaint complaint, int id)
    //Complaintresponse addComplaint(ComplaintrequestDTO complaintDTO);

    /*  @Override
      public Complaint addComplaint(Complaint c,Integer idUser) {
          User user=userRepository.findById(idUser).get();
          c.setUser(user);
          return complaintRepository.save(c);

      }

     */
     // public Complaintresponse addComplaint(Complaint complaint, int id)
    Complaint addComplaint(Complaint complaint, int id);



    Complaint updateComplaint(Complaint c);

    // ComplaintrequestDTO filterBadWords(ComplaintrequestDTO complaint);

    Complaint retrieveComplaint(Long idcomplaint);

    void deleteComplaint(Long idcomplaint);
    // List<Complaint> getComplaintByUser(int idUser);

    List<Object[]> getComplaintByType();

    HashMap<Typecomplaint, Double> getComplaintStatistics();


   //  Complaint addComplaint(Complaint complaint, int id);

    Complaint filterBadWords(Complaint complaint);


    void archiveResolvedComplaints();

    List<ComplaintHistory> getArchivedComplaints();
}
/* Complaint assignComplaintToUser(Long idcomplaint, int id); */
   /* List<Object[]> getComplaintByType(); */




