package com.microservices.cart.cartRepository;


import com.microservices.cart.cartModel.CartItems;
import com.microservices.cart.cartModel.CartModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartRepo extends JpaRepository<CartModel, Long> {


    Optional<CartModel> findByUserEmailId(String userEmailId);
    List<CartModel> findAllByUserEmailId(String userEmailId);
}
