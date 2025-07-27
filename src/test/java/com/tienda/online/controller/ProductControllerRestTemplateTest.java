package com.tienda.online.controller;

import com.tienda.online.dto.auth.AuthenticationRequest;
import com.tienda.online.dto.auth.AuthenticationResponse;
import com.tienda.online.dto.products.ProductAdminResponse;
import com.tienda.online.dto.products.ProductRequest;
import com.tienda.online.dto.products.ProductResponse;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
public class ProductControllerRestTemplateTest {

    @Autowired
    private TestRestTemplate testRestTemplate;


    @Test
    @Order(2)
    void testGetAllProducts(){
        ResponseEntity<ProductResponse[]> response = testRestTemplate.getForEntity("/products", ProductResponse[].class);
        List<ProductResponse> products = Arrays.asList(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());

        assertEquals(6, products.size());
        assertEquals(1L, products.get(0).getId());
        assertEquals("Sprite 600ml", products.get(0).getName());
        assertEquals("Refresco sprite 600ml", products.get(0).getDescription());

    }

    @Test
    @Order(3)
    void testGetProductById(){
        ResponseEntity<ProductResponse> response = testRestTemplate.getForEntity("/products/1", ProductResponse.class);
        ProductResponse product = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, response.getHeaders().getContentType());

        assertNotNull(product);
        assertEquals(1L, product.getId());
        assertEquals("Sprite 600ml", product.getName());
        assertEquals("Refresco sprite 600ml", product.getDescription());
    }

    @Test
    @Order(1)
    void testSaveProductWithAuthentication(){
        String jwtToken = authenticateAndGetJwt("mhernandez", "clave123");

        ProductRequest productRequest = new ProductRequest();
        productRequest.setBrandId(101L);
        productRequest.setCategoryIds(Set.of(102L));
        productRequest.setSupplierIds(Set.of(103L));
        productRequest.setName("Sprite 600ml");
        productRequest.setDescription("Refresco sprite 600ml");
        productRequest.setPrice(new BigDecimal("13.00"));
        productRequest.setPurchasePrice(new BigDecimal("8.00"));
        productRequest.setDiscount(false);
        productRequest.setOfferPrice(new BigDecimal("8.00"));
        productRequest.setStock(150);
        productRequest.setCreatedAt(LocalDateTime.now());

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<ProductRequest> requestEntity = new HttpEntity<>(productRequest, headers);

        ResponseEntity<ProductAdminResponse> productResponse = testRestTemplate.exchange(
                "/products",
                HttpMethod.POST,
                requestEntity,
                ProductAdminResponse.class
        );

        assertEquals(HttpStatus.CREATED, productResponse.getStatusCode());
        assertEquals(MediaType.APPLICATION_JSON, productResponse.getHeaders().getContentType());

        ProductAdminResponse productSave = productResponse.getBody();
        assertNotNull(productSave);
        assertEquals(1L, productSave.getId());
        assertEquals("Sprite 600ml", productSave.getName());
        assertEquals("Refresco sprite 600ml", productSave.getDescription());

    }

    @Test
    @Order(4)
    void TestDeleteProduct(){
        String jwtToken = authenticateAndGetJwt("mhernandez", "clave123");

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(jwtToken);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<ProductAdminResponse> productResponse = testRestTemplate.exchange(
                "/products",
                HttpMethod.POST,
                requestEntity,
                ProductAdminResponse.class
        );

        ResponseEntity<ProductResponse[]> response = testRestTemplate.getForEntity("/products", ProductResponse[].class);
        List<ProductResponse> products = Arrays.asList(response.getBody());

        assertEquals(6, products.size());

        Map<String, Long> pathVariables = new HashMap<>();
        pathVariables.put("id",1L);
        ResponseEntity<String> exchange = testRestTemplate.exchange("/products/{id}", HttpMethod.DELETE, requestEntity, String.class, pathVariables);
        assertEquals(HttpStatus.OK, exchange.getStatusCode());
        assertTrue(exchange.hasBody());
        assertEquals("Product removed", exchange.getBody());

        response = testRestTemplate.getForEntity("/products", ProductResponse[].class);
        products = Arrays.asList(response.getBody());
        assertEquals(5, products.size());
        ResponseEntity<ProductResponse> responseWithDetails = testRestTemplate.getForEntity("/products/2", ProductResponse.class);

        assertEquals(HttpStatus.NOT_FOUND, responseWithDetails.getStatusCode());
        assertTrue(exchange.hasBody());
        assertEquals("Product removed", exchange.getBody());

    }

    private String authenticateAndGetJwt(String username, String password) {
        AuthenticationRequest authRequest = new AuthenticationRequest();
        authRequest.setUsername(username);
        authRequest.setPassword(password);

        ResponseEntity<AuthenticationResponse> authResponse = testRestTemplate.postForEntity(
                "/auth/authenticate", authRequest, AuthenticationResponse.class);

        assertEquals(HttpStatus.OK, authResponse.getStatusCode());
        assertNotNull(authResponse.getBody());
        return authResponse.getBody().getJwt();
    }

}
