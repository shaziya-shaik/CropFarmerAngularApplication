package com.microservices.cart.cartModel;


import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cart_items")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cartItemId;
    private long cropId;
    private int quantity;
    private double price;
    private double subTotal;
    @ManyToOne
    @JoinColumn(name = "cart_cart_id")
    @JsonBackReference
    private CartModel cart;
}
