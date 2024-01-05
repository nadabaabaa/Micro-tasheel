package tn.esprit.infini.Pidev.Services;

import org.springframework.data.domain.Page;
import tn.esprit.infini.Pidev.entities.Pack;
import tn.esprit.infini.Pidev.entities.Reaction;
import tn.esprit.infini.Pidev.entities.TypeReaction;

import java.util.List;
import java.util.Set;

public interface IPackService {
    List<Pack> retrieveAllPacks();

    Pack addPack(Pack p);

    Pack updatePack (Pack p);

    Pack retrievePack (Integer idPack);

    void deletePack( Integer idPack);

  //  Pack likePack(int idPack, int idUser);
    Pack assignPackToCart(Integer idPack, Integer idCart);
    Set<Pack> PacksCart(Integer idCart);
    Pack getMostLikedPack();
    Pack getMostDislikedPack();

    //  boolean userHasLikedPack(int id, int idPack);

    String addReaction(int idPack, int idUser, TypeReaction type);

    double getAverageRating(int idPack);

    List<Pack> getPacksByPriceRange(double minPrice, double maxPrice);

    List<Pack> getPacksSortedByPrice(boolean ascending);

    Page<Pack> getAllPacks(int pageNumber, int pageSize);


    //  List<Pack> getPackRecommendations();

   // List<Pack> getMostPurchasedPacks(int count);
}
