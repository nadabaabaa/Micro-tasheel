package tn.esprit.infini.Pidev.RestController;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.infini.Pidev.Services.IAccount;
import tn.esprit.infini.Pidev.entities.Account;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/Account")
public class AccountController {
    private IAccount iAccount;
    @GetMapping("/getAccounts")
    List<Account> afficher() {return iAccount.retrieveAllAccounts();}
    @PostMapping("/addAccount/{idUser}")
    Account ajouter(@RequestBody Account account, @PathVariable("idUser") int idUser) {
        return iAccount.addAccount(account, idUser);
    }
    @GetMapping("/getAccountById/{idAccount}")
    Account afficherAvecId(@PathVariable int idAccount){
        return iAccount.retrieveAccount(idAccount);
    }
    @PutMapping("/updateAccount")
    public Account updateAccount(@RequestBody Account account) {
        return iAccount.updateAccount(account);
    }
    @DeleteMapping("/deleteAccount/{idAccount}")
    public void deleteUser(@PathVariable ("idAccount") Integer idAccount)
    {iAccount.deleteAccount(idAccount);}
    @PutMapping("/addBalance/{idAccount}/{amount}")
    public  void addBalance(@PathVariable int idAccount, @PathVariable float amount)
    {iAccount.addBalance(iAccount.retrieveAccount(idAccount), amount);}
    @PutMapping("/substractBalance/{idAccount}/{amount}")
    public  void substractBalance(@PathVariable int idAccount, @PathVariable float amount)
    {iAccount.substractBalance(iAccount.retrieveAccount(idAccount), amount);}
    @GetMapping("/countAccounts")
    public int countAccounts() {
        return iAccount.countAccounts();
    }
}
