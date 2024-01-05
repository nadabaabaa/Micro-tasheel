package tn.esprit.infini.Pidev.RestController;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import tn.esprit.infini.Pidev.Services.Iinvestservice;
import tn.esprit.infini.Pidev.entities.Invest;
import tn.esprit.infini.Pidev.entities.Statut;

import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/Invest")

public class InvestController {
    private Iinvestservice iinvestservice;

    @GetMapping("/getInvest")
    List<Invest> afficher() {
        return iinvestservice.retrieveAllInvests();
    }

    @PostMapping("/addInvest")
    InvestResponseDTO ajouter(@RequestBody InvestRequestDTO investRequestDTO) {
        return iinvestservice.addInvest(investRequestDTO);
    }

    @GetMapping("/getInvestById/{idInvest}")
    Invest afficherAvecId(@PathVariable Long idInvest){
        return iinvestservice.retrieveInvest(idInvest);
    }

    @PutMapping("/updateInvest")
    public Invest updateInvest(@RequestBody Invest invest) {
        return iinvestservice.updateInvest(invest);
    }

    @DeleteMapping("/deleteInvest/{idInvest}")
    void deleteInvest(@PathVariable ("idInvest") Long idInvest)
    {
        iinvestservice.deleteInvest(idInvest);
    }

    @GetMapping("/getinvestsbyiduser/userid")
    public List<Invest> getInvestByiduser(@PathVariable( "userid") Long userid) {
        return iinvestservice.getInvestByiduser(userid);
    }

    @GetMapping("/searchs")
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
   Double totalAmountOfInvests(){
       return iinvestservice.totalAmountOfInvests();
    }
    @GetMapping("/investbystatut")

    Map<Statut, Double> percentageOfinvestsByStatus(){
        return iinvestservice.percentageOfinvestsByStatus();
    }

}

