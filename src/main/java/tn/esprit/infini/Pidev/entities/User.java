package tn.esprit.infini.Pidev.entities;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.*;

import javax.persistence.*;

import lombok.*;

@Data
@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
            private int id;
            private String username;
            private String firstName ;
            private String lastName;
            private String email;
            private String password;
            private double phoneNumber;
            @Enumerated(value = EnumType.STRING)
            private Gender gender;
        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(	name = "user_roles",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
        private Set<Role> roles = new HashSet<>();
        @Column(name = "email_verified")
        private boolean emailVerified;
     public User(String username, String email,String firstName, String lastName,Gender gender, double phoneNumber,String encode){
        this.username = username;
        this.email = email;
        this.firstName = firstName ;
        this.lastName = lastName;
        this.gender = gender;
        this.phoneNumber = phoneNumber ;
        this.password = encode;
        this.panier=new Panier();
        this.panier.setUser(this);
     }
    @OneToMany(mappedBy = "user")
    private List<Commande> commandes;

    @OneToMany(mappedBy = "user")
    private List<Transaction> transactions;
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Panier panier;


}
