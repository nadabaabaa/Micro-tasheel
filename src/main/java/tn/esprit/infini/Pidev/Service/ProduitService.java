package tn.esprit.infini.Pidev.Service;


import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Service;
import tn.esprit.infini.Pidev.Repository.IProduitRep;
import tn.esprit.infini.Pidev.entities.Produit;

import java.util.List;
@Service
@Configuration
@EnableScheduling
@AllArgsConstructor
public class ProduitService implements IProduitService {
    @Autowired
    IProduitRep iProduitRep;
    @Override
    public Produit addProduit(Produit produit) {
            return iProduitRep.save(produit);
    }

    @Override
    public Produit modifierProduit(Produit produit) {
        return iProduitRep.save(produit);
    }

    @Override
    public void DeleteProduit(Produit produit) {
        iProduitRep.delete(produit);

    }

    @Override
    public List<Produit> afficher() {
        return iProduitRep.findAll();
    }

    @Override
    public void ajouterProduitapanier(Produit produit) {

    }


}
