package tn.esprit.infini.Pidev.Services;

import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tn.esprit.infini.Pidev.Repository.CartRepository;
import tn.esprit.infini.Pidev.Repository.PackRepository;
import tn.esprit.infini.Pidev.Repository.UserRepository;
import tn.esprit.infini.Pidev.entities.Cart;
import tn.esprit.infini.Pidev.entities.Pack;
import tn.esprit.infini.Pidev.entities.TypePack;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Service
@AllArgsConstructor


public class CartService implements ICartService {

        CartRepository cartRepository;
        UserRepository userRepository;
    private final PackRepository packRepository;

    @Override
        public List<Cart> retrieveAllCarts() {

        return (List<Cart>) cartRepository.findAll();
        }

        @Override
        public Cart addCart(Cart c) {

            return cartRepository.save(c);
        }

        @Override
        public Cart updateCart(Cart c) {
            return cartRepository.save(c);    }

        @Override
        public Cart retrieveCart(Integer idCart) {

            return cartRepository.findById(idCart).get();
        }

        @Override
        public void deleteCart(Integer idCart) {
            cartRepository.deleteById(idCart);

        }

    public double calculateTotalAmount(int idCart) {
        Cart cart = cartRepository.findByIdCart(idCart);
        double totalAmount = 0.0;
        Set<Pack> packs = cart.getPack();
        for (Pack p : packs) {
            totalAmount += p.getPrice() ;
        }
        return totalAmount;
    }

    public double calculateCartTotalWithInterest(int idCart) {
        Cart cart = cartRepository.findById(idCart).orElse(null);

        double totalAmount = cart.getPack().stream()
                .mapToDouble(pack -> pack.getPrice() )
                .sum();
        double interestRate = 0.0;
        if (totalAmount < 1000) {
            interestRate = 0.18;
        } else if (totalAmount >= 1000 && totalAmount <= 7000) {
            interestRate = 0.17;
        } else if (totalAmount > 7000) {
            interestRate = 0.15;
        }
        double totalAmountWithInterest = totalAmount + (totalAmount * interestRate);
        return totalAmountWithInterest;
    }


    @Override
    public Cart removePackFromCart(int idCart, int idPack) {
        Pack pack = packRepository.findByIdPack(idPack);
        Cart cart = cartRepository.findByIdCart(idCart);
        pack.setCart(null);
        packRepository.save(pack);
        return null;

    }

    @Override
    public Cart findMostExpensiveCart() {
        List<Cart> carts = cartRepository.findAll();
        Cart mostExpensiveCart = carts.get(0);
        double mostExpensiveCartTotalAmount = calculateTotalAmount(mostExpensiveCart.getIdCart());
        for (Cart cart : carts) {
            double currentCartTotalAmount = calculateTotalAmount(cart.getIdCart());
            if (currentCartTotalAmount > mostExpensiveCartTotalAmount) {
                mostExpensiveCart = cart;
                mostExpensiveCartTotalAmount = currentCartTotalAmount;
            }
        }
        return mostExpensiveCart;
    }


    @Override
    public double getMonthlyPackPrice(int idCart) {
        Cart cart = cartRepository.findByIdCart(idCart);
        double totalAmountWithInterest = calculateCartTotalWithInterest(cart.getIdCart());
        double monthlyPrice = totalAmountWithInterest/ cart.getNbreMounths();
        return monthlyPrice;
    }


    @Override
    public List<Pack> getRecommendedPacks(Integer idCart) {
        Cart cart = cartRepository.findByIdCart(idCart);
        List<Pack> packsInCart = cart.getPack().stream()
                .distinct()
                .collect(Collectors.toList());
        return packRepository.findAll().stream()
                .filter(pack -> !cart.getPack().contains(pack))
                .collect(Collectors.toList());
    }

    @Override
    public List<Pack> getRecommendedPacksByType(Integer idCart) {
        Cart cart = cartRepository.findByIdCart(idCart);
        TypePack packType = cart.getPack().stream()
                .map(Pack::getTypePack)
                .distinct()
                .findFirst()
                .orElse(null);
        if (packType == null) {
            return Collections.emptyList();
        }
        return packRepository.findByTypePack(packType).stream()
                .filter(pack -> !cart.getPack().contains(pack))
                .collect(Collectors.toList());
    }


    @Override
    @Scheduled(cron = "0 0 12 * * ?") // exécution à midi chaque jour
    public void clearCart() {
        LocalDate threeMonthsAgo = LocalDate.now().minusMonths(3);
        List<Pack> expiredPacks = packRepository.findByCreatedAtBefore(threeMonthsAgo);
        expiredPacks.forEach(pack -> {
            pack.setCart(null);
            packRepository.save(pack);
        });
    }


}





