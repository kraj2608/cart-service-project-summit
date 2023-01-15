package com.foodshop.cartservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class UpdateCartNameDTO {

    @NotEmpty(message = "Cart name is required")
    @JsonProperty("cart_name")
    private String cartName;

    @NotEmpty(message = "Owner id is required")
    @JsonProperty("owner_id")
    private String ownerId;
}
