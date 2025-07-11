package com.rapidshine.carwash.review_service.exceptions;

public class ReviewNotFoundException extends RuntimeException{
    public ReviewNotFoundException(String msg){
        super(msg);
    }
}
