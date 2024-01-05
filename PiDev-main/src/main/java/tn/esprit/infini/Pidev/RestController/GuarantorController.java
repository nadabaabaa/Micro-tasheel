package tn.esprit.infini.Pidev.RestController;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.infini.Pidev.Services.GuarantorService;
import tn.esprit.infini.Pidev.Services.IGuarantorService;
import tn.esprit.infini.Pidev.entities.Credit;
import tn.esprit.infini.Pidev.entities.Guarantor;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/Guarantor")
public class GuarantorController {
    GuarantorService guarantorService;

    @GetMapping("/getGuarantors")
    List<Guarantor> afficher() {

        return guarantorService.retrieveAllGuarantor();
    }
    @PostMapping("/addGuarantor")
    Guarantor ajouter(@RequestBody Guarantor guarantor) throws Exception {
        return guarantorService.addGuarantor(guarantor);
    }

    @GetMapping("/getById/{id}")
    Guarantor afficherAvecId(@PathVariable int id){
        return guarantorService.retrieveGuarantor(id);
    }

    @PutMapping("/updateGuarantor/{id}")
    public Guarantor updateGuarantor(@RequestBody Guarantor guarantor) {
        return guarantorService.updateGuarantor(guarantor);
    }

    @DeleteMapping("/deleteGuarantor/{idGuarantor}")
     void deleteGuarantor(@PathVariable ("idGuarantor") Integer idGuarantor )
    {
        guarantorService.deleteGuarantor(idGuarantor);
    }

    @PostMapping("/verify/amount")
    public boolean verifyGuarantor(@RequestBody Guarantor guarantor,
                                   @PathVariable ("amount") double amount) {
            return guarantorService.VerifyGuarantor(guarantor, amount);
        }


    @PostMapping("/check-cin")
    public String checkCin(@RequestBody int cinGuarantor) {
        boolean isValid = guarantorService.ValidCin(cinGuarantor);
        if (isValid) {
            return ("CIN is valid");
        } else {
            return ("CIN is not valid");
        }
    }
}
