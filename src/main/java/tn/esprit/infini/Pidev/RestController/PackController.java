
package tn.esprit.infini.Pidev.RestController;

import lombok.AllArgsConstructor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.infini.Pidev.Repository.CartRepository;
import tn.esprit.infini.Pidev.Repository.PackRepository;
import tn.esprit.infini.Pidev.Services.IPackService;
import tn.esprit.infini.Pidev.Services.PackService;
import tn.esprit.infini.Pidev.Services.UserService;
import tn.esprit.infini.Pidev.entities.Pack;
import tn.esprit.infini.Pidev.entities.Reaction;
import tn.esprit.infini.Pidev.entities.TypeReaction;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor

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
    Pack ajouter(@RequestBody Pack pack) {

        return iPackService.addPack(pack);
    }
    @GetMapping("/getPackById/{idPack}")
    Pack afficherAvecId(@PathVariable int idPack){

        return iPackService.retrievePack(idPack);
    }
    @PutMapping("/updatePack")
    public Pack updatePack(@RequestBody Pack pack) {
        return iPackService.updatePack(pack);
    }
    @DeleteMapping("/deletePack/{idPack}")
    void  deletePack(@PathVariable ("idPack") Integer idPack)
    {
        iPackService.deletePack(idPack);
    }

    // rating = like et dislike


    // affiche les packs d'une panier
    @GetMapping("/CartPacks/{idCart}") // affiche les packs d'une carte
    public Set<Pack> CartPack (@PathVariable ("idCart") Integer idCart)
    {
        return iPackService.PacksCart(idCart);
    }

    // affecter un pack au panier
    @PutMapping("/assignPackToCart/{idPack}/{idCart}") // affecte un pack au panier ( bouton ajouter au panier)
    public Pack assignPackToCart(@PathVariable Integer idPack, @PathVariable Integer idCart){
        return iPackService.assignPackToCart( idPack,  idCart);

    }
    // rating:

    @PostMapping("/addReactionToPack/{idPack}/reaction")
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
    public double getAverageRating(@PathVariable int idPack) {

        return packService.getAverageRating(idPack);
    }

    @GetMapping("/mostLikedPacks")
    public Pack findMostLikedPacks() {

        return packService.getMostLikedPack();
    }

    @GetMapping("/mostDislikedPacks")
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
