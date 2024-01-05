package tn.esprit.infini.Pidev.dto;

import com.stripe.exception.StripeException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tn.esprit.infini.Pidev.RestController.PaymentController;
import tn.esprit.infini.Pidev.RestController.TransactionController;
import tn.esprit.infini.Pidev.Services.IPayment;
import tn.esprit.infini.Pidev.Services.TransactionService;
import tn.esprit.infini.Pidev.entities.Transaction;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class ScheduledTasks {
    private List<Transaction> transactionList;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private IPayment iPayment;


    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss");

    @Scheduled(cron = "0 0 1 * * *")
    public void reportCurrentTime() throws StripeException, ParseException {
        Date today = new Date();


        List<Transaction> transactionsToConfirm = transactionService.getTransactionsRequiringPayment();
        for (Transaction transaction : transactionsToConfirm) {

            if (today.after(transaction.getDate())) {


                transactionService.confirmTransaction(transaction.getStripeId());
                iPayment.confimPayment(transaction.getStripeId());
            }



        }
    }
}