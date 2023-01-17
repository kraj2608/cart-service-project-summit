package com.foodshop.cartservice.controllers;

import com.foodshop.cartservice.dto.CartDTO;
import com.foodshop.cartservice.dto.ProductItemDTO;
import com.foodshop.cartservice.dto.UpdateCartNameDTO;
import com.foodshop.cartservice.models.Cart;
import com.foodshop.cartservice.services.ICartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final ICartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<Cart>> getAllCarts(@PathVariable("userId") String userId,
                                                  @RequestParam("cart_type") String type){
        return new ResponseEntity<>(cartService.getAllCartsOfAUser(userId,type),HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<Cart> addCart(@Valid @RequestBody CartDTO cartDTO){
        return new ResponseEntity<>(cartService.addCart(cartDTO.toCart()), HttpStatus.CREATED);
    }

    @PostMapping("/add/product/{cartId}")
    public ResponseEntity<Cart> addProductToCart(@Valid @RequestBody ProductItemDTO productItem,
                                                 @PathVariable("cartId") String cartId){
        return new ResponseEntity<>(cartService.addProductToCart(cartId,productItem.toProduct()),HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Cart> updateCart(@Valid @RequestBody UpdateCartNameDTO cartDTO,
                                           @PathVariable("id") String id){
        return new ResponseEntity<>(cartService.updateCartName(cartDTO,id),HttpStatus.OK);
        
    }
}
