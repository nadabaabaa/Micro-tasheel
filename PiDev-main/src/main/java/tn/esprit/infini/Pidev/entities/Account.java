package tn.esprit.infini.Pidev.entities;

import lombok.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Date creationDate;
    private float balance;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")

    private User user;
    @ManyToOne
    @JoinColumn(name = "transaction_id")

    private Transaction transaction;

    public Account(User user) {
        this.user = user;
        this.creationDate = new Date();
        this.balance = 0;
    }


}
