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

    @PostMapping("")
    public ResponseEntity<CartResponseDTO> addCart(@Valid @RequestBody CartDTO cartDTO){
        return new ResponseEntity<>(cartService.addCart(cartDTO.toCart()), HttpStatus.CREATED);
    }

    @GetMapping("/{cartId}")
    public ResponseEntity<CartResponseDTO> getCart(@PathVariable("cartId") String cartId){
        return new ResponseEntity<>(cartService.getCart(cartId),HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<CartListResponseDTO> getAllCarts(@PathVariable("userId") String userId){
        return new ResponseEntity<>(cartService.getAllCartsOfAUser(userId),HttpStatus.OK);
    }

    @PutMapping("/{cartId}")
    public ResponseEntity<CartResponseDTO> updateCart(@Valid @RequestBody UpdateCartNameDTO cartDTO,
                                                      @PathVariable("cartId") String id){
        return new ResponseEntity<>(cartService.updateCartName(cartDTO,id),HttpStatus.OK);

    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<CartResponseDTO> deleteCart(@PathVariable("cartId") String cartId){
        return new ResponseEntity<>(cartService.removeCart(cartId),HttpStatus.CREATED);
    }


    @PostMapping("/{cartId}/products")
    public ResponseEntity<CartResponseDTO> addProductToCart(@Valid @RequestBody ProductItemDTO productItem,
                                                 @PathVariable("cartId") String cartId){
        return new ResponseEntity<>(cartService.addProductToCart(cartId,productItem.toProduct()),HttpStatus.CREATED);
    }


    @DeleteMapping("/{cartId}/products/{productId}")
    public ResponseEntity<CartResponseDTO> removeProductFromCart(@PathVariable("cartId") String cartId,
                                                      @PathVariable("productId") String productId){
        return new ResponseEntity<>(cartService.removeProductFromCart(cartId,productId),HttpStatus.CREATED);
    }


}
