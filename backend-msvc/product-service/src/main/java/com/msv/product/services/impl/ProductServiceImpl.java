package com.msv.product.services.impl;

import com.libs.msvc.commons.dto.product.ProductResponse;
import com.msv.product.dto.product.ProductAdminResponse;
import com.msv.product.dto.product.ProductRequest;
import com.msv.product.entities.Product;
import com.msv.product.exception.ObjectNotFoundException;
import com.msv.product.repository.ProductRepository;
import com.msv.product.services.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();

        List<ProductResponse> productsResponse = new ArrayList<>();

        products.forEach(product -> {
            ProductResponse productResponse = getProductResponse(product);
            productsResponse.add(productResponse);
        });
        return productsResponse;
    }

//    @Override
//    public List<ProductAdminResponse> getAllProductsToAdmin() {
//        return null;
//    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException("Product with id " + id + " does not exist"));
        return getProductResponse(product);
    }

//    @Override
//    public ProductAdminResponse getProductByIdToAdmin(Long id) {
//        return null;
//    }

    @Override
    public List<ProductResponse> getAvailableProducts() {
        return null;
    }

//    @Override
//    public List<ProductAdminResponse> findProductsWithLowStock() {
//        return null;
//    }

    @Override
    public List<ProductResponse> searchByNameOrdered(String name) {
        return null;
    }

    @Override
    @Transactional
    public ProductAdminResponse createProduct(ProductRequest request) {
        Product product = new Product();
        updateProductFromRequest(product, request);
        product.setStock(request.getStock());

        return getProductAdminResponse(productRepository.save(product));
    }

//    @Override
//    ProductAdminResponse updateProduct(Long id, ProductRequest product) {
//        return null;
//    }
//
//    @Override
//    public ProductAdminResponse updateOfferStatus(Long idProduct, OfferStatusRequest product) {
//        return null;
//    }

    @Override
    public void deleteProduct(Long id) {

    }

    public static ProductResponse getProductResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());
        productResponse.setDiscount(product.isDiscount());
        productResponse.setOfferPrice(product.getOfferPrice());
        productResponse.setStock(product.getStock());
        return productResponse;
    }

    public static ProductAdminResponse getProductAdminResponse(Product product) {
        ProductAdminResponse productResponse = new ProductAdminResponse();
        productResponse.setId(product.getId());
        productResponse.setName(product.getName());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());
        productResponse.setPurchasePrice(product.getPurchasePrice());
        productResponse.setDiscount(product.isDiscount());
        productResponse.setOfferPrice(product.getOfferPrice());
        productResponse.setStock(product.getStock());
        productResponse.setCreatedAt(product.getCreatedAt());
        return productResponse;
    }

    public void updateProductFromRequest(Product product, ProductRequest request) {
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setPurchasePrice(request.getPurchasePrice());
        product.setDiscount(request.isDiscount());
        product.setOfferPrice(request.getOfferPrice());
        product.setStock(request.getStock());
    }
}
