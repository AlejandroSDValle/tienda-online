package com.tienda.online.dto.products;

import com.tienda.online.dto.brands.BrandResponse;
import com.tienda.online.dto.category.CategoryResponse;

import java.math.BigDecimal;
import java.util.List;

public class ProductResponse {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private boolean discount;

    private BigDecimal offerPrice;

    private int stock;

    private BrandResponse brand;

    private List<CategoryResponse> categories;

    public ProductResponse() {
    }

    public ProductResponse(Long id, String name, String description, BigDecimal price, boolean discount, BigDecimal offerPrice, int stock, BrandResponse brand, List<CategoryResponse> categories) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.discount = discount;
        this.offerPrice = offerPrice;
        this.stock = stock;
        this.brand = brand;
        this.categories = categories;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BrandResponse getBrand() {
        return brand;
    }

    public void setBrand(BrandResponse brand) {
        this.brand = brand;
    }

    public List<CategoryResponse> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryResponse> categories) {
        this.categories = categories;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isDiscount() {
        return discount;
    }

    public void setDiscount(boolean discount) {
        this.discount = discount;
    }

    public BigDecimal getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(BigDecimal offerPrice) {
        this.offerPrice = offerPrice;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

}
