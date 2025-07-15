package com.tienda.online.controllers;


import com.tienda.online.dto.brands.BrandRequest;
import com.tienda.online.dto.brands.BrandResponse;
import com.tienda.online.dto.products.ProductResponse;
import com.tienda.online.services.BrandService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/brands")
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @GetMapping
    public ResponseEntity<List<BrandResponse>> getAllBrands(){
        return new ResponseEntity<>(brandService.getAllBrands(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BrandResponse> getBrandById(@PathVariable Long id){
        return new ResponseEntity<>(brandService.getBrandById(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<List<ProductResponse>> getProductsByBrand(@PathVariable Long id){
        return new ResponseEntity<>(brandService.getProductsByBrand(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createBrand(@RequestBody BrandRequest brand){
        brandService.createBrand(brand);
        return new ResponseEntity<>("Brand created", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BrandResponse> updateBrand(@PathVariable Long id, @RequestBody BrandRequest brand){
        return new ResponseEntity<>(brandService.updateBrand(id, brand), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBrand(@PathVariable Long id){
        brandService.deleteBrand(id);
        return new ResponseEntity<>("Brand removed", HttpStatus.OK);
    }
}
