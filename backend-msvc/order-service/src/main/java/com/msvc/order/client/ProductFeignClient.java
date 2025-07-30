package com.msvc.order.client;

import com.msvc.order.dto.product.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "product-service", url = "http://localhost:8001/api/v1/products")
public interface ProductFeignClient {

    @GetMapping
    List<ProductResponse> getAllProducts();

}
