package tn.esprit.infini.Pidev.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.infini.Pidev.entities.Fine;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.infini.Pidev.entities.Fine;
import tn.esprit.infini.Pidev.entities.FineType;

import java.util.Date;
import java.util.List;

@Repository

public interface FineRepository extends JpaRepository<Fine,Long> {
    @Query("SELECT f FROM Fine f WHERE " +
            "(:idCustomer is null or f.idCustomer = :idCustomer) and " +
            "(:totalAmount is null or f.totalAmount >= :totalAmount) and " +
            "(:startDate is null or f.startDate between :startDate and :endDate) and " +
            "(:verified is null or f.verified = :verified) and " +
            "(:interest is null or f.interest >= :interest) and " +
            "(:fineType is null or f.fineType = :fineType) and " +
            "(:picture is null or f.picture like %:picture%) and " +
            "(:declaredDate is null or f.declaredDate >= :declaredDate)")
    List<Fine> searchFines(Long idCustomer, Double totalAmount, Date startDate, Date endDate, Boolean verified, Double interest, FineType fineType, String picture, Date declaredDate);
}

