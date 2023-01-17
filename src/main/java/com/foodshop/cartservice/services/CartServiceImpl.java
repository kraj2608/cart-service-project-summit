package com.foodshop.cartservice.services;

import com.foodshop.cartservice.dto.UpdateCartNameDTO;
import com.foodshop.cartservice.exceptions.BadRequestException;
import com.foodshop.cartservice.exceptions.CartAuthorizationAccessDeniedException;
import com.foodshop.cartservice.exceptions.CartNotFoundException;
import com.foodshop.cartservice.models.Cart;
import com.foodshop.cartservice.models.ProductItem;
import com.foodshop.cartservice.repositories.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

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
    public Cart getCart(String cartId, String userId) throws CartNotFoundException, CartAuthorizationAccessDeniedException {
        cartExistCheck(cartId);
        Cart cart = cartRepository.getCartByIdAndDeleted(cartId,false);
        if (!Objects.equals(cart.getOwnerId(), userId)){
            throw new CartAuthorizationAccessDeniedException("Access denied");
        }
        return cart;
    }

    @Override
    public List<Cart> getAllCartsOfAUser(String userId, String type) throws BadRequestException {
        if (type == null){
            return Stream.concat(cartRepository.getCartsByOwnerIdAndPurchasedAndDeleted(userId,true,false).stream(),
                    cartRepository.getCartsByOwnerIdAndPurchasedAndDeleted(userId,false,false).stream()).toList();
        }else if(Objects.equals(type, "PURCHASED")){
            return cartRepository.getCartsByOwnerIdAndPurchasedAndDeleted(userId,true,false);
        }else if (Objects.equals(type, "PENDING")){
            return cartRepository.getCartsByOwnerIdAndPurchasedAndDeleted(userId,false,false);
        }else{
            throw new BadRequestException("Invalid cart type");
        }

    }

    @Override
    public Cart addProductToCart(String cartId, ProductItem newProductITem) throws CartNotFoundException {
        cartExistCheck(cartId);
        Cart cart = cartRepository.getCartByIdAndDeleted(cartId,false);
        AtomicBoolean findExistingProduct = new AtomicBoolean(false);
        cart.getProductItems().forEach(productItem -> {
            if(Objects.equals(productItem.getProductId(), newProductITem.getProductId())){
                findExistingProduct.set(true);
                productItem.setQuantity(newProductITem.getQuantity());
            }
        });
        if(!findExistingProduct.get()){
            cart.getProductItems().add(newProductITem);
        }
        return cartRepository.save(cart);
    }

    @Override
    public Cart removeProductFromCart(String cartId, String productId) throws CartNotFoundException {
        cartExistCheck(cartId);
        Cart cart = cartRepository.getCartByIdAndDeleted(cartId,false);
        cart.setProductItems(cart.getProductItems()
                .stream()
                .filter(productItem -> !Objects.equals(productItem.getProductId(), productId))
                .toList());
        cartRepository.save(cart);
        return cart;
    }

    private void cartExistCheck(String cartId) throws CartNotFoundException{
        if (!cartRepository.existsByIdAndDeleted(cartId,false)){
            throw new CartNotFoundException("Cart not found");
        }
    }
}
