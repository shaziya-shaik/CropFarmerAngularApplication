package com.microservices.cart.cartRepository;

import com.microservices.cart.cartModel.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepo extends JpaRepository<CartItems,Long> {
}
