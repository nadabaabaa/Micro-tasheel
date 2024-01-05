package tn.esprit.infini.Pidev.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.infini.Pidev.entities.User;

import java.util.Optional;

@Repository
public interface IUserRepo extends JpaRepository<User,Integer> {

    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);


    Optional<User> findByEmail(String email);
    //User findByLogin(String login);
    //@Query(value = "SELECT all FROM user WHERE type like '%Client%'")
    //List<User> findAllByTypeEndingWith(@Param("type") String type);

    //User findByLogin(String login);
    int findByPhoneNumber(int phoneNumber);
    User findById ( int idUser);
    //@Query("SELECT u FROM User u JOIN u.likedPacks p WHERE p.id = :idPack")
    //  public List<User> getLikedByUsers(@Param("idPack") int idPack);


}

