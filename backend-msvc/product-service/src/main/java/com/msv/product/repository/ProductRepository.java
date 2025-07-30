package com.msv.product.repository;

import com.msv.product.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE p.stock > 0")
    List<Product> getAvailableProducts();

    @Query("SELECT p FROM Product p WHERE p.stock < 5")
    List<Product> findProductsWithLowStock();

    @Query("""
        SELECT p FROM Product p
        WHERE LOWER(p.name) LIKE LOWER(CONCAT(:name, '%'))
           OR LOWER(p.name) LIKE LOWER(CONCAT('%', :name, '%'))
        ORDER BY
            CASE
                WHEN LOWER(p.name) LIKE LOWER(CONCAT(:name, '%')) THEN 0
                ELSE 1
            END
    """)
    List<Product> searchByNameOrdered(@Param("name") String name);

}