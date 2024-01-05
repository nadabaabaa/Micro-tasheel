package tn.esprit.infini.Pidev.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;
@Getter
@Setter
@Entity
public class Bill  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idCustomer;
    private Double totalAmount;
    private Date dueDate;
    private Boolean verified=Boolean.FALSE;
    private Date startDate;
    private  Date declaredDate;
    private Double interest;
    @Enumerated(EnumType.STRING)
    private BillType billType;
    private String picture;


}
