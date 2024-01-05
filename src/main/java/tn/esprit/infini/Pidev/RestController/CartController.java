
package tn.esprit.infini.Pidev.RestController;


import com.twilio.exception.TwilioException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.infini.Pidev.Repository.CartRepository;
import tn.esprit.infini.Pidev.Repository.PackRepository;
import tn.esprit.infini.Pidev.Services.CartService;
import tn.esprit.infini.Pidev.Services.ICartService;
import tn.esprit.infini.Pidev.Services.PackService;
import tn.esprit.infini.Pidev.entities.Cart;
import tn.esprit.infini.Pidev.entities.Pack;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/Cart")
public class CartController {

    ICartService iCartService;
    CartService cartService;
    private final CartRepository cartRepository;

    PackService packService;
    private final PackRepository packRepository;

    @GetMapping("/getCart")
    List<Cart> afficher() {
        return iCartService.retrieveAllCarts();
    }

        @PostMapping("/addCart")
    Cart ajouter(@RequestBody Cart cart) {
        return iCartService.addCart(cart);
    }

    @GetMapping("/getCartById/{idCart}")
    Cart afficherAvecId(@PathVariable int idCart){
        return iCartService.retrieveCart(idCart);
    }

    @PutMapping("/updatePack")
    public Cart updatePack(@RequestBody Cart cart) {
        return iCartService.updateCart(cart);
    }

    @DeleteMapping("/deletePack/{idPack}")
    void deletePack(@PathVariable ("idGuarantor") Integer idPack){
        iCartService.deleteCart(idPack);
    }
    @PutMapping("/updateCart")
    public Cart updateCart(@RequestBody Cart cart) {
        return iCartService.updateCart(cart);
    }

    @DeleteMapping("/deleteCart/{idCart}")
    void deleteCart(@PathVariable ("idCart") Integer idCart)
    {

        iCartService.deleteCart(idCart);
    }

    @GetMapping("/getSumCart/{idCart}")
    double calculateTotalAmount(@PathVariable int idCart){

        return iCartService.calculateTotalAmount(idCart);
    }
    @GetMapping("/getSumCartInterest/{idCart}")
    double calculateCartTotalWithInterest(@PathVariable int idCart){

        return iCartService.calculateCartTotalWithInterest(idCart);
    }

    @DeleteMapping("/removePackFromCart/{idCart}/{idPack}") // c bon khedmet
    public Cart removePackFromCart(@PathVariable int idCart,
                                   @PathVariable int idPack){
        cartService.removePackFromCart(idCart,idPack);
        return null;
    }

    @GetMapping("/{idCart}/monthly-price")
    public double getMonthlyPrice(@PathVariable("idCart") int idCart) {
        Cart cart = cartRepository.findByIdCart(idCart);
        return cartService.getMonthlyPackPrice(idCart);
    }

    @GetMapping("/most-expensive/{idCart}")
    public Cart findMostExpensiveCart(@PathVariable Integer idCart) {
        Cart mostExpensiveCart = cartService.findMostExpensiveCart();
        return mostExpensiveCart ;
    }

    @GetMapping("/recommended-packs/{idCart}")
    public List<Pack> getRecommendedPacks(@PathVariable Integer idCart) {
        List<Pack> recommendedPacks = cartService.getRecommendedPacks(idCart);
        return recommendedPacks;
    }

    @GetMapping("/recommended-packs-by-type/{idCart}")
    public List<Pack> getRecommendedPacksByType(@PathVariable Integer idCart) {
        List<Pack> recommendedPacks = cartService.getRecommendedPacksByType(idCart);
        return recommendedPacks;
    }


    @PostMapping("/clearExpiredPacks")
    public String clearExpiredPacks() {
        cartService.clearCart();
        return ("Expired packs have been cleared from cart");
    }

}


