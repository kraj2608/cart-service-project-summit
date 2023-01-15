package com.foodshop.cartservice.services;

import com.foodshop.cartservice.dto.UpdateCartNameDTO;
import com.foodshop.cartservice.exceptions.CartAuthorizationAccessDeniedException;
import com.foodshop.cartservice.exceptions.CartNotFoundException;
import com.foodshop.cartservice.models.Cart;

public interface ICartService {
    Cart addCart(Cart cart);
    Cart updateCartName(UpdateCartNameDTO cartNameDTO, String id) throws CartNotFoundException, CartAuthorizationAccessDeniedException;
}
