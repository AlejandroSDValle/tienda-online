package com.tienda.online.services;


import com.tienda.online.dto.category.CategoryRequest;
import com.tienda.online.dto.category.CategoryResponse;
import com.tienda.online.dto.products.ProductResponse;

import java.util.List;

public interface CategoryService {

    List<CategoryResponse> getAllCategories();

    CategoryResponse getCategoryById(Long id);

    List<ProductResponse> getProductsByCategory(Long id);

    void createCategory(CategoryRequest categoryRequest);

    CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest);

    void deleteCategory(Long id);
}
