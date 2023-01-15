package com.foodshop.cartservice.exceptions;

public class CartAuthorizationAccessDeniedException extends RuntimeException{
    private final String message;

    public CartAuthorizationAccessDeniedException(String message){
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
