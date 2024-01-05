package tn.esprit.infini.Pidev.Repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import tn.esprit.infini.Pidev.entities.Insurance;
import org.springframework.data.repository.query.Param;
import tn.esprit.infini.Pidev.entities.Account;
import tn.esprit.infini.Pidev.entities.Insurance;
import tn.esprit.infini.Pidev.entities.Typeinsurance;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface InsuranceRepository extends CrudRepository<Insurance,Integer> {
    @Query(value = "SELECT COUNT(*) FROM Insurance u WHERE u.user_id = :userId", nativeQuery = true)
    Integer numberofinsurancebyuser(@Param("userId") Integer userId);

@Query(value = "SELECT * FROM Insurance u WHERE u.archived= false ",nativeQuery = true)
    List<Insurance> retrieveinsurance();

    @Query(value = "SELECT * FROM Insurance u WHERE u.archived= true ",nativeQuery = true)
    List<Insurance> retrieveArchivedinsurance();
    @Query("SELECT DISTINCT a FROM Account a "
            + "JOIN a.transaction t "
            + "JOIN t.credits c "
            + "JOIN c.insurance i "
            + "GROUP BY a.id "
            + "HAVING COUNT(i.idinsurance) > 3")
    List<Account> findAccountsByNumberOfInsurances();
    @Query("SELECT i FROM Insurance i WHERE i.endinsurance < :date")
    List<Insurance> findByEndinsuranceBefore(@Param("date") Date date);

    @Query("SELECT c.Typeinsurance, COUNT(c) FROM Insurance c GROUP BY c.Typeinsurance")
    List<Object[]> getInsuranceByType();


    Insurance findByIdinsurance(int idinsurance);
    @Query("SELECT i.Typeinsurance, COUNT(i) FROM Insurance i GROUP BY i.Typeinsurance")
    List<Object[]> countInsurancesByType();

}

