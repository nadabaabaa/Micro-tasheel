package tn.esprit.infini.Pidev.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.infini.Pidev.entities.Account;
import tn.esprit.infini.Pidev.entities.Credit;
import tn.esprit.infini.Pidev.entities.Transaction;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.infini.Pidev.entities.Transaction;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    public Transaction findByStripeId(String StripeId);
    List <Transaction> getTransactionBycredit(Credit credit);
    List<Transaction> getTransactionByAccounts(Account account);


    @Transactional
    @Modifying
    @Query("UPDATE Transaction t SET t.status = :newStatus WHERE t.stripeId = :stripeId")
     public void updateTransactionStatusByStripeId(@Param("newStatus") String newStatus, @Param("stripeId") String stripeId);
    @Transactional
    @Modifying
    @Query("UPDATE Transaction t SET t.paymentMethod = :newPaymentMethod WHERE t.stripeId = :stripeId")
    public void updateTransactionPaymentMethodByStripeId(@Param("newPaymentMethod") String newPaymentMethod, @Param("stripeId") String stripeId);
    List<Transaction> findByStatus(String status);



}
