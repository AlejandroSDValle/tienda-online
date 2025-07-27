package com.tienda.online.dto.category;

import java.io.Serializable;

public class CategoryRequest implements Serializable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
