package com.microservices.cart.cartController;


import com.microservices.cart.cartDTO.CartRequestDTO;
import com.microservices.cart.cartDTO.CartResponseDTO;
import com.microservices.cart.cartDTO.cartItemsDTO.CartItemsRequestDTO;

import com.microservices.cart.cartService.CartService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private  final CartService service;

    public CartController(CartService service) {
        this.service = service;
    }

    @GetMapping("/cartItems")
    public ResponseEntity<CartResponseDTO> getCartByUser(@RequestParam String userEmailId) {
        CartResponseDTO cart=service.getCartItems(userEmailId);
        if(cart==null) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return  ResponseEntity.ok(cart);
    }

    @PostMapping("/cartItems")
    public ResponseEntity<Void> addCartItems(@RequestBody CartItemsRequestDTO cartItemsRequestDTO,@RequestParam String userEmailId) {
        service.addCartItems(cartItemsRequestDTO , userEmailId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/DeleteCart")
    public ResponseEntity<Void> deleteCart(@RequestParam String userEmailId) {
        service.deleteCart(userEmailId );
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/deleteItem/{cartItemId}")
    public ResponseEntity<Boolean> deleteCartItem(@PathVariable long cartItemId, @RequestParam String userEmailId) {
        boolean result=service.deleteCartItem(cartItemId, userEmailId);
        if(result==false){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
            return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<Void> createCart(String userEmailId) {
        service.createCart(userEmailId);
        return  ResponseEntity.status(HttpStatus.CREATED).build();
    }




}
