package tn.esprit.infini.Pidev.Services;

import org.springframework.scheduling.annotation.Scheduled;
import tn.esprit.infini.Pidev.entities.Cart;
import tn.esprit.infini.Pidev.entities.Pack;

import java.util.List;

public interface ICartService {
    List<Cart> retrieveAllCarts();

    Cart addCart(Cart c);

    Cart updateCart (Cart c);

    Cart retrieveCart (Integer idCart);

    void deleteCart( Integer idCart);

    double calculateTotalAmount(int idCart);

    double calculateCartTotalWithInterest(int cartId);


    Cart removePackFromCart(int idCart, int idPack);

    Cart findMostExpensiveCart();


    double getMonthlyPackPrice(int idCart);

    List<Pack> getRecommendedPacks(Integer idCart);

     List<Pack> getRecommendedPacksByType(Integer idCart);
    @Scheduled(cron = "0 0 12 * * ?") // exécution à midi chaque jour
    void clearCart();
}
