package tn.esprit.infini.Pidev.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.FutureOrPresent;
import lombok.*;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder


@Table(name = "Credit")
public class Credit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private Double amount;

    private LocalDate dateOfApplication = LocalDate.now();

    @NonNull
    @FutureOrPresent
    private LocalDate dateofobtaining;

    private LocalDate dateoffinish;

    private Double interestrate;

    private Integer duration;
    @Enumerated(EnumType.STRING)
    private Statut statut = Statut.EN_ATTENTE;

    @OneToOne
    @JsonIgnore
    private Guarantor guarantor;

    @Enumerated(EnumType.STRING)
    private TypeCredit typeCredit;

    @OneToMany(mappedBy = "credit", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Transaction> transactions = new ArrayList<>();

    @OneToOne
    @JsonIgnore
    private Insurance insurance;
    @NonNull
    @Enumerated(EnumType.STRING)
    private TypeRemboursement typeRemboursement;





    @PrePersist
    public void validateDates() {
        if (dateoffinish.isBefore(dateofobtaining) || dateofobtaining.isEqual(dateoffinish)) {
            throw new IllegalArgumentException("Date of obtaining cannot be before or equal to date of application");
        }

    }
}





