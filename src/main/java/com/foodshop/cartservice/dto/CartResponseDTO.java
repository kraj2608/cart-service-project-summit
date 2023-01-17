package com.foodshop.cartservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.foodshop.cartservice.models.Cart;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CartResponseDTO {
    private final Cart cart;
    private final String message;

    @JsonProperty("status_code")
    private final int statusCode;
}
