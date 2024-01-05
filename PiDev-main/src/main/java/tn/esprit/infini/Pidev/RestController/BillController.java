package tn.esprit.infini.Pidev.RestController;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.infini.Pidev.Services.IBill;
import tn.esprit.infini.Pidev.entities.Bill;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/Bill")
public class BillController {
    private  IBill iBill;
    @GetMapping("/getBills")
    List<Bill> afficher() {
        return iBill.retrieveAllBills();
    }

    @PostMapping("/addBill")
    Bill ajouter(@RequestBody Bill bill) {

        return iBill.addbill(bill);
    }

    @GetMapping("/getBillById/{idBill}")
    Bill afficherAvecId(@PathVariable Long idBill){
        return iBill.retrieveBill(idBill);
    }

    @PutMapping("/updateBill")
    public Bill updateBill(@RequestBody Bill bill) {
        return iBill.updateBill(bill);
    }

    @DeleteMapping("/deleteBill/{idBill}")
    void deleteBill(@PathVariable("idBill") Long idBill)
    {
        iBill.deleteBill(idBill);
    }
    @GetMapping("/bills/search")
    public List<Bill> searchBills(@RequestBody Map<String, String> params) {

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

        // call service method to search for bills

        return iBill.searchBills(criteria, criteria.size());
    }

}
