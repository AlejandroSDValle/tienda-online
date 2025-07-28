package com.tienda.online.repository;


import com.tienda.online.entities.Brand;
import com.tienda.online.entities.Category;
import com.tienda.online.entities.Product;
import com.tienda.online.entities.Supplier;
import com.tienda.online.repositories.BrandRepository;
import com.tienda.online.repositories.CategoryRepository;
import com.tienda.online.repositories.ProductRepository;
import com.tienda.online.repositories.SupplierRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    private Brand brand;
    private Category category;
    private Supplier supplier;
    private Product product;


    @BeforeEach
    void setup(){
        brand = new Brand();
        brand.setName("Sprite");
        brand = brandRepository.save(brand);

        category = new Category();
        category.setName("Sodas");
        category = categoryRepository.save(category);

        supplier = new Supplier();
        supplier.setName("Proveedor Sur");
        supplier = supplierRepository.save(supplier);

        product = new Product();
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
    @DisplayName("Test to get all products")
    void testGetAllProducts(){
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
        productRepository.save(product);
        productRepository.save(productTest);

        List<Product> products = productRepository.findAll();

        assertThat(products).isNotEmpty();
        assertThat(products.size()).isGreaterThanOrEqualTo(2);
        assertThat(products.get(0).getName()).isEqualTo("Sprite 600ml");
    }

    @Test
    @DisplayName("Test to get product by id")
    void testGetProductById(){
        productRepository.save(product);

        Product productBD = productRepository.findById(product.getId()).get();

        assertThat(productBD).isNotNull();
    }

    @Test
    @DisplayName("Test to save a Product")
    void testSaveProduct(){
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

        Product saved = productRepository.save(productTest);

        assertThat(saved).isNotNull();
        assertThat(saved.getId()).isNotNull();
        assertThat(saved.getName()).isEqualTo("Coca Cola 300ml");

        Optional<Product> optional = productRepository.findById(saved.getId());
        assertThat(optional).isPresent();
    }

    @Test
    @DisplayName("Test to update one Product")
    void testUpdateProduct(){
        productRepository.save(product);

        Product saved =productRepository.findById(product.getId()).get();
        product.setName("Crema Lala");
        product.setDescription("Crema Lala se 485ml");
        product.setPrice(new BigDecimal("40.00"));
        product.setPurchasePrice(new BigDecimal("40.00"));
        product.setStock(20);
        product.setDiscount(false);

        Product productUpdated = productRepository.save(saved);

        assertThat(productUpdated.getName()).isEqualTo("Crema Lala");
        assertThat(productUpdated.getDescription()).isEqualTo("Crema Lala se 485ml");
    }

    @Test
    @DisplayName("Test to delete one Product")
    void testDeleteProduct(){
        productRepository.save(product);

        productRepository.deleteById(product.getId());
        Optional<Product> productOptional = productRepository.findById(product.getId());

        assertThat(productOptional).isEmpty();
    }
}
