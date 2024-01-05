package tn.esprit.infini.Pidev.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.infini.Pidev.entities.Cart;


@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    Cart findByIdCart(Integer idCart);
    //Cart findByIdCartAndIdPack(Integer idCart, Integer idPack);

}
