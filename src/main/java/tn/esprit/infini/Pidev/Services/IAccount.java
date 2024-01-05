package tn.esprit.infini.Pidev.Services;

import tn.esprit.infini.Pidev.entities.Account;

import java.util.List;

public interface IAccount {
    //CRUD
    Account addAccount(Account account, int idUser);
    List<Account> retrieveAllAccounts();
    Account updateAccount (Account account);
    void deleteAccount(Integer idAccount);
    //Advanced functions
    Account retrieveAccount (int idAccount);
    void addBalance(Account account, float amount);
    void substractBalance(Account account, float amount);
    int countAccounts();

}
