package com.foodshop.cartservice.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ErrorResponseDTO {

    @JsonProperty("status_code")
    private int statusCode;
    private String message;


}
