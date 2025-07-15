package com.tienda.online.services.impl;

import com.tienda.online.dto.brands.BrandResponse;
import com.tienda.online.dto.category.CategoryResponse;
import com.tienda.online.dto.products.OfferStatusRequest;
import com.tienda.online.dto.products.ProductAdminResponse;
import com.tienda.online.dto.products.ProductRequest;
import com.tienda.online.dto.products.ProductResponse;
import com.tienda.online.dto.supplier.SupplierResponse;
import com.tienda.online.entities.Brand;
import com.tienda.online.entities.Category;
import com.tienda.online.entities.Product;
import com.tienda.online.entities.Supplier;
import com.tienda.online.exceptions.MissingOfferPriceException;
import com.tienda.online.exceptions.ObjectNotFoundException;
import com.tienda.online.repositories.BrandRepository;
import com.tienda.online.repositories.CategoryRepository;
import com.tienda.online.repositories.ProductRepository;
import com.tienda.online.repositories.SupplierRepository;
import com.tienda.online.services.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final BrandRepository brandRepository;

    private final CategoryRepository categoryRepository;

    private final SupplierRepository supplierRepository;

    public ProductServiceImpl(ProductRepository productRepository, BrandRepository brandRepository, CategoryRepository categoryRepository,
                              SupplierRepository supplierRepository) {
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
        this.categoryRepository = categoryRepository;
        this.supplierRepository = supplierRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();

        List<ProductResponse> productsResponse = new ArrayList<>();

        products.forEach(product -> {
            ProductResponse productResponse = getProductResponse(product);
            productsResponse.add(productResponse);
        });
        return productsResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductAdminResponse> getAllProductsToAdmin() {
        List<Product> products = productRepository.findAll();

        List<ProductAdminResponse> productsResponse = new ArrayList<>();

        products.forEach(product -> {
            ProductAdminResponse productResponse = getProductAdminResponse(product);
            productsResponse.add(productResponse);
        });
        return productsResponse;
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException("Product with id " + id + " does not exist"));
        return getProductResponse(product);
    }

    @Override
    @Transactional(readOnly = true)
    public ProductAdminResponse getProductByIdToAdmin(Long id) {
        Product product = productRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException("Product with id " + id + " does not exist"));
        return getProductAdminResponse(product);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getAvailableProducts() {
        List<Product> productsAvailable = productRepository.getAvailableProducts();
        List<ProductResponse> products = new ArrayList<>();
        productsAvailable.forEach(product -> {
            products.add(getProductResponse(product));
        });
        return products;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductAdminResponse> findProductsWithLowStock() {
        List<Product> productsLowSotck = productRepository.findProductsWithLowStock();
        List<ProductAdminResponse> products = new ArrayList<>();
        productsLowSotck.forEach(product -> {
            products.add(getProductAdminResponse(product));
        });
        return products;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> searchByNameOrdered(String name) {
        List<Product> productsFound = productRepository.searchByNameOrdered(name);
        List<ProductResponse> products = new ArrayList<>();
        productsFound.forEach(product -> {
            products.add(getProductResponse(product));
        });
        return products;
    }

    @Override
    @Transactional
    public void createProduct(ProductRequest request) {
        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new ObjectNotFoundException("Brand with ID " + request.getBrandId() + " does not exist"));

        List<Category> categories = categoryRepository.findAllById(request.getCategoryIds());

        List<Supplier> suppliers = supplierRepository.findAllById(request.getSupplierIds());

        Product product = new Product();
        updateProductFromRequest(product, request, brand, categories, suppliers);
        product.setStock(request.getStock());

        productRepository.save(product);
    }

    @Override
    public ProductAdminResponse updateProduct(Long id, ProductRequest request) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Product with id " + id + " does not exist"));
        Brand brand = brandRepository.findById(request.getBrandId())
                .orElseThrow(() -> new ObjectNotFoundException("Brand with ID " + request.getBrandId() + " does not exist"));

        List<Category> categories = categoryRepository.findAllById(request.getCategoryIds());

        List<Supplier> suppliers = supplierRepository.findAllById(request.getSupplierIds());

        updateProductFromRequest(product, request, brand, categories, suppliers);
        productRepository.save(product);
        return getProductAdminResponse(product);
    }

    @Override
    @Transactional
    public ProductAdminResponse updateOfferStatus(Long idProduct, OfferStatusRequest request) {
        Product productDb = productRepository.findById(idProduct)
                .orElseThrow(()-> new ObjectNotFoundException("Product with id " + idProduct + " does not exist"));

        if(productDb.isDiscount()){
            productDb.setDiscount(false);
            productDb.setOfferPrice(productDb.getPrice());
        }else{
            if (request == null) {
                throw new MissingOfferPriceException("Offer price is required when activating the offer.");
            }
            productDb.setDiscount(true);
            productDb.setOfferPrice(request.getOfferPrice());
        }
        productRepository.save(productDb);
        return getProductAdminResponse(productDb);
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException("Product with id " + id + " does not exist"));
        productRepository.delete(product);
    }

    public static ProductResponse getProductResponse(Product product) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(product.getId());

        BrandResponse brandResponse = new BrandResponse();
        brandResponse.setId(product.getBrand().getId());
        brandResponse.setName(product.getBrand().getName());

        productResponse.setBrand(brandResponse);

        List<CategoryResponse> categoryResponses = new ArrayList<>();
        product.getCategories().forEach(category -> {
            CategoryResponse categoryResponse = CategoryServiceImpl.getCategoryResponse(category);
            categoryResponses.add(categoryResponse);
        });
        productResponse.setCategories(categoryResponses);
        productResponse.setName(product.getName());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());
        productResponse.setDiscount(product.isDiscount());
        productResponse.setOfferPrice(product.getOfferPrice());
        productResponse.setStock(product.getStock());
        return productResponse;
    }

    public static ProductAdminResponse getProductAdminResponse(Product product) {
        ProductAdminResponse productResponse = new ProductAdminResponse();
        productResponse.setId(product.getId());

        BrandResponse brandResponse = new BrandResponse();
        brandResponse.setId(product.getBrand().getId());
        brandResponse.setName(product.getBrand().getName());

        productResponse.setBrand(brandResponse);

        List<CategoryResponse> categoryResponses = new ArrayList<>();
        product.getCategories().forEach(category -> {
            CategoryResponse categoryResponse = CategoryServiceImpl.getCategoryResponse(category);
            categoryResponses.add(categoryResponse);
        });
        productResponse.setCategories(categoryResponses);

        List<SupplierResponse> supplierResponses = new ArrayList<>();
        product.getSuppliers().forEach(supplier -> {
            SupplierResponse supplierResponse = SupplierServiceImpl.getSupplierResponse(supplier);
            supplierResponses.add(supplierResponse);
        });
        productResponse.setSuppliers(supplierResponses);

        productResponse.setName(product.getName());
        productResponse.setDescription(product.getDescription());
        productResponse.setPrice(product.getPrice());
        productResponse.setPurchasePrice(product.getPurchasePrice());
        productResponse.setDiscount(product.isDiscount());
        productResponse.setOfferPrice(product.getOfferPrice());
        productResponse.setStock(product.getStock());
        productResponse.setCreatedAt(product.getCreatedAt());
        return productResponse;
    }

    public void updateProductFromRequest(Product product, ProductRequest request, Brand brand, List<Category> categories, List<Supplier> suppliers) {
        product.setBrand(brand);
        product.setCategories(categories);
        product.setSuppliers(suppliers);
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setPurchasePrice(request.getPurchasePrice());
        product.setDiscount(request.isDiscount());
        product.setOfferPrice(request.getOfferPrice());
        product.setStock(request.getStock());
    }
}
