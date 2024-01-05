package tn.esprit.infini.Pidev.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;


import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
@Entity
@Getter
@Setter
public class Fine  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idCustomer;
    private Double totalAmount;
    private Date dueDate;
    private Date startDate;
    private Boolean verified;
    private Double interest;
    private String picture;
    private Date declaredDate;
    @Enumerated(EnumType.STRING)
    private FineType fineType;


}
