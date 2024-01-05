package tn.esprit.infini.Pidev.Controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.infini.Pidev.Service.IPayment;
import tn.esprit.infini.Pidev.Service.ITransaction;
import tn.esprit.infini.Pidev.entities.Transaction;


@RestController
@AllArgsConstructor
@NoArgsConstructor
@RequestMapping("/Payment")
public class PaymentController {
    @Value("${stripe.api.key}")
    private String stripePublicKey;
    private static Gson gson = new Gson();
    @Autowired
    private ITransaction transactionService;
    @Autowired
    private IPayment paymentService;




    @PostMapping("/create-payment-intent")
    public String createPaymentIntent(@NotNull @RequestBody Transaction transaction)throws StripeException {
        Stripe.apiKey=this.stripePublicKey;
       String IntentId =paymentService.createPaymentIntent(transaction);
       paymentService.persistTransaction(IntentId);
       return IntentId;
    }
    @PostMapping("confirm-payment-intent")
    public void confimPayment(@RequestBody String intentId) throws StripeException {
        Stripe.apiKey=this.stripePublicKey;
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(intentId, JsonObject.class);
        String id = jsonObject.get("intentId").getAsString();
        paymentService.confimPayment(id);
        transactionService.confirmTransaction(id);
        }

        @PostMapping("/persist-payment-base")
        public  void persistTransaction(@RequestBody String intentId) throws StripeException {
            Stripe.apiKey=this.stripePublicKey;
            paymentService.persistTransaction(intentId);


        }
        /*/
    @GetMapping("/GetPaymentModel/{idCredit}")
    public void divideTransaction(@PathVariable Long idCredit) throws StripeException {
        List<Transaction> maListe =transactionService.divideTransaction( idCredit);
        for (Transaction transaction:maListe)
        {
           String intent= paymentService.createPaymentIntent(transaction);
           transaction.setStripeId(intent);
           transaction.setStatus("requires_payment_method");
           transaction.setIdobject(idCredit);
           transaction.setTypeTransaction(TypeTransaction.Credit);
            transactionService.addTransaction(transaction);

        }

         */


    }








