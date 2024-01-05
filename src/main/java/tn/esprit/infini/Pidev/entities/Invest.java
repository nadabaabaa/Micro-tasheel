
package tn.esprit.infini.Pidev.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.*;
import jakarta.persistence.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

import jakarta.persistence.*;


import jakarta.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table( name = "Invest")
public class Invest implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double amount;
    private LocalDate dateofapplication=LocalDate.now();
    private LocalDate dateofobtaining;
    private LocalDate dateoffinish;
    private  double interestrate=0.6;
    private Integer mounths;
    @Enumerated(EnumType.STRING)
    private Statut statut = Statut.EN_ATTENTE;
    @OneToMany(mappedBy = "invest", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Transaction> transactions=new ArrayList<>();

    @PrePersist
    public void validateDates() {
        if (dateoffinish.isBefore(dateofobtaining) || dateofobtaining.isEqual(dateoffinish)) {
            throw new IllegalArgumentException("Date of obtaining cannot be before or equal to date of finish");
        }
    }

}



