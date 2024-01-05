package tn.esprit.infini.Pidev.Service;

import com.stripe.exception.StripeException;
import tn.esprit.infini.Pidev.entities.Transaction;

public interface IPayment {
    public void persistTransaction(String intentId) throws StripeException;

    public String createPaymentIntent(Transaction transaction) throws StripeException;
    public void confimPayment(String intentId) throws StripeException;
}

