package com.tienda.online.services.impl;

import com.tienda.online.dto.brands.BrandRequest;
import com.tienda.online.dto.brands.BrandResponse;
import com.tienda.online.dto.products.ProductResponse;
import com.tienda.online.entities.Brand;
import com.tienda.online.exceptions.ObjectNotFoundException;
import com.tienda.online.repositories.BrandRepository;
import com.tienda.online.services.BrandService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    public BrandServiceImpl(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<BrandResponse> getAllBrands() {
        List<Brand> brands = brandRepository.findAll();
        List<BrandResponse> brandResponses = new ArrayList<>();
        brands.forEach(brand -> {
            BrandResponse brandResponse = getBrandResponse(brand);
            brandResponses.add(brandResponse);
        });

        return brandResponses;
    }

    @Override
    @Transactional(readOnly = true)
    public BrandResponse getBrandById(Long id) {
        Brand brand = brandRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException("Brand with ID " + id + " does not exist"));
        return getBrandResponse(brand);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getProductsByBrand(Long id) {
        Brand brand = brandRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException("Brand with ID " + id + " does not exist"));
        List<ProductResponse> productResponses = new ArrayList<>();
        brand.getProducts().forEach(product -> {
            productResponses.add(ProductServiceImpl.getProductResponse(product));
        });
        return productResponses;
    }

    @Override
    @Transactional
    public void createBrand(BrandRequest brandRequest) {
        Brand brand = new Brand();
        brand.setName(brandRequest.getName());
        brandRepository.save(brand);
    }

    @Override
    @Transactional
    public BrandResponse updateBrand(Long id, BrandRequest brandRequest) {
        Brand brand = brandRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException("Brand with ID " + id + " does not exist"));
        brand.setName(brandRequest.getName());

        brandRepository.save(brand);

        return getBrandResponse(brand);
    }

    @Override
    @Transactional
    public void deleteBrand(Long id) {
        Brand brand = brandRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException("Brand with ID " + id + " does not exist"));
        brandRepository.delete(brand);
    }

    private static BrandResponse getBrandResponse(Brand brand) {
        BrandResponse brandResponse = new BrandResponse();
        brandResponse.setId(brand.getId());
        brandResponse.setName(brand.getName());
        return brandResponse;
    }
}
