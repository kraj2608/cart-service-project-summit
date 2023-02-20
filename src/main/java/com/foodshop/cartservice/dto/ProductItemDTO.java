package com.foodshop.cartservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.foodshop.cartservice.models.ProductItem;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductItemDTO {
    @JsonProperty("product_id")
    @NotEmpty(message = "product_id is required!")
    private String productId;

    @NotNull(message = "quantity is required!")
    @Min(value = 1, message = "quantity must be greater than or equal to 1!")
    private Integer quantity;

    @JsonProperty("unit_price")
    @Min(value = 0, message = "unit_price is required")
    @NotNull(message = "unit_price cannot be null")
    private int unitPrice;


    public ProductItem toProduct(){
        return ProductItem.builder()
                .productId(productId)
                .quantity(quantity)
                .unitPrice(unitPrice)
                .build();
    }
}
