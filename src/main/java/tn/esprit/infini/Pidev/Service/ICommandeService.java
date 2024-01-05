package tn.esprit.infini.Pidev.Service;


import tn.esprit.infini.Pidev.entities.Commande;

import java.util.List;

public interface ICommandeService {
    Commande addCommande(Commande commande);
    List<Commande> afficher();
    void annulerCommande(Commande commande);
    void validerCommande(Commande commande);


}
