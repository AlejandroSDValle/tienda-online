package com.tienda.online.services;

import com.tienda.online.dto.brands.BrandRequest;
import com.tienda.online.dto.brands.BrandResponse;
import com.tienda.online.dto.products.ProductResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BrandService {

    List<BrandResponse> getAllBrands();

    BrandResponse getBrandById(Long id);

    List<ProductResponse> getProductsByBrand(Long id);

    void createBrand(BrandRequest brandRequest);

    BrandResponse updateBrand(Long id, BrandRequest brandRequest);

    void deleteBrand(Long id);
}
