package com.microservices.cart.cartModel;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "Cart")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cartId;
    private String userEmailId;
    private double totalPrice;
    @OneToMany(mappedBy = "cart" , cascade = CascadeType.ALL,
    orphanRemoval = true)
    @JsonManagedReference
    private List<CartItems> items;


}
