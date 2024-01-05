package tn.esprit.infini.Pidev.Services;

import com.stripe.exception.StripeException;
import org.jetbrains.annotations.NotNull;
import org.springframework.web.bind.annotation.RequestBody;
import tn.esprit.infini.Pidev.entities.Transaction;

public interface IPayment {
    public void persistTransaction(String intentId) throws StripeException;

    public String createPaymentIntent(Transaction transaction) throws StripeException;
    public void confimPayment(String intentId) throws StripeException;
}

