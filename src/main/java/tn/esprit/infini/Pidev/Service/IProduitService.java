package tn.esprit.infini.Pidev.Service;
import tn.esprit.infini.Pidev.entities.Produit;
import java.util.List;

public interface IProduitService {
    Produit addProduit(Produit produit);
    Produit modifierProduit(Produit produit);
    void DeleteProduit(Produit produit);
    List<Produit> afficher();
    void ajouterProduitapanier(Produit produit);
}
