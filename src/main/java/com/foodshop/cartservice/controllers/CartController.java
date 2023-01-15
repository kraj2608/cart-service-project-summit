package com.foodshop.cartservice.controllers;

import com.foodshop.cartservice.dto.CartDTO;
import com.foodshop.cartservice.models.Cart;
import com.foodshop.cartservice.services.ICartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final ICartService cartService;

    @PostMapping("/add")
    public ResponseEntity<Cart> addCart(@Valid @RequestBody CartDTO cartDTO){
        return new ResponseEntity<>(cartService.addCart(cartDTO.toCart()), HttpStatus.CREATED);
    }
}
