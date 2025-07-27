package com.tienda.online.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tienda.online.config.security.filter.JwtAuthenticationFilter;
import com.tienda.online.controllers.ProductController;
import com.tienda.online.dto.brands.BrandResponse;
import com.tienda.online.dto.category.CategoryResponse;
import com.tienda.online.dto.products.ProductAdminResponse;
import com.tienda.online.dto.products.ProductRequest;
import com.tienda.online.dto.products.ProductResponse;
import com.tienda.online.dto.supplier.SupplierResponse;
import com.tienda.online.exceptions.ObjectNotFoundException;
import com.tienda.online.services.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = ProductController.class,
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = JwtAuthenticationFilter.class
        )
)
@AutoConfigureMockMvc(addFilters = false)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductService productService;

    private BrandResponse brand;
    private CategoryResponse category;
    private SupplierResponse supplier;
    private ProductResponse product;

    @BeforeEach
    void setup(){
        brand = new BrandResponse();
        brand.setId(1L);
        brand.setName("Coca Cola");

        category = new CategoryResponse();
        category.setId(2L);
        category.setName("Bebidas");

        supplier = new SupplierResponse();
        supplier.setId(3L);
        supplier.setName("Proveedor Sur");

        product = new ProductResponse(1L, "Coca Cola 600ml", "Refresco sabor cola de 600ml", new BigDecimal("18.50"), false, BigDecimal.ZERO, 100, brand, List.of(category));
    }

    @Test
    void testGetAllProducts() throws Exception {
        List<ProductResponse> listProducts = new ArrayList<>();
        listProducts.add(product);
        listProducts.add(new ProductResponse(2L, "Coca Cola Light", "Versión sin azúcar de Coca Cola", new BigDecimal("20.00"), true, new BigDecimal("17.50"), 80, brand, List.of(category)));
        listProducts.add(new ProductResponse(3L, "Sprite 500ml", "Refresco sabor lima-limón de 500ml", new BigDecimal("17.00"), false, BigDecimal.ZERO, 120, brand, List.of(category)));
        listProducts.add(new ProductResponse(4L, "Fanta Naranja 600ml", "Refresco sabor naranja", new BigDecimal("19.00"), true, new BigDecimal("15.00"), 60, brand, List.of(category)));
        listProducts.add(new ProductResponse(5L, "Agua Ciel 1L", "Agua purificada sin gas", new BigDecimal("12.00"), false, BigDecimal.ZERO, 200, brand, List.of(category)));

        given(productService.getAllProducts()).willReturn(listProducts);

        mockMvc.perform(get("/products"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(listProducts.size())))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("Coca Cola 600ml")))
                .andExpect(jsonPath("$[0].price", is(18.50)))
                .andExpect(jsonPath("$[0].discount", is(false)))
                .andExpect(jsonPath("$[0].stock", is(100)));


    }

    @Test
    void TestGetProductById() throws Exception {
        Long productoId = 1L;

        given(productService.getProductById(productoId)).willReturn(product);

        mockMvc.perform(get("/products/{id}", productoId))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Coca Cola 600ml")))
                .andExpect(jsonPath("$.price", is(18.50)))
                .andExpect(jsonPath("$.discount", is(false)))
                .andExpect(jsonPath("$.stock", is(100)));
    }

    @Test
    void TestGetProductNotFoundById() throws Exception {
        Long productoId = 1L;

        given(productService.getProductById(productoId))
                .willThrow(new ObjectNotFoundException("Product with id " + productoId + " does not exist"));

        mockMvc.perform(get("/products/{id}", productoId))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void testSaveProduct() throws Exception {
        ProductRequest request = new ProductRequest();
        request.setBrandId(1L);
        request.setCategoryIds(Set.of(2L));
        request.setSupplierIds(Set.of(3L));
        request.setName("Sprite 600ml");
        request.setDescription("Refresco sprite 600ml");
        request.setPrice(new BigDecimal("13.00"));
        request.setDiscount(false);
        request.setOfferPrice(new BigDecimal("8.00"));
        request.setStock(150);
        request.setPurchasePrice(new BigDecimal("8.00"));

        ProductAdminResponse response = new ProductAdminResponse();
        response.setName("Sprite 600ml");
        response.setDescription("Refresco sprite 600ml");
        response.setPrice(new BigDecimal("13.00"));
        response.setPurchasePrice(new BigDecimal("8.00"));
        response.setDiscount(false);
        response.setOfferPrice(new BigDecimal("8.00"));
        response.setStock(150);
        response.setBrand(brand);
        response.setCategories(List.of(category));
        response.setSuppliers(List.of(supplier));


        given(productService.createProduct(any(ProductRequest.class)))
                .willReturn(response);

        ResultActions result = mockMvc.perform(post("/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        result.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(response.getName())))
                .andExpect(jsonPath("$.description", is(response.getDescription())))
                .andExpect(jsonPath("$.price", is(response.getPrice().doubleValue())));

    }

    @Test
    void TestUpdateProduct() throws Exception{
        Long productId = 1L;

        ProductRequest request = new ProductRequest();
        request.setBrandId(1L);
        request.setCategoryIds(Set.of(2L));
        request.setSupplierIds(Set.of(3L));
        request.setName("Sprite 600ml");
        request.setDescription("Refresco sprite 600ml");
        request.setPrice(new BigDecimal("13.00"));
        request.setDiscount(false);
        request.setOfferPrice(new BigDecimal("8.00"));
        request.setStock(150);
        request.setPurchasePrice(new BigDecimal("8.00"));

        ProductAdminResponse response = new ProductAdminResponse();
        response.setName("Sprite 300ml");
        response.setDescription("Refresco sprite 300ml");
        response.setPrice(new BigDecimal("13.00"));
        response.setPurchasePrice(new BigDecimal("5.00"));
        response.setDiscount(true);
        response.setOfferPrice(new BigDecimal("8.00"));
        response.setStock(150);
        response.setBrand(brand);
        response.setCategories(List.of(category));
        response.setSuppliers(List.of(supplier));

        given(productService.updateProduct(eq(productId), any(ProductRequest.class)))
                .willReturn(response);

        mockMvc.perform(put("/products/{id}", productId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(response)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Sprite 300ml"))
                .andExpect(jsonPath("$.purchasePrice").value(5.00))
                .andExpect(jsonPath("$.offerPrice").value(8.00))
                .andDo(print());
    }

    @Test
    void TestDeleteProduct() throws Exception{
        Long productId = 1L;

        willDoNothing().given(productService).deleteProduct(productId);

        mockMvc.perform(delete("/products/{id}", productId))
                .andExpect(status().isOk())
                .andDo(print());
    }

}
