package tn.esprit.infini.Pidev.Services;





import com.google.gson.Gson;
import lombok.AllArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tn.esprit.infini.Pidev.Repository.TransactionRepository;
import tn.esprit.infini.Pidev.entities.Credit;
import tn.esprit.infini.Pidev.entities.Transaction;
import tn.esprit.infini.Pidev.entities.TypeRemboursement;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.lang.String;

@Service
@AllArgsConstructor
public class TransactionService implements ITransaction {


    private  Creditservice creditservice;
    private TransactionRepository transactionRepository;
    private static Gson gson = new Gson();

    @Override
    public Transaction addTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> retrieveAllTransactions() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction updateTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction retrieveTransaction(Long idTransaction) {
        return transactionRepository.findById(idTransaction).get();
    }

    @Override
    public Transaction retrieveTransactionByStripeId(String stripeId) {
        return transactionRepository.findByStripeId(stripeId);
    }


    @Override
    public void deleteTransaction(Long idTransaction) {
        transactionRepository.deleteById(idTransaction);

    }

    @Override

    public List<Transaction> divideTransaction(Long idCredit) {
        List<Transaction> transactionList = new ArrayList<>();
        Credit credit =creditservice.retrieveCredit(idCredit);
        Integer numberOfMonths = credit.getDuration();
        if (credit.getTypeRemboursement()==TypeRemboursement.Mensualitéfixe){
            Double monthlyPayment =creditservice.CalculMensualitefixe(credit);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());

            for (int i = 1; i <= numberOfMonths; i++) {
                Transaction payment = new Transaction();
                payment.setAmount(monthlyPayment.longValue());
                calendar.add(Calendar.SECOND, 40);
                payment.setIdobject(credit.getId());
                payment.setDate(calendar.getTime());

                transactionList.add(payment);
            }
            return transactionList;

        }
        else  {
            List<Double> monthlyPayment = creditservice.CalculMensualitevariable(credit);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            for (int i = 0; i < numberOfMonths; i++) {
                Transaction payment = new Transaction();
                double tempAmount = monthlyPayment.get(i);
                long amount = (long) tempAmount;
                payment.setAmount(amount);
                calendar.add(Calendar.SECOND, 40);
                payment.setDate(calendar.getTime());
                payment.setIdobject(credit.getId());
                transactionList.add(payment);
            }
            return transactionList;

        }
        }








    @Override
    public List<Transaction> getTransactionsRequiringPayment() {
        return transactionRepository.findByStatus("requires_payment_method");
    }

    @Override
    public void confirmTransaction(String intentId) {
       /* JsonObject jsonObject = gson.fromJson(intentId, JsonObject.class);

        String id = jsonObject.get("intentId").getAsString();*/

        transactionRepository.updateTransactionStatusByStripeId("Succeded",intentId);
        transactionRepository.updateTransactionPaymentMethodByStripeId("visa_credit_card",intentId);


    }



}

