package com.foodshop.cartservice.services;

import com.foodshop.cartservice.dto.UpdateCartNameDTO;
import com.foodshop.cartservice.exceptions.BadRequestException;
import com.foodshop.cartservice.exceptions.CartAuthorizationAccessDeniedException;
import com.foodshop.cartservice.exceptions.CartNotFoundException;
import com.foodshop.cartservice.models.Cart;
import com.foodshop.cartservice.repositories.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements ICartService{

    private final CartRepository cartRepository;
    @Override
    public Cart addCart(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public Cart updateCartName(UpdateCartNameDTO cartNameDTO, String id) throws CartNotFoundException,CartAuthorizationAccessDeniedException {
        if (!cartRepository.existsByIdAndDeleted(id,false)){
            throw new CartNotFoundException("Cart not found");
        }
        Cart cart = cartRepository.getCartByIdAndDeleted(id,false);
        if(!Objects.equals(cart.getOwnerId(), cartNameDTO.getOwnerId())){
            throw new CartAuthorizationAccessDeniedException("Access denied for this cart");
        }
        cartRepository.delete(cart);
        cart.setCartName(cartNameDTO.getCartName());
        cartRepository.save(cart);
        return cart;
    }

    @Override
    public List<Cart> getAllCartsOfAUser(String userId, String type) throws BadRequestException {
        if(Objects.equals(type, "PURCHASED")){
            return cartRepository.getCartsByOwnerIdAndPurchasedAndDeleted(userId,true,false);
        }else if (Objects.equals(type, "PENDING")){
            return cartRepository.getCartsByOwnerIdAndPurchasedAndDeleted(userId,false,false);
        }else{
            throw new BadRequestException("Invalid cart type");
        }

    }
}
