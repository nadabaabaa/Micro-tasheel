package tn.esprit.infini.Pidev.RestController;

import lombok.AllArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.infini.Pidev.Services.IFine;
import tn.esprit.infini.Pidev.entities.Fine;
import tn.esprit.infini.Pidev.entities.FineType;
import tn.esprit.infini.Pidev.entities.Transaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/Fine")
public class FineController {
    private IFine iFine;
    @GetMapping("/getFines")
    List<Fine> afficher() {
        return iFine.retrieveAllFines();
    }
    @PostMapping("/addFine")
    Fine ajouter(@RequestBody Fine fine) {
        return iFine.addFine(fine);
    }
    @GetMapping("/getFineById/{idFine}")
    Fine afficherAvecId(@PathVariable Long idFine){
        return iFine.retrieveFine(idFine);
    }

    @PutMapping("/updateFine")
    public Fine updateFine(@RequestBody Fine fine) {
        return iFine.updateFine(fine);
    }

    @DeleteMapping("/deleteFine/{idFine}")
    void deleteFine(@PathVariable ("idFine") Long idFine)
    {
        iFine.deleteFine(idFine);
    }


    @GetMapping("/fines/search")
     List<Fine> searchFines(@RequestBody Map<String, String> params) {

        // convert request params to map of Object values
        Map<String, Object> criteria = new HashMap<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            // check if value can be converted to Double
            try {
                Double doubleValue = Double.valueOf(value);
                criteria.put(key, doubleValue);
            } catch (NumberFormatException e) {
                // value is not a Double, try to convert to Date
                try {
                    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    Date dateValue = dateFormat.parse(value);
                    criteria.put(key, dateValue);
                } catch (ParseException ex) {
                    // value is not a Date, use as String
                    criteria.put(key, value);
                }
            }
        }
        // call service method to search for fines

        return iFine.searchFines(criteria,criteria.size());

    }
    @GetMapping("/GetFineModel/{idFine}")
    public List<String> calculatePaymentsByDay(@PathVariable Long idFine)

    { Fine fine = iFine.retrieveFine(idFine);
        return iFine.calculatePaymentsByDay(fine.getStartDate(),fine.getDueDate(), fine.getTotalAmount());

    }
}
