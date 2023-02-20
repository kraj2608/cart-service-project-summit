package com.foodshop.cartservice.repositories;

import com.foodshop.cartservice.models.Cart;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CartRepository extends MongoRepository<Cart,String> {
    Cart getCartById(String id);
    boolean existsById(String id);


    List<Cart> getAllByOwnerEmail(String ownerEmail);

}
