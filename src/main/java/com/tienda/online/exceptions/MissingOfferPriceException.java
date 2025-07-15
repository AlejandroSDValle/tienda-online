package com.tienda.online.exceptions;

public class MissingOfferPriceException extends RuntimeException{
    public MissingOfferPriceException(String message) {
        super(message);
    }
}
