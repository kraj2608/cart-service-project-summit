package com.foodshop.cartservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.foodshop.cartservice.models.Cart;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CartListResponseDTO {
    private final List<Cart> carts;
    private final String message;

    @JsonProperty("status_code")
    private final int statusCode;
}
