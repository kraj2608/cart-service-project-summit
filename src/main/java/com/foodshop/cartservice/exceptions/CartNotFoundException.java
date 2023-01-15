package com.foodshop.cartservice.exceptions;

public class CartNotFoundException extends RuntimeException{

    private final String message;

    public CartNotFoundException(String message){
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
