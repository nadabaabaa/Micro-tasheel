package tn.esprit.infini.Pidev.dto;
import lombok.*;
import tn.esprit.infini.Pidev.entities.Statut;
import tn.esprit.infini.Pidev.entities.TypeCredit;
import tn.esprit.infini.Pidev.entities.TypeRemboursement;
import java.time.LocalDate;
import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditRequestDTO {

    @NonNull
    private Double amount;
    @NonNull
    @FutureOrPresent
    private LocalDate dateofobtaining;
    private LocalDate  dateoffinish;
    private  Integer duration;


    @NonNull
    private TypeRemboursement typeRemboursement;
    @NonNull
    private TypeCredit typeCredit;
    @PrePersist
    public void validateDates() {
            if (dateoffinish.isBefore(dateofobtaining) || dateofobtaining.isEqual(dateoffinish)) {
                throw new IllegalArgumentException("Date of obtaining cannot be before or equal to date of finish");
            }
        }


    }





