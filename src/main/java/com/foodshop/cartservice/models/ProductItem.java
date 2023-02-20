package com.foodshop.cartservice.models;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductItem {

    @JsonProperty("product_id")
    private String productId;

    private int quantity;


    @JsonProperty("unit_price")
    private int unitPrice;
}
