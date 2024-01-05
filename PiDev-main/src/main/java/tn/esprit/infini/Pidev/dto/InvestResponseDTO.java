package tn.esprit.infini.Pidev.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import tn.esprit.infini.Pidev.entities.Statut;
import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvestResponseDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double amount;
    private LocalDate dateofapplication=LocalDate.now();
    private LocalDate dateofobtaining;
    private LocalDate dateoffinish;
    private  double interestrate;
    private Integer mounths;
    @Enumerated(EnumType.STRING)
    private Statut statut = Statut.EN_ATTENTE;
}

