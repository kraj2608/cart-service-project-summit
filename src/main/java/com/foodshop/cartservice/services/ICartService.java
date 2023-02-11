package com.foodshop.cartservice.services;

import com.foodshop.cartservice.dto.CartListResponseDTO;
import com.foodshop.cartservice.dto.CartResponseDTO;
import com.foodshop.cartservice.dto.UpdateCartNameDTO;
import com.foodshop.cartservice.exceptions.BadRequestException;
import com.foodshop.cartservice.exceptions.CartNotFoundException;
import com.foodshop.cartservice.models.Cart;
import com.foodshop.cartservice.models.ProductItem;


public interface ICartService {
    CartResponseDTO addCart(Cart cart);
    CartResponseDTO updateCartName(UpdateCartNameDTO cartNameDTO, String id) throws
            CartNotFoundException;

    CartResponseDTO getCart(String cartId) throws CartNotFoundException;

    CartListResponseDTO getAllCartsOfAUser(String userId, String type) throws BadRequestException;
    CartResponseDTO addProductToCart(String cartId, ProductItem productItem) throws CartNotFoundException;

    CartResponseDTO removeProductFromCart(String cartId,String productId) throws CartNotFoundException;

    CartResponseDTO removeCart(String cartId) throws CartNotFoundException;


}
