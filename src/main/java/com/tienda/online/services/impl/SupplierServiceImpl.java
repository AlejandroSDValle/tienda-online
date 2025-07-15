package com.tienda.online.services.impl;

import com.tienda.online.dto.products.ProductResponse;
import com.tienda.online.dto.supplier.SupplierRequest;
import com.tienda.online.dto.supplier.SupplierResponse;
import com.tienda.online.entities.Product;
import com.tienda.online.entities.Supplier;
import com.tienda.online.exceptions.ObjectNotFoundException;
import com.tienda.online.repositories.SupplierRepository;
import com.tienda.online.services.SupplierService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;

    public SupplierServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<SupplierResponse> getAllSupplier() {
        List<Supplier> suppliers = supplierRepository.findAll();
        List<SupplierResponse> supplierResponses = new ArrayList<>();
        suppliers.forEach(supplier -> {
            SupplierResponse supplierResponse = getSupplierResponse(supplier);
            supplierResponses.add(supplierResponse);
        });
        return supplierResponses;
    }

    @Override
    @Transactional(readOnly = true)
    public SupplierResponse getSupplierById(Long id) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException("Supplier with ID " + id + " does not exist"));
        return getSupplierResponse(supplier);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getProductsBySupplier(Long id) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException("Supplier with ID " + id + " does not exist"));

        List<ProductResponse> productResponses = new ArrayList<>();
        supplier.getProducts().forEach(product -> {
            productResponses.add(ProductServiceImpl.getProductResponse(product));
        });
        return productResponses;
    }

    @Override
    @Transactional
    public void createSupplier(SupplierRequest supplierRequest) {
        Supplier supplier = new Supplier();
        supplier.setName(supplierRequest.getName());
        supplier.setPhone(supplierRequest.getPhone());
        supplier.setEmail(supplierRequest.getEmail());
        supplierRepository.save(supplier);
    }

    @Override
    @Transactional
    public SupplierResponse updateSupplier(Long id, SupplierRequest supplierRequest) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException("Supplier with ID " + id + " does not exist"));
        supplier.setName(supplierRequest.getName());
        supplier.setPhone(supplierRequest.getPhone());
        supplier.setEmail(supplierRequest.getEmail());
        supplierRepository.save(supplier);
        return getSupplierResponse(supplier);
    }

    @Override
    @Transactional
    public void deleteSupplier(Long id) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException("Supplier with ID " + id + " does not exist"));

        // Eliminar relación en los productos
        for (Product product : supplier.getProducts()) {
            product.getSuppliers().remove(supplier);
        }

        supplierRepository.delete(supplier);
    }

    public static SupplierResponse getSupplierResponse(Supplier supplier) {
        SupplierResponse supplierResponse = new SupplierResponse();
        supplierResponse.setId(supplier.getId());
        supplierResponse.setName(supplier.getName());
        supplierResponse.setPhone(supplier.getPhone());
        supplierResponse.setEmail(supplier.getEmail());
        return supplierResponse;
    }
}
