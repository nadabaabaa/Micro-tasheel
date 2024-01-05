package tn.esprit.infini.Pidev.RestController;


import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.infini.Pidev.Services.IInsuranceService;
import tn.esprit.infini.Pidev.entities.Insurance;
import tn.esprit.infini.Pidev.entities.Typeinsurance;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/Insurance")
@AllArgsConstructor
public class InsuranceController {
    private IInsuranceService insuranceService;


    @PostMapping("/addinsurance")
    public void addinsurance(@RequestBody Insurance insurance) {
        insuranceService.addInsurance(insurance);
    }
    @GetMapping("/displaycurrentinsurance")
    public List<Insurance> displayinsurance() {
        return insuranceService.retrieveAllinsurances();
    }
    @GetMapping("/displayArchivedinsurance")
    public List<Insurance> displayArchivedinsurance() {
        return insuranceService.retrieveArchivedinsurance();
    }


    @GetMapping("/displaywithId/{idinsurance}")
    public Insurance displaywithidinsurance(@PathVariable Integer idinsurance) {
        return insuranceService.retrieveInsurance(  idinsurance);
    }
    @PutMapping("/updateinsurance")
    public Insurance updateinsurance(@RequestBody Insurance insurance) {

        return insuranceService.updateInsurance(insurance);
    }


    @GetMapping("/calculate-cost")
    public double calculateInsuranceCost(@RequestBody Insurance insurance) {
        return insuranceService.calculateInsuranceCost(insurance);
    }

    @PostMapping("/calculateCostWithDiscount")
    public double calculateCostWithDiscount(@RequestBody Insurance insurance) {
        return insuranceService.calculateInsuranceCostWithDiscount(insurance);
    }
    @GetMapping("/getinsurancebyType")
    public List<Object[]> getInsurancetByType() {
        return insuranceService.getInsuranceByType();
    }

   @PostMapping("/request")
   public String processInsuranceRequest(@RequestBody Insurance insurance) {
       boolean isApproved = insuranceService.processInsuranceRequest(insurance);
       if (isApproved) {
           insuranceService.addInsurance(insurance);
           return "La demande d'assurance a été approuvée et l'assurance a été ajoutée.";
       } else {
           return "La demande d'assurance a été refusée.";
       }
   }
    @PostMapping("/packs/{packId}/insurances/{insuranceId}")
    public Insurance addInsuranceToPack(@PathVariable int packId, @PathVariable int insuranceId) {
        return insuranceService.addInsuranceToPack(insuranceId, packId);
    }


    @PostMapping("/calculate-fico-score")
    public float calculateFicoScore(@RequestBody Insurance insurance) {
        return insuranceService.calculateFicoScore(insurance);
    }
    @GetMapping("/LevelOfRiskCalculator")

    public double LevelOfRiskCalculator(@RequestBody Insurance insurance) throws IOException {
        return insuranceService.LevelOfRiskCalculator(insurance);
    }
    @PutMapping("/assignInsuranceToPack/{idinsurancek}/{idPack}") // affecte un pack au panier ( bouton ajouter au panier)
    public Insurance assignInsiranceToPack(@PathVariable Integer idinsurance
                                         , @PathVariable Integer idPack){
        return insuranceService.assignInsuranceToPack( idinsurance,  idPack);

    }
   /* @GetMapping("/insurancecc")
    public Double getInsuranceMaxNumber() {
        List<Object[]> insuranceList = insuranceService.getInsuranceByType();
        double maxNumber = insuranceService.findMaxNumber(insuranceList);
        return maxNumber;
    }*/
}


