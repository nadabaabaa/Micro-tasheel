package tn.esprit.infini.Pidev.entities;
import lombok.*;


import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Getter
@Setter
@Table( name = "Complaint")

public class Complaint implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idcomplaint;
    private LocalDate dateofcomplaint= LocalDate.now();
    @Enumerated(EnumType.STRING)
    private Stateofcomplaint stateofcomplaint=Stateofcomplaint.complaintpending;
    public String description;
    @Enumerated(EnumType.STRING)
    private Typecomplaint typecomplaint;

    @ManyToOne
    User user;
}
