package com.tienda.online.service;

import com.tienda.online.dto.products.ProductAdminResponse;
import com.tienda.online.dto.products.ProductRequest;
import com.tienda.online.dto.products.ProductResponse;
import com.tienda.online.entities.Brand;
import com.tienda.online.entities.Category;
import com.tienda.online.entities.Product;
import com.tienda.online.entities.Supplier;
import com.tienda.online.repositories.BrandRepository;
import com.tienda.online.repositories.CategoryRepository;
import com.tienda.online.repositories.ProductRepository;
import com.tienda.online.repositories.SupplierRepository;
import com.tienda.online.services.impl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private BrandRepository brandRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private SupplierRepository supplierRepository;

    private Brand brand;
    private Category category;
    private Supplier supplier;
    private Product product;

    @BeforeEach
    void setup(){
        brand = new Brand();
        brand.setId(1L);
        brand.setName("Coca Cola");

        category = new Category();
        category.setId(2L);
        category.setName("Sodas");

        supplier = new Supplier();
        supplier.setId(3L);
        supplier.setName("Proveedor Sur");

        product = new Product();
        product.setId(10L);
        product.setName("Sprite 600ml");
        product.setDescription("Refresco sprite 600ml");
        product.setPrice(new BigDecimal("13.00"));
        product.setPurchasePrice(new BigDecimal("8.00"));
        product.setStock(150);
        product.setDiscount(false);
        product.setBrand(brand);
        product.getCategories().add(category);
        product.getSuppliers().add(supplier);
    }

    @Test
    @DisplayName("Test to get products")
    void testGetAllProducts() {
        Product productTest = new Product();
        productTest.setName("Coca Cola 300ml");
        productTest.setDescription("Refresco coca cola sin azucar de lata");
        productTest.setPrice(new BigDecimal("15.00"));
        productTest.setPurchasePrice(new BigDecimal("10.00"));
        productTest.setDiscount(true);
        productTest.setOfferPrice(new BigDecimal("13.00"));
        productTest.setStock(100);
        productTest.setBrand(brand);
        productTest.getCategories().add(category);
        productTest.getSuppliers().add(supplier);

        given(productRepository.findAll()).willReturn(List.of(product, productTest));

        List<ProductResponse> responses = productService.getAllProducts();

        assertThat(responses).isNotEmpty();
        assertThat(responses.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("Test to get product by id")
    void testGetProductById() {
        given(productRepository.findById(10L)).willReturn(Optional.of(product));

        ProductResponse response = productService.getProductById(10L);

        assertThat(response).isNotNull();
    }


    @Test
    @DisplayName("Test to save a product")
    void testSaveProduct() {

        ProductRequest request = new ProductRequest();
        request.setName("Sprite 600ml");
        request.setDescription("Refresco sprite 600ml");
        request.setPrice(new BigDecimal("13.00"));
        request.setPurchasePrice(new BigDecimal("8.00"));
        request.setStock(150);
        request.setDiscount(false);
        request.setBrandId(1L);
        request.setCategoryIds(Set.of(2L));
        request.setSupplierIds(Set.of(3L));

        given(brandRepository.findById(1L)).willReturn(Optional.of(brand));
        given(categoryRepository.findAllById(Set.of(2L))).willReturn(List.of(category));
        given(supplierRepository.findAllById(Set.of(3L))).willReturn(List.of(supplier));

        given(productRepository.save(any(Product.class))).willReturn(product);

        ProductAdminResponse response = productService.createProduct(request);

        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(10L);
        assertThat(response.getName()).isEqualTo("Sprite 600ml");

        verify(brandRepository).findById(1L);
        verify(categoryRepository).findAllById(Set.of(2L));
        verify(supplierRepository).findAllById(Set.of(3L));
        verify(productRepository).save(any(Product.class));
    }

    @Test
    @DisplayName("Test to update a product")
    void testUpdateProduct() {
        given(productRepository.findById(10L)).willReturn(Optional.of(product));
        given(brandRepository.findById(1L)).willReturn(Optional.of(brand));
        given(categoryRepository.findAllById(Set.of(2L))).willReturn(List.of(category));
        given(supplierRepository.findAllById(Set.of(3L))).willReturn(List.of(supplier));

        ProductRequest request = new ProductRequest();
        request.setName("Pepsi 600ml");
        request.setDescription("Refresco pepsi enbotellado de 600ml");
        request.setPrice(new BigDecimal("16.00"));
        request.setPurchasePrice(new BigDecimal("10.00"));
        request.setStock(15);
        request.setDiscount(true);
        request.setBrandId(1L);
        request.setCategoryIds(Set.of(2L));
        request.setSupplierIds(Set.of(3L));

        ProductAdminResponse response = productService.updateProduct(10L, request);

        assertThat(response.getName()).isEqualTo("Pepsi 600ml");
        assertThat(response.getDescription()).isEqualTo("Refresco pepsi enbotellado de 600ml");
    }

    @Test
    @DisplayName("Test to delete a product")
    void testDeleteProduct() {
        given(productRepository.findById(10L)).willReturn(Optional.of(product));

        productService.deleteProduct(10L);

        verify(productRepository, times(1)).delete(product);
    }
}
