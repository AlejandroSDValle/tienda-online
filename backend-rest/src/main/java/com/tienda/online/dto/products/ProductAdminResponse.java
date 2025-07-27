package com.tienda.online.dto.products;

import com.tienda.online.dto.brands.BrandResponse;
import com.tienda.online.dto.category.CategoryResponse;
import com.tienda.online.dto.supplier.SupplierResponse;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ProductAdminResponse {

    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private BigDecimal purchasePrice;

    private boolean discount;

    private BigDecimal offerPrice;

    private int stock;

    private LocalDateTime createdAt;

    private BrandResponse brand;

    private List<CategoryResponse> categories;

    private List<SupplierResponse> suppliers;

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

    public List<SupplierResponse> getSuppliers() {
        return suppliers;
    }

    public void setSuppliers(List<SupplierResponse> suppliers) {
        this.suppliers = suppliers;
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

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
