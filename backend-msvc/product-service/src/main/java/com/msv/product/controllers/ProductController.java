package com.msv.product.controllers;

import com.libs.msvc.commons.dto.product.ProductResponse;
import com.msv.product.dto.product.ProductAdminResponse;
import com.msv.product.dto.product.ProductRequest;
import com.msv.product.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;


    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

    @GetMapping("/error/{opt}")
    public ResponseEntity<List<ProductResponse>> getAllProductsWithError(@PathVariable Long opt) throws InterruptedException {
        if(opt.equals(10L)){
            throw new IllegalStateException("Simulacion de error");
        }
        if(opt.equals(7L)){
            TimeUnit.SECONDS.sleep(3L);
        }
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }

//    @GetMapping("/admin")
//    public ResponseEntity<List<ProductAdminResponse>> getAllProductsToAdmin() {
//        return new ResponseEntity<>(productService.getAllProductsToAdmin(), HttpStatus.OK);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable Long id){
        return new ResponseEntity<>(productService.getProductById(id),  HttpStatus.OK);
    }

//    @GetMapping("/{id}/admin")
//    public ResponseEntity<ProductAdminResponse> getProductByIdToAdmin(@PathVariable Long id){
//        return new ResponseEntity<>(productService.getProductByIdToAdmin(id),  HttpStatus.OK);
//    }

    @GetMapping("/availability")
    public ResponseEntity<List<ProductResponse>> getAvailableProducts(){
        return new ResponseEntity<>(productService.getAvailableProducts(),  HttpStatus.OK);
    }

//    @GetMapping("/low-stock")
//    public ResponseEntity<List<ProductAdminResponse>> findProductsWithLowStock(){
//        return new ResponseEntity<>(productService.findProductsWithLowStock(),  HttpStatus.OK);
//    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductResponse>> searchProducts(@RequestParam String name) {
        return ResponseEntity.ok(productService.searchByNameOrdered(name));
    }

    @PostMapping
    public ResponseEntity<ProductAdminResponse> createProduct(@RequestBody ProductRequest product){
        ProductAdminResponse response = productService.createProduct(product);
        return new ResponseEntity<>(response,  HttpStatus.CREATED);
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<ProductAdminResponse> updateProduct(@PathVariable Long id, @RequestBody ProductRequest product){
//        return new ResponseEntity<>(productService.updateProduct(id, product),  HttpStatus.OK);
//    }

//    @PatchMapping("/{idProduct}/toggle-offer")
//    public ResponseEntity<ProductAdminResponse> updateOfferStatus(
//            @PathVariable Long idProduct, @RequestBody(required = false) OfferStatusRequest product){
//        return new ResponseEntity<>(productService.updateOfferStatus(idProduct, product), HttpStatus.OK);
//    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        productService.deleteProduct(id);
        return new ResponseEntity<>("Product removed", HttpStatus.OK);
    }

}
