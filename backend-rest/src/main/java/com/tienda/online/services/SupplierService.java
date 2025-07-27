package com.tienda.online.services;

import com.tienda.online.dto.products.ProductResponse;
import com.tienda.online.dto.supplier.SupplierRequest;
import com.tienda.online.dto.supplier.SupplierResponse;

import java.util.List;

public interface SupplierService {

    List<SupplierResponse> getAllSupplier();

    SupplierResponse getSupplierById(Long id);

    List<ProductResponse> getProductsBySupplier(Long id);

    void createSupplier(SupplierRequest supplierRequest);

    SupplierResponse updateSupplier(Long id, SupplierRequest supplierRequest);

    void deleteSupplier(Long id);
}
