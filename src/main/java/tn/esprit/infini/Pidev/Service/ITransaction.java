package tn.esprit.infini.Pidev.Service;

import tn.esprit.infini.Pidev.entities.Transaction;

import java.util.List;

public interface ITransaction {
    Transaction addTransaction(Transaction transaction);

    List<Transaction> retrieveAllTransactions();

  Transaction updateTransaction(Transaction transaction);

   Transaction retrieveTransaction(Long idTransaction);
    Transaction retrieveTransactionByStripeId(String stripeId);

    void deleteTransaction(Long idTransaction);


   // List<Transaction>divideTransaction(Long idCredit);



    public List<Transaction> getTransactionsRequiringPayment() ;



    void confirmTransaction(String intentId);
}
