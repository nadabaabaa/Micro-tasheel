package tn.esprit.infini.Pidev.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.infini.Pidev.entities.Account;
import tn.esprit.infini.Pidev.entities.Credit;
import tn.esprit.infini.Pidev.entities.User;

import java.util.List;



import org.springframework.stereotype.Repository;

import tn.esprit.infini.Pidev.entities.Pack;
import tn.esprit.infini.Pidev.entities.User;


import java.util.List;


import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    //User findByLogin(String login);

    @Query("SELECT u FROM User u INNER JOIN u.account a INNER JOIN a.transaction t INNER JOIN t.credit c WHERE c.id = :creditId")
    User findUserByCreditId(@Param("creditId") Long creditId);

    Optional<User> findByEmail(String email);
    //User findByLogin(String login);
    //@Query(value = "SELECT all FROM user WHERE type like '%Client%'")
    //List<User> findAllByTypeEndingWith(@Param("type") String type);

    User findByAccount(Account account);
    //User findByLogin(String login);
    int findByPhoneNumber(int phoneNumber);
    User findById ( int idUser);
    //@Query("SELECT u FROM User u JOIN u.likedPacks p WHERE p.id = :idPack")
    //  public List<User> getLikedByUsers(@Param("idPack") int idPack);

}


