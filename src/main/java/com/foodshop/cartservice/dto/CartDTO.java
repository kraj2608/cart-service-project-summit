package com.foodshop.cartservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.foodshop.cartservice.models.Cart;
import com.foodshop.cartservice.models.ProductItem;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CartDTO {


    @NotEmpty(message = "Owner email is required")
    @JsonProperty("owner_email")
    private String ownerEmail;

    @NotEmpty(message = "Cart name is required")
    @JsonProperty("cart_name")
    private String cartName;

    @JsonProperty("products")
    private List<ProductItem> productItems;

    public Cart toCart(){
        return Cart.builder()
                .cartName(cartName)
                .ownerEmail(ownerEmail)
                .productItems(new ArrayList<>())
                .build();
    }
}
