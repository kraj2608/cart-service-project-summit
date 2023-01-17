package com.foodshop.cartservice.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductItem {

    @JsonProperty("product_id")
    @NotEmpty(message = "product_id is required!")
    private String productId;

    @NotEmpty(message = "quantity is required")
    @NotNull(message = "product_id cannot be null")
    private int quantity;
}
