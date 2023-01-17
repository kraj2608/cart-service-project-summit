package com.foodshop.cartservice.controllers;

import com.foodshop.cartservice.dto.*;
import com.foodshop.cartservice.services.ICartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
@RequiredArgsConstructor
public class CartController {

    private final ICartService cartService;

    @GetMapping("/{cartId}/{userId}")
    public ResponseEntity<CartResponseDTO> getCart(@PathVariable("userId") String userId,
                                        @PathVariable("cartId") String cartId){
        return new ResponseEntity<>(cartService.getCart(cartId,userId),HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartListResponseDTO> getAllCarts(@PathVariable("userId") String userId,
                                                           @RequestParam(required = false,value = "cart_type") String type){
        return new ResponseEntity<>(cartService.getAllCartsOfAUser(userId,type),HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<CartResponseDTO> addCart(@Valid @RequestBody CartDTO cartDTO){
        return new ResponseEntity<>(cartService.addCart(cartDTO.toCart()), HttpStatus.CREATED);
    }

    @PostMapping("/{cartId}/add/product")
    public ResponseEntity<CartResponseDTO> addProductToCart(@Valid @RequestBody ProductItemDTO productItem,
                                                 @PathVariable("cartId") String cartId){
        return new ResponseEntity<>(cartService.addProductToCart(cartId,productItem.toProduct()),HttpStatus.CREATED);
    }

    @PutMapping("/{cartId}/update/name")
    public ResponseEntity<CartResponseDTO> updateCart(@Valid @RequestBody UpdateCartNameDTO cartDTO,
                                           @PathVariable("cartId") String id){
        return new ResponseEntity<>(cartService.updateCartName(cartDTO,id),HttpStatus.OK);
        
    }

    @DeleteMapping("/{cartId}/remove/product/{productId}")
    public ResponseEntity<CartResponseDTO> removeProductFromCart(@PathVariable("cartId") String cartId,
                                                      @PathVariable("productId") String productId){
        return new ResponseEntity<>(cartService.removeProductFromCart(cartId,productId),HttpStatus.CREATED);
    }

    @DeleteMapping("/{cartId}/{userId}")
    public ResponseEntity<CartResponseDTO> deleteCart(@PathVariable("cartId") String cartId,
                                           @PathVariable("userId") String userId){
        return new ResponseEntity<>(cartService.removeCart(cartId,userId),HttpStatus.CREATED);
    }
}
