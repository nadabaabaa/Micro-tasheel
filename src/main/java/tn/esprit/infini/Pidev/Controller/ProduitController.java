package tn.esprit.infini.Pidev.Controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.infini.Pidev.Service.IProduitService;
import tn.esprit.infini.Pidev.entities.Produit;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/Produit")

public class ProduitController {

    IProduitService iproduitservice;

    @PostMapping("/addproduit")
    Produit addProduit(@RequestBody Produit produit) {
        return iproduitservice.addProduit(produit);
    }
    @GetMapping("/afficher")
    List<Produit> afficher() {
        return iproduitservice.afficher();
    }
    @PutMapping("/modifier")
    Produit modifierProduit(@RequestBody Produit produit){
        return iproduitservice.modifierProduit(produit);
    }

    @DeleteMapping("/delete")

    void DeleteProduit(@RequestBody Produit produit){
        iproduitservice.DeleteProduit(produit);
    }

}


