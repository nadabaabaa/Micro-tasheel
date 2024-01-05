package tn.esprit.infini.Pidev.dto;



import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import tn.esprit.infini.Pidev.entities.Statut;

import javax.persistence.PrePersist;
import javax.validation.constraints.FutureOrPresent;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class    InvestRequestDTO {

    @NonNull
    private Double amount;
    @NonNull
    @FutureOrPresent
    private LocalDate dateofobtaining;
    private LocalDate dateoffinish;
    private Integer mounths;

    @PrePersist
    public void validateDates() {
        if (dateoffinish.isBefore(dateofobtaining) || dateofobtaining.isEqual(dateoffinish)) {
            throw new IllegalArgumentException("Date of obtaining cannot be before or equal to date of finish");
        }
    }


}





