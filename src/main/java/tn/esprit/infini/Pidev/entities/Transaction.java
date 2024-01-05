package tn.esprit.infini.Pidev.entities;


import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table( name = "Transaction")
public class    Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private TypeTransaction typeTransaction;
    private long idUser;
    private long idobject;
    private Date date;
    private String status;
    @Enumerated(EnumType.STRING)
    private Statut statut;
    private  Long amount;
    private String stripeId;
    private String paymentMethod;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne
    @JoinColumn(name = "commande_id")
    private Commande commande;
    @Enumerated(EnumType.STRING)
    private ModePaiement modePaiement;
        public Transaction(TypeTransaction typeTransaction, long idUser, long idobject, Date date, Long amount, String stripeId, String paymentMethod,String status) {
        this.typeTransaction = typeTransaction;
        this.idUser = idUser;
        this.idobject = idobject;
        this.date = date;
        this.amount = amount;
        this.stripeId=stripeId;
        this.paymentMethod=paymentMethod;
        this.status= status;
    }




}
