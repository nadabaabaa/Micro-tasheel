package tn.esprit.infini.Pidev.entities;


import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;

import java.util.Set;
import java.io.Serializable;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.*;

import jakarta.persistence.*;


import java.util.Set;

import jakarta.persistence.*;


import java.io.Serializable;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table( name = "Cart")


public class Cart implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "idCart")
        private int idCart;
        private String productName;
        private String productDescription;
        private int quantity;
        private double price;
        private double mouthlyamount;
        private int nbreMounths;
        @Enumerated(EnumType.STRING)
        private TypePack typePAck;
            @OneToMany(mappedBy = "cart")
             @JsonIgnore
             public Set<Pack> pack;



}



