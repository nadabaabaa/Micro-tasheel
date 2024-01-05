package tn.esprit.infini.Pidev.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.infini.Pidev.entities.Cart;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.infini.Pidev.entities.Pack;
import tn.esprit.infini.Pidev.entities.TypePack;
import tn.esprit.infini.Pidev.entities.User;

import java.awt.print.Pageable;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public interface PackRepository extends JpaRepository<Pack, Integer> {


    Pack findByIdPack(Integer idPack);
    // Pack findByPrice (double price);


    List<Pack> findByPriceBetween(double minPrice, double maxPrice);

    List<Pack> findByOrderByPriceAsc();

    List<Pack> findByOrderByPriceDesc();

    List<Pack> findByTypePack(TypePack typePack);

    List<Pack> findByCreatedAtBefore(LocalDate threeMonthsAgo);

    // @Query(value = "select p from Pack p order by p.likes desc")
    //List<Pack> findMostLikedPack();
   //  @Query(value = "select p from Pack p order by p.dislikes desc")
    // List<Pack> findMostDislikedPack();

    //@Query("SELECT u FROM User u JOIN u.likedPacks p WHERE p.id = :idPack")
   // public List<User> getLikedByUsers(@Param("idPack") int idPack);





}
