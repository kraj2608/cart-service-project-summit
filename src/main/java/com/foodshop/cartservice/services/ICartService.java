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

    Cart getCart(String cartId,String userId) throws CartNotFoundException, CartAuthorizationAccessDeniedException;

    List<Cart> getAllCartsOfAUser(String userId,String type) throws BadRequestException;
    Cart addProductToCart(String cartId, ProductItem productItem) throws CartNotFoundException;

    Cart removeProductFromCart(String cartId,String productId) throws CartNotFoundException;

    Cart removeCart(String cartId,String userId) throws CartNotFoundException,CartAuthorizationAccessDeniedException;


}
