package com.tienda.online.controllers;

import com.tienda.online.dto.products.ProductResponse;
import com.tienda.online.dto.supplier.SupplierRequest;
import com.tienda.online.dto.supplier.SupplierResponse;
import com.tienda.online.services.SupplierService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suppliers")
public class SupplierController {

    private final SupplierService supplierService;

    public SupplierController(SupplierService supplierService) {
        this.supplierService = supplierService;
    }

    @GetMapping
    public ResponseEntity<List<SupplierResponse>> getAllSupplier(){
        return new ResponseEntity<>(supplierService.getAllSupplier(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierResponse> getSupplierById(@PathVariable Long id){
        return new ResponseEntity<>(supplierService.getSupplierById(id), HttpStatus.OK);
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<List<ProductResponse>> getProductsBySupplier(@PathVariable Long id){
        return new ResponseEntity<>(supplierService.getProductsBySupplier(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<String> createSupplier(@RequestBody SupplierRequest supplierRequest){
        supplierService.createSupplier(supplierRequest);
        return new ResponseEntity<>("Supplier created", HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierResponse> updateSupplier(@PathVariable Long id, @RequestBody SupplierRequest supplierRequest){
        return new ResponseEntity<>(supplierService.updateSupplier(id, supplierRequest), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSupplier(@PathVariable Long id){
        supplierService.deleteSupplier(id);
        return new ResponseEntity<>("Supplier removed", HttpStatus.OK);
    }
}
