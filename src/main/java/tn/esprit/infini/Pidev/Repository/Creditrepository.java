package tn.esprit.infini.Pidev.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.infini.Pidev.entities.*;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface Creditrepository extends JpaRepository<Credit,Long>, JpaSpecificationExecutor<Credit>
{

    @Query("SELECT c FROM Credit c WHERE "
            + "(:id IS NULL OR c.id = :id) AND "
            + "(:amount IS NULL OR c.amount = :amount) AND "
            + "(:dateOfApplication IS NULL OR c.dateOfApplication = :dateOfApplication) AND "
            + "(:dateofobtaining IS NULL OR c.dateofobtaining = :dateofobtaining) AND "
            + "(:dateoffinish IS NULL OR c.dateoffinish = :dateoffinish) AND "
            + "(:interestrate IS NULL OR c.interestrate = :interestrate) AND "
            + "(:duration IS NULL OR c.duration = :duration) AND "
            + "(:statut IS NULL OR c.statut = :statut) AND "
            + "(:guarantor IS NULL OR c.guarantor = :guarantor) AND "
            + "(:typeCredit IS NULL OR c.typeCredit = :typeCredit) AND "
            + "(:insurance IS NULL OR c.insurance = :insurance)")
    List<Credit> findCreditsByAttributes(
            @Param("id") Long id,
            @Param("amount") Double amount,
            @Param("dateOfApplication") LocalDate dateOfApplication,
            @Param("dateofobtaining") LocalDate dateofobtaining,
            @Param("dateoffinish") LocalDate dateoffinish,
            @Param("interestrate") Double interestrate,
            @Param("duration") Integer duration,
            @Param("statut") Statut statut,
            @Param("guarantor") Guarantor guarantor,
            @Param("typeCredit") TypeCredit typeCredit,
            @Param("insurance") Insurance insurance);



    @Query("SELECT c FROM Credit c JOIN c.transactions t WHERE t.idUser = :userId")
           List <Credit> getCreditByiduser(@Param("userId") Long userid);

    Credit findByAmount(double amount);

}
