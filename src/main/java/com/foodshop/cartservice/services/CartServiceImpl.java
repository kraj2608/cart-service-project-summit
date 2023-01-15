package com.foodshop.cartservice.services;

import com.foodshop.cartservice.models.Cart;
import com.foodshop.cartservice.repositories.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements ICartService{

    private final CartRepository cartRepository;
    @Override
    public Cart addCart(Cart cart) {
        return cartRepository.save(cart);
    }
}
