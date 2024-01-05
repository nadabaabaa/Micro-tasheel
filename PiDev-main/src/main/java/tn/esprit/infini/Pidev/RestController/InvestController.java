package tn.esprit.infini.Pidev.RestController;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.esprit.infini.Pidev.Services.Iinvestservice;
import tn.esprit.infini.Pidev.entities.Credit;
import tn.esprit.infini.Pidev.entities.Invest;
import tn.esprit.infini.Pidev.entities.Statut;
import tn.esprit.infini.Pidev.entities.User;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/Invest")
@CrossOrigin(origins = "http://localhost:4200/")

public class InvestController {
    private Iinvestservice iinvestservice;

    @GetMapping("/getInvest")
    @PreAuthorize("hasRole('ADMIN')")
    List<Invest> afficher() {
        return iinvestservice.retrieveAllInvests();
    }

    @PostMapping("/addInvest")
    @PreAuthorize("hasRole('USER')")
    InvestResponseDTO ajouter(@RequestBody InvestRequestDTO investRequestDTO) {
        return iinvestservice.addInvest(investRequestDTO);
    }

    @GetMapping("/getInvestById/{idInvest}")
    @PreAuthorize("hasRole('ADMIN')")
    Invest afficherAvecId(@PathVariable Long idInvest){
        return iinvestservice.retrieveInvest(idInvest);
    }

    @PutMapping("/updateInvest")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Invest updateInvest(@RequestBody Invest invest) {
        return iinvestservice.updateInvest(invest);
    }

    @DeleteMapping("/deleteInvest/{idInvest}")
    @PreAuthorize("hasRole('ADMIN')")
    void deleteInvest(@PathVariable ("idInvest") Long idInvest)
    {
        iinvestservice.deleteInvest(idInvest);
    }

    @GetMapping("/getinvestsbyiduser/userid")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public List<Invest> getInvestByiduser(@PathVariable( "userid") Long userid) {
        return iinvestservice.getInvestByiduser(userid);
    }
    @GetMapping("/searchs")
    @PreAuthorize("hasRole('ADMIN')")

    public List<Invest> searchInvests(
            @RequestParam(required = false) Long id,
            @RequestParam(required = false) Double amount,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dateofapplication ,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dateofobtaining,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dateoffinish,
            @RequestParam(required = false) Double interestrate,
            @RequestParam(required = false) Integer mounths,
            @RequestParam(required = false) Statut statut) {
            return iinvestservice.searchInvests(id,amount,dateofapplication,dateofobtaining,dateoffinish,interestrate,mounths,statut);
    }
    @GetMapping("/amount")
    @PreAuthorize("hasRole('ADMIN')")

    Double totalAmountOfInvests(){
       return iinvestservice.totalAmountOfInvests();
    }
    @GetMapping("/investbystatut")
    @PreAuthorize("hasRole('ADMIN')")
    Map<Statut, Double> percentageOfinvestsByStatus(){
        return iinvestservice.percentageOfinvestsByStatus();
    }
    @PostMapping("/mensualite")
        List<Double> mensualite(@RequestBody Invest invest){
        return  iinvestservice.Mensualite(invest);
    }
    @PostMapping("/taxes")
    List<Double> taxes(@RequestBody Invest invest){
        return  iinvestservice.tax(invest);
    }


}

