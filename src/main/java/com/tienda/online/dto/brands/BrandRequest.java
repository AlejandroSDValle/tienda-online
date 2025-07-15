package com.tienda.online.dto.brands;

import java.io.Serializable;

public class BrandRequest implements Serializable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
