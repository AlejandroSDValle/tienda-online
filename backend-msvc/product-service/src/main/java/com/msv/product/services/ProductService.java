package com.msv.product.services;

import com.libs.msvc.commons.dto.product.ProductResponse;
import com.msv.product.dto.product.ProductAdminResponse;
import com.msv.product.dto.product.ProductRequest;

import java.util.List;

public interface ProductService {

    /// ------------------------------------
    /// ----------  Methods GET  -----------
    /// ------------------------------------
    List<ProductResponse> getAllProducts();
//    List<ProductAdminResponse> getAllProductsToAdmin();
    ProductResponse getProductById(Long id);
//    ProductAdminResponse getProductByIdToAdmin(Long id);
    List<ProductResponse> getAvailableProducts();
//    List<ProductAdminResponse> findProductsWithLowStock();
    List<ProductResponse> searchByNameOrdered(String name);

    /// ------------------------------------
    /// ----------  Methods POST  ----------
    /// ------------------------------------

    ProductAdminResponse createProduct(ProductRequest product);

    ///------------------------------------
    ///---------  Methods UPDATE  ---------
    ///------------------------------------

//    ProductAdminResponse updateProduct(Long id, ProductRequest product);

//    ProductAdminResponse updateOfferStatus(Long idProduct, OfferStatusRequest product);

    ///------------------------------------
    ///---------  Methods DELETE  ---------
    ///------------------------------------

    void deleteProduct(Long id);
}
