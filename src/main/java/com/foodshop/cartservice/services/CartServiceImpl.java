package com.foodshop.cartservice.services;

import com.foodshop.cartservice.constants.ResponseMessages;
import com.foodshop.cartservice.dto.CartListResponseDTO;
import com.foodshop.cartservice.dto.CartResponseDTO;
import com.foodshop.cartservice.dto.UpdateCartNameDTO;
import com.foodshop.cartservice.exceptions.BadRequestException;
import com.foodshop.cartservice.exceptions.CartNotFoundException;
import com.foodshop.cartservice.models.Cart;
import com.foodshop.cartservice.models.ProductItem;
import com.foodshop.cartservice.repositories.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

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
    public CartResponseDTO updateCartName(UpdateCartNameDTO cartNameDTO, String id) throws CartNotFoundException {
        if (!cartRepository.existsById(id)){
            throw new CartNotFoundException("Cart not found");
        }
        Cart cart = cartRepository.getCartById(id);
        cart.setCartName(cartNameDTO.getCartName());
        return CartResponseDTO
                .builder()
                .cart(cartRepository.save(cart))
                .message(ResponseMessages.CART_UPDATED_SUCCESS)
                .statusCode(HttpStatus.CREATED.value())
                .build();

    }

    @Override
    public CartResponseDTO getCart(String cartId) throws CartNotFoundException {
        cartExistCheck(cartId);
        return CartResponseDTO
                .builder()
                .cart(cartRepository.getCartById(cartId))
                .message(ResponseMessages.CART_FETCHED_SUCCESS)
                .statusCode(HttpStatus.OK.value())
                .build();
    }

    @Override
    public CartListResponseDTO getAllCartsOfAUser(String email) throws BadRequestException {

            return CartListResponseDTO
                    .builder()
                    .carts(cartRepository.getAllByOwnerEmail(email))
                    .message(ResponseMessages.CARTS_FETCHED_SUCCESS)
                    .statusCode(HttpStatus.OK.value())
                    .build();


    }

    @Override
    public CartResponseDTO addProductToCart(String cartId, ProductItem newProductITem) throws CartNotFoundException {
        cartExistCheck(cartId);
        Cart cart = cartRepository.getCartById(cartId);
        AtomicBoolean findExistingProduct = new AtomicBoolean(false);
        cart.getProductItems().forEach(productItem -> {
            if(Objects.equals(productItem.getProductId(), newProductITem.getProductId())){
                findExistingProduct.set(true);
                productItem.setQuantity(newProductITem.getQuantity() + productItem.getQuantity());
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
        Cart cart = cartRepository.getCartById(cartId);
        cart.getProductItems().forEach(productItem -> {
            if (Objects.equals(productItem.getProductId(), productId)){
                if (productItem.getQuantity() - 1 == 0){
                    cart.setProductItems(cart.getProductItems().stream().filter(productItem1 ->
                            !Objects.equals(productItem1.getProductId(), productItem.getProductId())
                    ).toList());

                }else{
                    productItem.setQuantity(productItem.getQuantity() - 1);
                }
            }

        });
        return CartResponseDTO
                .builder()
                .cart(cartRepository.save(cart))
                .message(ResponseMessages.CART_PRODUCT_REMOVE_SUCCESS)
                .statusCode(HttpStatus.CREATED.value())
                .build();
    }

    @Override
    public CartResponseDTO removeCart(String cartId) throws CartNotFoundException {
        cartExistCheck(cartId);
        Cart cart = cartRepository.getCartById(cartId);
        cartRepository.delete(cart);
        return CartResponseDTO
                .builder()
                .cart(cart)
                .message(ResponseMessages.CART_REMOVED_SUCCESS)
                .statusCode(HttpStatus.CREATED.value())
                .build();
    }

    private void cartExistCheck(String cartId) throws CartNotFoundException{
        if (!cartRepository.existsById(cartId)){
            throw new CartNotFoundException("Cart not found");
        }
    }
}
