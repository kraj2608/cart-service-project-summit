package com.foodshop.cartservice.services;

import com.foodshop.cartservice.dto.UpdateCartNameDTO;
import com.foodshop.cartservice.exceptions.BadRequestException;
import com.foodshop.cartservice.exceptions.CartAuthorizationAccessDeniedException;
import com.foodshop.cartservice.exceptions.CartNotFoundException;
import com.foodshop.cartservice.models.Cart;
import com.foodshop.cartservice.models.ProductItem;

import java.util.List;

public interface ICartService {
    Cart addCart(Cart cart);
    Cart updateCartName(UpdateCartNameDTO cartNameDTO, String id) throws
            CartNotFoundException, CartAuthorizationAccessDeniedException;

    List<Cart> getAllCartsOfAUser(String userId,String type) throws BadRequestException;
    Cart addProductToCart(String cartId, ProductItem productItem) throws CartNotFoundException;
}
