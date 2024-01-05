package tn.esprit.infini.Pidev.Services;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.infini.Pidev.Repository.ComplaintHistoryRepository;
import tn.esprit.infini.Pidev.Repository.ComplaintRepository;
import tn.esprit.infini.Pidev.Repository.UserRepository;
import tn.esprit.infini.Pidev.entities.*;
import tn.esprit.infini.Pidev.mappers.Complaintmapper;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@AllArgsConstructor
public  class ComplaintService implements IComplaintService {

  private EmailService emailService;
    ComplaintRepository complaintRepository;
    UserRepository userRepository;
    Complaintmapper complaintmapper;
    private ComplaintHistoryRepository complaintHistoryRepository;

    @Override
    public List<Complaint> retrieveAllcomplaints() {
        return (List<Complaint>) complaintRepository.findAll();
    }

    /*  @Override
      public Complaint addComplaint(Complaint c,Integer idUser) {
          User user=userRepository.findById(idUser).get();
          c.setUser(user);
          return complaintRepository.save(c);

      }

     */

     // public Complaint addComplaint(Complaint complaint, int id)
     //Complaintresponse addComplaint(ComplaintrequestDTO complaintDTO);

    @Override
    public Complaint addComplaint(Complaint complaint, int id) {
        //        UR.findByAccount(account).setType(TypeUser.Casual_Client);

        User user= userRepository.findById(id);
        complaint.setUser(user);
        return complaintRepository.save(filterBadWords(complaint));
    }
 /*@Override
    public Complaint addComplaint(Complaint complaint, int id) {

        User user = userRepository.findById(id).orElse(null);

        if (complaintContainsBadWords(complaint)) {
           user.setBlockedUntil(LocalDateTime.now().plusHours(2)); // Block the user for 2 hours

            throw new BadWordsException("Your complaint contains inappropriate language and has been blocked for 2 hours.");
        }

        complaint.setUser(user);
        return complaintRepository.save(filterBadWords(complaint));
    }
       @Override
       public boolean complaintContainsBadWords(Complaint complaint) {
        String[] badWords = {"shit", "crap", "basterd","damn it"," Bloody Hell"," Rubbish",};

        for (String word : badWords) {
            if (complaint.getDescription().toLowerCase().contains(word.toLowerCase())) {
                return true;
            }
        }

        return false;
    }

*/



    @Override
    public Complaint updateComplaint(Complaint c) {

        return complaintRepository.save(c);
    }



    @Override
    public String deleteComplaint(Long idcomplaint) {

        complaintRepository.deleteById(idcomplaint);
        return ("complaint supprimé");
    }

 /*   @Override
    public List<Complaint> getComplaintByUser(int idUser) {

        return  complaintRepository.getComplaintsByUser(idUser);
        return (List<Complaint>) complaints ;
    } */
    @Override
    public List<Object[]> getComplaintByType() {

        return complaintRepository.getComplaintsByType();
    }

    @Override
    public HashMap<Typecomplaint, Double> getComplaintStatistics() {
        List<Complaint> complaints = (List<Complaint>) complaintRepository.findAll();
        HashMap<Typecomplaint, Long> statistics = new HashMap<>();
        for (Complaint complaint : complaints) {
            Typecomplaint type = complaint.getTypecomplaint();
              if (statistics.containsKey(type)) {
                statistics.put(type, statistics.get(type) + 1);
            } else {
                statistics.put(type, 1L);
            }
        }
        HashMap<Typecomplaint, Double> percentages = new HashMap<>();
        long totalComplaints = complaints.size();
        for (Map.Entry<Typecomplaint, Long> entry : statistics.entrySet()) {
            Typecomplaint type = entry.getKey();
            long count = entry.getValue();
            double percentage = (double) count / totalComplaints * 100;
            percentages.put(type, percentage);
        }
        return percentages;
    }
   @Override
    public Complaint filterBadWords(Complaint complaint) {
        String[] badWords = {"shit", "crap", "basterd","damn it"," Bloody Hell"," Rubbish",};

        for (String word : badWords) {
            complaint.description = complaint.description.replaceAll("(?i)" + word, "**********");
        }

        return complaint;
    }
    @Override
    public Complaint retrieveComplaint(Long idcomplaint) {
        return complaintRepository.findById(idcomplaint).get();
    }
    /*@Override
    public void archiveResolvedComplaints(int userId) {
        List<Complaint> complaints = complaintRepository.getComplaintsByUser(userId);
        for (Complaint complaint : complaints) {
            if (complaint.getStateofcomplaint() == Stateofcomplaint.complaintresolved) {
                ComplaintHistory history = ComplaintHistory.builder()
                        .complaint(complaint)
                        .date(LocalDate.now())
                        .comment("Complaint resolved and archived")
                        .state(complaint.getStateofcomplaint())
                        .build();
                complaintHistoryRepository.save(history);
                complaintRepository.delete(complaint);
            }
        }
    }*/ //
    @Override
    @Scheduled( cron = "0 0 0 * * *")
    public void archiveResolvedComplaints() {
        List<Complaint> resolvedComplaints = complaintRepository.getResolvedComplaints();
        for (Complaint complaint : resolvedComplaints) {
            ComplaintHistory history = ComplaintHistory.builder()
                    .complaint(complaint)
                    .date(LocalDate.now())
                    .comment("Complaint resolved and archived")
                    .state(complaint.getStateofcomplaint())
                    .build();
            complaintHistoryRepository.save(history);
            complaintRepository.delete(complaint);
        }
        System.out.println("Resolved complaints have been archived.");
    }
@Override
    public List<ComplaintHistory> getArchivedComplaints() {
        return (List<ComplaintHistory>) complaintHistoryRepository.findAll();
    }


   public void sendResolvedComplaintsEmails() {
     List<Complaint> resolvedComplaints = complaintRepository.findComplaintsByState(Stateofcomplaint.complaintresolved);
     for (Complaint complaint : resolvedComplaints) {
         // envoyer l'e-mail à l'utilisateur correspondant ici
         User user = complaint.getUser();
         String userEmail = user.getEmail();
        String emailBody = "Votre réclamation a été traitée avec succès.";
         // emailService.sendEmail(userEmail, "Réclamation traitée", emailBody);
         Email email = new Email();
         email.setRecipient(userEmail);
         email.setSubject("Réclamation traitée");
         email.setMsgBody(emailBody);

         emailService.sendEmail(email);
     }
 }
}















