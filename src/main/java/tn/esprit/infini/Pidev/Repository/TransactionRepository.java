package tn.esprit.infini.Pidev.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;
import tn.esprit.infini.Pidev.entities.Transaction;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    public Transaction findByStripeId(String StripeId);


    @Transactional
    @Modifying
    @Query("UPDATE Transaction t SET t.status = :newStatus WHERE t.stripeId = :stripeId")
     public void updateTransactionStatusByStripeId(@Param("newStatus") String newStatus, @Param("stripeId") String stripeId);
    @Transactional
    @Modifying
    @Query("UPDATE Transaction t SET t.modePaiement = :newPaymentMethod WHERE t.stripeId = :stripeId")
    public void updateTransactionPaymentMethodByStripeId(@Param("newPaymentMethod") String newPaymentMethod, @Param("stripeId") String stripeId);
    List<Transaction> findByStatus(String status);



}
