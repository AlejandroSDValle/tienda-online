package com.tienda.online.services.impl;


import com.tienda.online.dto.category.CategoryRequest;
import com.tienda.online.dto.category.CategoryResponse;
import com.tienda.online.dto.products.ProductResponse;
import com.tienda.online.entities.Category;
import com.tienda.online.entities.Product;
import com.tienda.online.exceptions.ObjectNotFoundException;
import com.tienda.online.repositories.CategoryRepository;
import com.tienda.online.services.CategoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories() {
        List<CategoryResponse> categoryResponses = new ArrayList<>();
        categoryRepository.findAll().forEach(category -> {
            CategoryResponse categoryResponse = getCategoryResponse(category);
            categoryResponses.add(categoryResponse);
        });
        return categoryResponses;
    }

    @Override
    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException("Category with ID " + id + " does not exist"));
        return getCategoryResponse(category);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> getProductsByCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException("Category with ID " + id + " does not exist"));
        List<ProductResponse> productResponses = new ArrayList<>();
        category.getProducts().forEach(product -> {
            productResponses.add(ProductServiceImpl.getProductResponse(product));
        });
        return productResponses;
    }

    @Override
    @Transactional
    public void createCategory(CategoryRequest categoryRequest) {
        Category category = new Category();
        category.setName(categoryRequest.getName());
        categoryRepository.save(category);
    }

    @Override
    @Transactional
    public CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException("Category with ID " + id + " does not exist"));
        category.setName(categoryRequest.getName());
        categoryRepository.save(category);
        return getCategoryResponse(category);
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(()-> new ObjectNotFoundException("Category with ID " + id + " does not exist"));

        // Quitar relación con productos
        for (Product product : category.getProducts()) {
            product.getCategories().remove(category);
        }

        categoryRepository.delete(category);
    }

    public static CategoryResponse getCategoryResponse(Category category) {
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setId(category.getId());
        categoryResponse.setName(category.getName());
        return categoryResponse;
    }
}
