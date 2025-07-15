package com.tienda.online.dto.products;

import java.math.BigDecimal;

public class OfferStatusRequest {

    private BigDecimal offerPrice;

    public BigDecimal getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(BigDecimal offerPrice) {
        this.offerPrice = offerPrice;
    }
}
