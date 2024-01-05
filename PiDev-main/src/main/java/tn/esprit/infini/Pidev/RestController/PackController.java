
package tn.esprit.infini.Pidev.RestController;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import tn.esprit.infini.Pidev.Repository.CartRepository;
import tn.esprit.infini.Pidev.Repository.PackRepository;
import tn.esprit.infini.Pidev.Services.IPackService;
import tn.esprit.infini.Pidev.Services.PackService;
import tn.esprit.infini.Pidev.Services.user_management.UserService;
import tn.esprit.infini.Pidev.entities.Pack;
import tn.esprit.infini.Pidev.entities.Reaction;
import tn.esprit.infini.Pidev.entities.TypeReaction;

import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200/")

@RequestMapping("/Pack")
public class PackController {

    IPackService iPackService;
    PackRepository packRepository;
    UserService userService;
    PackService packService;
    CartRepository cartRepository;

   // CRUD:
    @GetMapping("/getPack")

    List<Pack> afficher() {
        return iPackService.retrieveAllPacks();
    }

    @PostMapping("/addPack")
    @PreAuthorize("hasRole('ADMIN')")

    Pack ajouter(@RequestBody Pack pack) {

        return iPackService.addPack(pack);
    }
    @GetMapping("/getPackById/{idPack}")
    Pack afficherAvecId(@PathVariable int idPack){

        return iPackService.retrievePack(idPack);
    }
    @PutMapping("/updatePack")
    @PreAuthorize("hasRole('ADMIN')")
    public Pack updatePack(@RequestBody Pack pack) {
        return iPackService.updatePack(pack);
    }
    @DeleteMapping("/deletePack/{idPack}")
    @PreAuthorize("hasRole('ADMIN')")

    void  deletePack(@PathVariable ("idPack") Integer idPack)
    {
        iPackService.deletePack(idPack);
    }

    // rating = like et dislike


    // affiche les packs d'une panier
    @GetMapping("/CartPacks/{idCart}") // affiche les packs d'une carte
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public Set<Pack> CartPack (@PathVariable ("idCart") Integer idCart)
    {
        return iPackService.PacksCart(idCart);
    }

    // affecter un pack au panier
    @PutMapping("/assignPackToCart/{idPack}/{idCart}") // affecte un pack au panier ( bouton ajouter au panier)
    @PreAuthorize("hasRole('USER')")
    public Pack assignPackToCart(@PathVariable Integer idPack, @PathVariable Integer idCart){
        return iPackService.assignPackToCart( idPack,  idCart);

    }
    // rating:

    @PostMapping("/addReactionToPack/{idPack}/reaction")
    @PreAuthorize("hasRole('USER')")

    public void addReactionToPack(@PathVariable int idPack,
                                  @RequestBody Reaction reaction) {
        // récupérer les informations du formulaire
        int idUser = reaction.getIdUser();
        TypeReaction type = reaction.getTP();

        // appeler le service pour ajouter la réaction au pack
        packService.addReaction(idPack, idUser, type);

    }

    @PostMapping("/{idPack}/{idUser}/{type}")
    public String addReaction(
            @PathVariable("idPack") int idPack,
            @PathVariable("idUser") int idUser,
            @PathVariable("type") TypeReaction type) {

        return packService.addReaction(idPack, idUser, type);

    }


    @GetMapping("/average-rating/{idPack}")
    @PreAuthorize("hasRole('ADMIN')")

    public double getAverageRating(@PathVariable int idPack) {

        return packService.getAverageRating(idPack);
    }

    @GetMapping("/mostLikedPacks")
    @PreAuthorize("hasRole('ADMIN')")

    public Pack findMostLikedPacks() {

        return packService.getMostLikedPack();
    }

    @GetMapping("/mostDislikedPacks")
    @PreAuthorize("hasRole('ADMIN')")
    public Pack findMostDislikedProducts() {

        return iPackService.getMostDislikedPack();
    }

    @GetMapping("/byPriceRange")
    public List<Pack> getPacksByPriceRange(@RequestParam("minPrice") double minPrice,
                                           @RequestParam("maxPrice") double maxPrice) {
        return packService.getPacksByPriceRange(minPrice, maxPrice);
    }

    @GetMapping("/orderByPriceAsc")
    public List<Pack> getPacksOrderByPriceAsc() {
        return packRepository.findByOrderByPriceAsc();
    }

    @GetMapping("/orderByPriceDesc")
    public List<Pack> getPacksOrderByPriceDesc() {
        return packRepository.findByOrderByPriceDesc();
    }

    @GetMapping("/packs")

    public Page<Pack> getAllPacks(@RequestParam("page") int page,
                                  @RequestParam("size") int size) {
        Page<Pack> packs = packService.getAllPacks(page, size);
        return packs;
    }

}
