package com.msvc.order.client;

import com.libs.msvc.commons.dto.product.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "product-service")
public interface ProductFeignClient {

    @GetMapping("/products")
    List<ProductResponse> getAllProducts();

    @GetMapping("/products/error/{opt}")
    List<ProductResponse> getAllProductsWithError(@PathVariable Long opt);

}
