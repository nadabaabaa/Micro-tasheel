package tn.esprit.infini.Pidev.Services;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import tn.esprit.infini.Pidev.entities.Transaction;
import tn.esprit.infini.Pidev.entities.TypeTransaction;
import java.lang.String;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class PaymentService implements IPayment {
    @Value("${stripe.api.key}")
    private String stripePublicKey;
    private static Gson gson = new Gson();
    @Autowired
    private ITransaction iTransaction;
    @Override
    public void persistTransaction(String intentId) throws StripeException {
        Stripe.apiKey=this.stripePublicKey;

        /*JsonObject jsonObject = gson.fromJson(intentId, JsonObject.class);

        String id = jsonObject.get("id").getAsString();


         */
        PaymentIntent paymentIntent = PaymentIntent.retrieve(intentId);
        String status =paymentIntent.getStatus();
        Long userId = 1l;
        Date date = new Date();

        Long idObject = 1l;
        Long amount = paymentIntent.getAmount()/100L;
        String stripeid=paymentIntent.getId();
        String paymentMethod=paymentIntent.getPaymentMethod();
        TypeTransaction typeTransaction = TypeTransaction.Invest;
        Transaction transaction =new Transaction(typeTransaction,userId,idObject, date,amount,stripeid,paymentMethod,status);
        iTransaction.addTransaction(transaction);


    }

    @Override
    public String createPaymentIntent(Transaction transaction) throws StripeException {
        Stripe.apiKey=this.stripePublicKey;
        PaymentIntentCreateParams createParams = new
                PaymentIntentCreateParams.Builder()
                .setCurrency("usd")
                .setAmount(transaction.getAmount()*100L)
                .build();

        PaymentIntent intent = PaymentIntent.create(createParams);
        String intentID = intent.getId();
        return intentID;



    }

    @Override
    public void confimPayment(String intentId) throws StripeException {
        Stripe.apiKey=this.stripePublicKey;
        PaymentIntent intent = PaymentIntent.retrieve(intentId);
        Map<String, Object> params = new HashMap<>();
        params.put("payment_method", "pm_card_visa");
        intent.confirm(params);

    }


}
