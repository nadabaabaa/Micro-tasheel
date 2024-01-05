package tn.esprit.infini.Pidev.entities;

import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import lombok.*;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table( name = "insurance")
public class Insurance implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idinsurance;
    private String insured;
    private Double insuredAmount;
    private Date startinsurance;
    private Date endinsurance;
    private String coverage;
    private Double deductible;
    private String claimsHistory;
    @ManyToOne
    Pack pack;
    @OneToOne
    Credit credit;
    private Double levelofrisk;
    private Boolean archived;
    @Enumerated(EnumType.STRING)
    private Typeinsurance  Typeinsurance;
    @Enumerated(EnumType.STRING)
    private Statut  statut;




}

