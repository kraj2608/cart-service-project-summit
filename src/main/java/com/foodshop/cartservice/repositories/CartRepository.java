package com.foodshop.cartservice.repositories;

import com.foodshop.cartservice.models.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CartRepository extends MongoRepository<Cart,String> {
    Cart getCartByIdAndDeleted(String id, boolean deleted);
    boolean existsByIdAndDeleted(String id, boolean deleted);

    List<Cart> getCartsByOwnerIdAndPurchasedAndDeleted(String ownerId, boolean purchased, boolean deleted);

}
