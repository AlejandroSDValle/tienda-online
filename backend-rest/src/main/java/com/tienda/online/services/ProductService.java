package com.tienda.online.services;

import com.tienda.online.dto.products.OfferStatusRequest;
import com.tienda.online.dto.products.ProductAdminResponse;
import com.tienda.online.dto.products.ProductRequest;
import com.tienda.online.dto.products.ProductResponse;

import java.util.List;

public interface ProductService {

    /// ------------------------------------
    /// ----------  Methods GET  -----------
    /// ------------------------------------
    List<ProductResponse> getAllProducts();
    List<ProductAdminResponse> getAllProductsToAdmin();
    ProductResponse getProductById(Long id);
    ProductAdminResponse getProductByIdToAdmin(Long id);
    List<ProductResponse> getAvailableProducts();
    List<ProductAdminResponse> findProductsWithLowStock();
    List<ProductResponse> searchByNameOrdered(String name);

    /// ------------------------------------
    /// ----------  Methods POST  ----------
    /// ------------------------------------

    ProductAdminResponse createProduct(ProductRequest product);

    ///------------------------------------
    ///---------  Methods UPDATE  ---------
    ///------------------------------------

    ProductAdminResponse updateProduct(Long id, ProductRequest product);

    ProductAdminResponse updateOfferStatus(Long idProduct, OfferStatusRequest product);

    ///------------------------------------
    ///---------  Methods DELETE  ---------
    ///------------------------------------

    void deleteProduct(Long id);
}
