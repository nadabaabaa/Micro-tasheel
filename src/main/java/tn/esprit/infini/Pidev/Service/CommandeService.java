package tn.esprit.infini.Pidev.Service;



import tn.esprit.infini.Pidev.Repository.ICommandeRep;
import tn.esprit.infini.Pidev.Repository.IPanierRep;
import tn.esprit.infini.Pidev.entities.Commande;
import tn.esprit.infini.Pidev.entities.Statut;

import java.util.List;

public class CommandeService implements ICommandeService{
    ICommandeRep iCommandeRep;
    IPanierRep iPanierRep;
    @Override
    public Commande addCommande(Commande commande) {
        iPanierRep.delete(commande.getPanier());
        return iCommandeRep.save(commande);
    }

    @Override
    public List<Commande> afficher() {
        return iCommandeRep.findAll();
    }

    @Override
    public void annulerCommande(Commande commande) {
         commande.setStatut(Statut.ANNULE);
    }

    @Override
    public void validerCommande(Commande commande) {
        commande.setStatut(Statut.livré);
    }


}
