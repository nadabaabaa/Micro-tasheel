package tn.esprit.infini.Pidev.Controller;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import tn.esprit.infini.Pidev.Repository.TransactionRepository;
import tn.esprit.infini.Pidev.Service.ITransaction;
import tn.esprit.infini.Pidev.entities.Transaction;


import java.util.List;

@RestController
@Service
@AllArgsConstructor
@RequestMapping("/Transaction")
public class TransactionController {
    private ITransaction iTransaction;

    @GetMapping("/getTransactions")
    List<Transaction> afficher() {
        return iTransaction.retrieveAllTransactions();
    }

    @PostMapping("/addTransaction")
    Transaction ajouter(@RequestBody Transaction transaction) {
        return iTransaction.addTransaction(transaction);
    }

    @GetMapping("/getTransactionById/{idTransaction}")
    Transaction afficherAvecId(@PathVariable Long idTransaction){
        return iTransaction.retrieveTransaction(idTransaction);
    }

    @PutMapping("/updateTransaction")
    public Transaction updateTransaction(@RequestBody Transaction transaction) {
        return iTransaction.updateTransaction(transaction);
    }

    @DeleteMapping("/deleteTransaction/{idTransaction}")
    void  deleteTransaction(@PathVariable ("idTransaction") Long idTransaction)
    {
        iTransaction.deleteTransaction(idTransaction);
    }
    @PostMapping("/confirmTransaction")
    void confirmTransaction(@RequestBody String intentId)
    {
        iTransaction.confirmTransaction(intentId);


    }



}
