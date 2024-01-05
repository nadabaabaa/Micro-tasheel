package tn.esprit.infini.Pidev.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.infini.Pidev.entities.Invest;

import java.util.List;

@Repository
public interface Investrepository extends JpaRepository<Invest,Long> {
    @Query("SELECT i FROM Invest i JOIN  i.transactions t WHERE t.idUser = :userid")
    List<Invest> getInvestByiduser(@Param("userid") Long userid);
    Invest getInvestById(Long id);

}
