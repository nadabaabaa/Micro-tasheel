package tn.esprit.infini.Pidev.RestController;

import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.esprit.infini.Pidev.Services.ComplaintService;
import tn.esprit.infini.Pidev.Services.IComplaintService;
import tn.esprit.infini.Pidev.entities.Complaint;
import tn.esprit.infini.Pidev.entities.ComplaintHistory;
import tn.esprit.infini.Pidev.entities.Typecomplaint;

import java.util.HashMap;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/Complaint")
@CrossOrigin(origins = "http://localhost:4200/")
public class ComplaintRestController {

    private ComplaintService complaintService;
    private IComplaintService iComplaintService;

    @GetMapping("/displayComplaint")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Complaint> display() {
        return iComplaintService.retrieveAllcomplaints();
    }

    /*@PostMapping("/addComplaint/{idUser}")
    public Complaint add(@RequestBody Complaint complaint,@PathVariable("idUser")Integer idUser) {
        return iComplaintService.addComplaint(complaint,idUser);
    }
    */
     /* @PostMapping("/addComplaint")
    Complaint ajouter(@RequestBody Complaint c) {
        return iComplaintService.addComplaint(c);
    } */
    @PostMapping("/addComplaint/{id}")
    @PreAuthorize("hasRole('USER')")
    Complaint ajouter(@RequestBody Complaint complaint, @PathVariable("id") int id) {
        return iComplaintService.addComplaint(complaint, id);
    }


    @GetMapping("/displaywithIdComplaint/{idcomplaint}")
    @PreAuthorize("hasRole('ADMIN')")
    public Complaint displaywithidComplaint(@PathVariable("idcomplaint") Long idcomplaint) {
        return iComplaintService.retrieveComplaint(idcomplaint);
    }

    @GetMapping("/statistics")
    @PreAuthorize("hasRole('ADMIN')")

    public HashMap<Typecomplaint, Double> getComplaintStatistics() {
        return iComplaintService.getComplaintStatistics();
    }

    @GetMapping("/getcomplaintbyType")
    @PreAuthorize("hasRole('ADMIN')")

    public List<Object[]> getcomplaintByTyoe() {
        return iComplaintService.getComplaintByType();

    }

    @DeleteMapping("/deletecomplaint/{idcomplaint}")
    public void deletecomplaint(@PathVariable("idcomplaint") Long idcomplaint) {
         iComplaintService.deleteComplaint(idcomplaint);
    }

    @GetMapping("/archive")
    @PreAuthorize("hasRole('ADMIN')")

    public String archiveResolvedComplaints() {
        complaintService.archiveResolvedComplaints();
        return "Les réclamations résolues sont archivées.";
    }

    @GetMapping("/afficherarchive")
    @PreAuthorize("hasRole('ADMIN')")

    public List<ComplaintHistory> getArchivedComplaints() {
        return complaintService.getArchivedComplaints();
    }

    /*68@PostMapping("/complaints")
   public Complaint addComplaint(@RequestBody Complaint complaint) {
       String filteredDescription = iComplaintService.filterBadWords(complaint.getDescription());
       complaint.setDescription(filteredDescription);
    return   iComplaintService.addComplaint(complaint);

   } */
    @GetMapping("/sendResolvedEmails")
    @PreAuthorize("hasRole('ADMIN')")

    public String sendResolvedEmails() {
        complaintService.sendResolvedComplaintsEmails();
        return "Resolved complaints emails sent successfully";
    }

    @GetMapping("/displayComplaint12/{id}")
    @PreAuthorize("hasRole('USER')")
    public List<Complaint> display(@PathVariable("id") int id) {
        return iComplaintService.retrieveAllcomplaintsuser(id);
    }

    @PutMapping("/updatecomplaint")
    public Complaint updateComplaint(@RequestBody Complaint complaint) {
        return iComplaintService.updateComplaint(complaint) ;
    }


}
    /* @PutMapping("/assignComplainttTouser/{idcomplaint}/{id}")
    public Complaint  addandassingComplaintTouser(@PathVariable("idcomplaint") Long idcomplaint,@PathVariable("id") int id){
     return iComplaintService.assignComplaintToUser(idcomplaint,id);
    }

*/

