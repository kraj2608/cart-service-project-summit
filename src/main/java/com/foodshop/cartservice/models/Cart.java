package com.foodshop.cartservice.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document("carts")
@Builder
@Data
public class Cart {

    @Id
    private String id;

    @JsonProperty("owner_id")
    private String ownerId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean purchased;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private boolean deleted;

    @CreatedDate
    @JsonProperty("created_at")
    private Date createdAt;

    @LastModifiedDate
    @JsonProperty("modified_at")
    private Date modifiedAt;

    @JsonProperty("cart_name")
    private String cartName;

    @JsonProperty("product_items")
    private List<ProductItem> productItems;

}
