package com.foodshop.cartservice.services;

import com.foodshop.cartservice.constants.ResponseMessages;
import com.foodshop.cartservice.dto.CartListResponseDTO;
import com.foodshop.cartservice.dto.CartResponseDTO;
import com.foodshop.cartservice.dto.UpdateCartNameDTO;
import com.foodshop.cartservice.exceptions.BadRequestException;
import com.foodshop.cartservice.exceptions.CartAuthorizationAccessDeniedException;
import com.foodshop.cartservice.exceptions.CartNotFoundException;
import com.foodshop.cartservice.models.Cart;
import com.foodshop.cartservice.models.ProductItem;
import com.foodshop.cartservice.repositories.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements ICartService{

    private final CartRepository cartRepository;
    @Override
    public CartResponseDTO addCart(Cart cart) {
        return CartResponseDTO
                .builder()
                .cart(cartRepository.save(cart))
                .message(ResponseMessages.CART_CREATED_SUCCESS)
                .statusCode(HttpStatus.CREATED.value())
                .build();
    }

    @Override
    public CartResponseDTO updateCartName(UpdateCartNameDTO cartNameDTO, String id) throws CartNotFoundException,CartAuthorizationAccessDeniedException {
        if (!cartRepository.existsByIdAndDeleted(id,false)){
            throw new CartNotFoundException("Cart not found");
        }
        Cart cart = cartRepository.getCartByIdAndDeleted(id,false);
        if(!Objects.equals(cart.getOwnerId(), cartNameDTO.getOwnerId())){
            throw new CartAuthorizationAccessDeniedException("Access denied for this cart");
        }
        cart.setCartName(cartNameDTO.getCartName());
        return CartResponseDTO
                .builder()
                .cart(cartRepository.save(cart))
                .message(ResponseMessages.CART_UPDATED_SUCCESS)
                .statusCode(HttpStatus.CREATED.value())
                .build();

    }

    @Override
    public CartResponseDTO getCart(String cartId, String userId) throws CartNotFoundException, CartAuthorizationAccessDeniedException {
        cartExistCheck(cartId);
        Cart cart = cartRepository.getCartByIdAndDeleted(cartId,false);
        if (!Objects.equals(cart.getOwnerId(), userId)){
            throw new CartAuthorizationAccessDeniedException("Access denied");
        }
        return CartResponseDTO
                .builder()
                .cart(cartRepository.getCartByIdAndDeleted(cartId,false))
                .message(ResponseMessages.CART_FETCHED_SUCCESS)
                .statusCode(HttpStatus.OK.value())
                .build();
    }

    @Override
    public CartListResponseDTO getAllCartsOfAUser(String userId, String type) throws BadRequestException {
        if (type == null){
            return CartListResponseDTO
                    .builder()
                    .carts(Stream.concat(cartRepository.getCartsByOwnerIdAndPurchasedAndDeleted(userId,true,false).stream(),
                            cartRepository.getCartsByOwnerIdAndPurchasedAndDeleted(userId,false,false).stream()).toList())
                    .message(ResponseMessages.CARTS_FETCHED_SUCCESS)
                    .statusCode(HttpStatus.OK.value())
                    .build();
        }else if(Objects.equals(type, "PURCHASED")){
            return CartListResponseDTO
                    .builder()
                    .carts(cartRepository.getCartsByOwnerIdAndPurchasedAndDeleted(userId,true,false))
                    .message(ResponseMessages.CARTS_FETCHED_SUCCESS)
                    .statusCode(HttpStatus.OK.value())
                    .build();

        }else if (Objects.equals(type, "PENDING")){
            return CartListResponseDTO
                    .builder()
                    .carts(cartRepository.getCartsByOwnerIdAndPurchasedAndDeleted(userId,false,false))
                    .message(ResponseMessages.CARTS_FETCHED_SUCCESS)
                    .statusCode(HttpStatus.OK.value())
                    .build();
        }else{
            throw new BadRequestException("Invalid cart type");
        }

    }

    @Override
    public CartResponseDTO addProductToCart(String cartId, ProductItem newProductITem) throws CartNotFoundException {
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
        return CartResponseDTO
                .builder()
                .cart(cartRepository.save(cart))
                .message(ResponseMessages.CART_PRODUCT_ADD_SUCCESS)
                .statusCode(HttpStatus.CREATED.value())
                .build();
    }

    @Override
    public CartResponseDTO removeProductFromCart(String cartId, String productId) throws CartNotFoundException {
        cartExistCheck(cartId);
        Cart cart = cartRepository.getCartByIdAndDeleted(cartId,false);
        cart.setProductItems(cart.getProductItems()
                .stream()
                .filter(productItem -> !Objects.equals(productItem.getProductId(), productId))
                .toList());
        return CartResponseDTO
                .builder()
                .cart(cartRepository.save(cart))
                .message(ResponseMessages.CART_PRODUCT_REMOVE_SUCCESS)
                .statusCode(HttpStatus.CREATED.value())
                .build();
    }

    @Override
    public CartResponseDTO removeCart(String cartId,String userID) throws CartNotFoundException {
        cartExistCheck(cartId);
        Cart cart = cartRepository.getCartByIdAndDeleted(cartId,false);
        if(!Objects.equals(cart.getOwnerId(), userID)){
            throw new CartNotFoundException("Access Denied");
        }
        cart.setDeleted(true);
        cartRepository.save(cart);
        return CartResponseDTO
                .builder()
                .cart(cartRepository.save(cart))
                .message(ResponseMessages.CART_REMOVED_SUCCESS)
                .statusCode(HttpStatus.CREATED.value())
                .build();
    }

    private void cartExistCheck(String cartId) throws CartNotFoundException{
        if (!cartRepository.existsByIdAndDeleted(cartId,false)){
            throw new CartNotFoundException("Cart not found");
        }
    }
}
