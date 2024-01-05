package tn.esprit.infini.Pidev.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table( name = "Transaction")
public class Transaction implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private TypeTransaction typeTransaction;
    private long idUser;
    private long idobject;
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    private Invest invest;
    @ManyToOne(fetch = FetchType.LAZY)
    private Credit credit;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "transaction")
    private List<Account> accounts;
    private Statut statut;
    @Column(name = "amount")
    private  Long amount;
    @Column(name="stripeId")
    private String stripeId;
    @Column(name = "paymentMethod")
    private String paymentMethod;
    @ManyToOne
    @JsonIgnore
    private Invest invests;
    @ManyToOne
    @JsonIgnore
    private Credit credits;
    @OneToMany(mappedBy = "transaction")
    private Set<Pack> packs;
    @Column
    private String status;


    public Transaction(TypeTransaction typeTransaction, long idUser, long idobject, Date date, Long amount, String stripeId, String paymentMethod,String status) {
        this.typeTransaction = typeTransaction;
        this.idUser = idUser;
        this.idobject = idobject;
        this.date = date;
        this.amount = amount;
        this.stripeId=stripeId;
        this.paymentMethod=paymentMethod;
        this.status=status;

    }


}
