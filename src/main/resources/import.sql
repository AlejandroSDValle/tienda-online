-----------------------------------------------------------------------------------
------------------------------- CREACIÓN DE MODULOS -------------------------------
-----------------------------------------------------------------------------------
INSERT INTO module (name, base_path) VALUES ('PRODUCT', '/products');
INSERT INTO module (name, base_path) VALUES ('BRAND', '/brands');
INSERT INTO module (name, base_path) VALUES ('CATEGORY', '/categories');
INSERT INTO module (name, base_path) VALUES ('SUPPLIER', '/suppliers');
INSERT INTO module (name, base_path) VALUES ('CUSTOMER', '/customers');
INSERT INTO module (name, base_path) VALUES ('AUTH', '/auth');
INSERT INTO module (name, base_path) VALUES ('ORDER', '/orders');

-----------------------------------------------------------------------------------
---------------------------- CREACIÓN DE OPERACIONES ------------------------------
-----------------------------------------------------------------------------------
----------------- OPERACIONES DE PRODUCTOS ---------------- 1 - 7
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('READ_ALL_PRODUCTS_ADMIN','/admin', 'GET', false, 1);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('READ_ONE_PRODUCT_ADMIN','/[0-9]*/admin', 'GET', false, 1);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('READ_LOW-STORCK_PRODUCT','/low-stock', 'GET', false, 1);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('CREATE_ONE_PRODUCT','', 'POST', false, 1);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('UPDATE_ONE_PRODUCT','/[0-9]*', 'PUT', false, 1);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('OFFER_ONE_PRODUCT','/[0-9]*/toggle-offer', 'PATCH', false, 1);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('DELETE_ONE_PRODUCT','/[0-9]*', 'DELETE', false, 1);

----------------- OPERACIONES DE MARCAS ---------------- 8 - 10
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('CREATE_ONE_BRAND','', 'POST', false, 2);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('UPDATE_ONE_BRAND','/[0-9]*', 'PUT', false, 2);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('DELETE_ONE_BRAND','/[0-9]*', 'DELETE', false, 2);

----------------- OPERACIONES DE CATEGORIAS ---------------- 11 - 13
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('CREATE_ONE_CATEGORY','', 'POST', false, 3);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('UPDATE_ONE_CATEGORY','/[0-9]*', 'PUT', false, 3);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('DELETE_ONE_CATEGORY','/[0-9]*', 'DELETE', false, 3);

----------------- OPERACIONES DE PROVEEDORES ---------------- 14 - 19
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('READ_ALL_SUPPLIERS','', 'GET', false, 4);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('READ_ONE_SUPPLIER','/[0-9]*', 'GET', false, 4);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('READ_ALL_PRODUCTS_BY_SUPPLIER','/[0-9]*/products', 'GET', false, 4);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('CREATE_ONE_SUPPLIER','', 'POST', false, 4);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('UPDATE_ONE_SUPPLIER','/[0-9]*', 'PUT', false, 4);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('DELETE_ONE_SUPPLIER','/[0-9]*', 'DELETE', false, 4);

----------------- OPERACIONES DE CUSTOMER ---------------- 20 - 22
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('SEARCH_CUSTOMER_BY_EMAIL','', 'GET', false, 5);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('SEARCH_EMPLOYEES','/search/employees', 'GET', false, 5);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('READ_MY_PROFILE','/profile','GET', false, 5);


----------------- OPERACIONES DE AUTH ---------------- 23 -24
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('VERIFY_ACCOUNT','/verify', 'POST', false, 6);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('VERIFY_ACCOUNT','/verify-with-email', 'POST', false, 6);

----------------- OPERACIONES DE ORDERS ---------------- 25 - 34
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('GET_USER_ORDERS','/me', 'GET', false, 7);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('GET_USER_ORDERS_BY_STATUS','/me/status', 'GET', false, 7);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('GET_USER_ORDERS_BY_DATE','/me/date', 'GET', false, 7);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('GET_ALL_ORDERS','/admin', 'GET', false, 7);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('GET_ALL_ORDERS_BY_STATUS','/admin/status', 'GET', false, 7);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('GET_ALL_ORDERS_BY_DATE','/admin/date', 'GET', false, 7);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('GET_ALL_ORDERS_BY_EMPLOYEE','/employee/[0-9]*', 'GET', false, 7);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('GET_ALL_ORDERS_BY_CUSTOMER','/customer/[0-9]*', 'GET', false, 7);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('CREATE_ONE_ORDER','', 'POST', false, 7);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('UPDATE_STATUS','/order/[0-9]*/status', 'PATCH', false, 7);


--------------------------------------------------------------- PUBLICOS ----------------------------------------------------------------------------
----------------- OPERACIONES DE PRODUCTOS PUBLICOS----------------
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('READ_ALL_PRODUCTS','', 'GET', true, 1);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('READ_ONE_PRODUCT','/[0-9]*', 'GET', true, 1);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('READ_AVAILABLE_PRODUCT','/availability', 'GET', true, 1);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('SEARCH_PRODUCT','/search', 'GET', true, 1);

----------------- OPERACIONES DE MARCAS PUBLICOS----------------
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('READ_ALL_BRANDS','', 'GET', true, 2);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('READ_ONE_BRAND','/[0-9]*', 'GET', true, 2);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('READ_ALL_PRODUCTS_BY_BRAND','/[0-9]*/products', 'GET', true, 2);

----------------- OPERACIONES DE CATEGORIAS PUBLICOS ----------------
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('READ_ALL_CATEGORIES','', 'GET', true, 3);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('READ_ONE_CATEGORY','/[0-9]*', 'GET', true, 3);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('READ_ALL_PRODUCTS_BY_CATEGORY','/[0-9]*/products', 'GET', true, 3);

----------------- OPERACIONES DE CUSTOMER PUBLICOS----------------

----------------- OPERACIONES DE AUTH PUBLICOS----------------
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('AUTHENTICATE','/authenticate', 'POST', true, 6);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('VALIDATE-TOKEN','/validate', 'GET', true, 6);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('REGISTER_ONE','', 'POST', true, 6);
INSERT INTO operation (name, path, http_method, permit_all, module_id) VALUES ('VERIFY_ACCOUNT','/generate-token', 'POST', true, 6);

---------------------------------------------------------------------------------------------
------------------------------------- CREACIÓN DE ROLES -------------------------------------
---------------------------------------------------------------------------------------------
INSERT INTO role (name) VALUES ('CUSTOMER');
INSERT INTO role (name) VALUES ('ASSISTANT_ADMINISTRATOR');
INSERT INTO role (name) VALUES ('ADMINISTRATOR');

------------------------------------------------------------------------------------------------
------------------------------------- CREACIÓN DE PERMISOS -------------------------------------
------------------------------------------------------------------------------------------------
------------------- PERMISOS DE CUSTOMER ------------------
INSERT INTO granted_permission (role_id, operation_id) VALUES (1, 22);
INSERT INTO granted_permission (role_id, operation_id) VALUES (1, 25);
INSERT INTO granted_permission (role_id, operation_id) VALUES (1, 26);
INSERT INTO granted_permission (role_id, operation_id) VALUES (1, 27);
INSERT INTO granted_permission (role_id, operation_id) VALUES (1, 33);

------------------- PERMISOS DE ASSISTANT ------------------
INSERT INTO granted_permission (role_id, operation_id) VALUES (2, 1);
INSERT INTO granted_permission (role_id, operation_id) VALUES (2, 2);
INSERT INTO granted_permission (role_id, operation_id) VALUES (2, 3);
INSERT INTO granted_permission (role_id, operation_id) VALUES (2, 6);
INSERT INTO granted_permission (role_id, operation_id) VALUES (2, 14);
INSERT INTO granted_permission (role_id, operation_id) VALUES (2, 15);
INSERT INTO granted_permission (role_id, operation_id) VALUES (2, 16);
INSERT INTO granted_permission (role_id, operation_id) VALUES (2, 20);
INSERT INTO granted_permission (role_id, operation_id) VALUES (2, 21);
INSERT INTO granted_permission (role_id, operation_id) VALUES (2, 22);
INSERT INTO granted_permission (role_id, operation_id) VALUES (2, 23);
INSERT INTO granted_permission (role_id, operation_id) VALUES (2, 24);
INSERT INTO granted_permission (role_id, operation_id) VALUES (2, 28);
INSERT INTO granted_permission (role_id, operation_id) VALUES (2, 29);
INSERT INTO granted_permission (role_id, operation_id) VALUES (2, 30);
INSERT INTO granted_permission (role_id, operation_id) VALUES (2, 31);
INSERT INTO granted_permission (role_id, operation_id) VALUES (2, 32);
INSERT INTO granted_permission (role_id, operation_id) VALUES (2, 33);
INSERT INTO granted_permission (role_id, operation_id) VALUES (2, 34);

----------------- PERMISOS DE ADMINISTRATOR ----------------
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 1);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 2);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 3);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 4);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 5);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 6);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 7);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 8);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 9);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 10);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 11);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 12);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 13);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 14);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 15);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 16);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 17);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 18);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 19);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 20);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 21);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 22);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 23);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 24);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 28);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 29);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 30);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 31);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 32);
INSERT INTO granted_permission (role_id, operation_id) VALUES (2, 33);
INSERT INTO granted_permission (role_id, operation_id) VALUES (3, 34);

-----------------------------------------------------------------------------------------------
------------------------------------- CREACIÓN DE USUARIOS ------------------------------------
-----------------------------------------------------------------------------------------------
INSERT INTO users (confirmed, email, username, name, password, role_id) VALUES (1, 'lmarquez@hotmail.com','lmarquez', 'luis márquez', '$2a$10$ywh1O2EwghHmFIMGeHgsx.9lMw5IXpg4jafeFS.Oi6nFv0181gHli', 1);
INSERT INTO users (confirmed, email, username, name, password, role_id) VALUES (1, 'alejandro@hotmail.com','alejandro', 'Alejandro Santi', '$2a$10$ywh1O2EwghHmFIMGeHgsx.9lMw5IXpg4jafeFS.Oi6nFv0181gHli', 1);
INSERT INTO users (confirmed, email, username, name, password, role_id) VALUES (1, 'juan@hotmail.com','juan', 'Juan Nenduano', '$2a$10$ywh1O2EwghHmFIMGeHgsx.9lMw5IXpg4jafeFS.Oi6nFv0181gHli', 1);
INSERT INTO users (confirmed, email, username, name, password, role_id) VALUES (1, 'fperez@hotmail.com','fperez', 'fulano pérez', '$2a$10$ywh1O2EwghHmFIMGeHgsx.9lMw5IXpg4jafeFS.Oi6nFv0181gHli', 2);
INSERT INTO users (confirmed, email, username, name, password, role_id) VALUES (1, 'mhernandez@hotmail.com','mhernandez', 'mengano hernández', '$2a$10$ywh1O2EwghHmFIMGeHgsx.9lMw5IXpg4jafeFS.Oi6nFv0181gHli', 3);

----------------------------------------------------------------------------------------------
------------------------------------- CREACIÓN DE MARCAS -------------------------------------
----------------------------------------------------------------------------------------------
INSERT INTO brands (id, name) VALUES (101, 'Sony');
INSERT INTO brands (id, name) VALUES (102, 'Samsung');
INSERT INTO brands (id, name) VALUES (103, 'LG');
INSERT INTO brands (id, name) VALUES (104, 'Apple');
INSERT INTO brands (id, name) VALUES (105, 'Lenovo');

--------------------------------------------------------------------------------------------------
------------------------------------- CREACIÓN DE CATEGORIAS -------------------------------------
--------------------------------------------------------------------------------------------------
INSERT INTO categories (id, name) VALUES (101, 'Electrónica');
INSERT INTO categories (id, name) VALUES (102, 'Computación');
INSERT INTO categories (id, name) VALUES (103, 'Accesorios');
INSERT INTO categories (id, name) VALUES (104, 'Hogar');
INSERT INTO categories (id, name) VALUES (105, 'Juguetes');


---------------------------------------------------------------------------------------------------
------------------------------------- CREACIÓN DE PROVEEDORES -------------------------------------
---------------------------------------------------------------------------------------------------
INSERT INTO suppliers (id, name, phone, email) VALUES (101, 'Proveedor Uno', '5551234567', 'contacto1@proveedor.com');
INSERT INTO suppliers (id, name, phone, email) VALUES (102, 'Proveedor Dos', '5552345678', 'contacto2@proveedor.com');
INSERT INTO suppliers (id, name, phone, email) VALUES (103, 'Proveedor Tres', '5553456789', 'contacto3@proveedor.com');
INSERT INTO suppliers (id, name, phone, email) VALUES (104, 'Proveedor Cuatro', '5554567890', 'contacto4@proveedor.com');
INSERT INTO suppliers (id, name, phone, email) VALUES (105, 'Proveedor Cinco', '5555678901', 'contacto5@proveedor.com');


-------------------------------------------------------------------------------------------------
------------------------------------- CREACIÓN DE PRODUCTOS -------------------------------------
-------------------------------------------------------------------------------------------------
INSERT INTO products (id, brand_id, name, description, price, purchase_price, discount, offer_price, stock, created_at) VALUES (101, 101, 'Audífonos Inalámbricos', 'Audífonos con cancelación de ruido', 1500.00, 1200.00, true, 1300.00, 125, '2024-07-01 10:00:00');
INSERT INTO products (id, brand_id, name, description, price, purchase_price, discount, offer_price, stock, created_at) VALUES (102, 102, 'Smart TV 50"', 'Televisión 4K UHD', 8000.00, 6000.00, true, 7500.00, 110, '2024-07-02 12:00:00');
INSERT INTO products (id, brand_id, name, description, price, purchase_price, discount, offer_price, stock, created_at) VALUES (103, 103, 'Laptop Gamer', '16GB RAM, RTX 3050', 20000.00, 17000.00, false, NULL, 115, '2024-07-03 14:00:00');
INSERT INTO products (id, brand_id, name, description, price, purchase_price, discount, offer_price, stock, created_at) VALUES (104, 104, 'iPhone 14', '128GB, Midnight', 23000.00, 20000.00, true, 21000.00, 118, '2024-07-04 16:00:00');
INSERT INTO products (id, brand_id, name, description, price, purchase_price, discount, offer_price, stock, created_at) VALUES (105, 105, 'Tablet Lenovo', '10.1" HD, 64GB', 5000.00, 4000.00, false, NULL, 115, '2024-07-05 09:00:00');


-----------------------------------------------------------------------------------------------------------
------------------------------------- CREACIÓN DE PRODUCTS_CATEGORIES -------------------------------------
-----------------------------------------------------------------------------------------------------------
INSERT INTO products_categories (product_id, category_id) VALUES (101, 101);
INSERT INTO products_categories (product_id, category_id) VALUES (101, 103);;
INSERT INTO products_categories (product_id, category_id) VALUES (102, 101);
INSERT INTO products_categories (product_id, category_id) VALUES (102, 104);
INSERT INTO products_categories (product_id, category_id) VALUES (103, 102);
INSERT INTO products_categories (product_id, category_id) VALUES (104, 101);
INSERT INTO products_categories (product_id, category_id) VALUES (104, 102);
INSERT INTO products_categories (product_id, category_id) VALUES (105, 102);
INSERT INTO products_categories (product_id, category_id) VALUES (105, 104);
INSERT INTO products_categories (product_id, category_id) VALUES (105, 103);

----------------------------------------------------------------------------------------------------------
------------------------------------- CREACIÓN DE PRODUCTS_SUPPLIERS -------------------------------------
----------------------------------------------------------------------------------------------------------
INSERT INTO products_suppliers (product_id, supplier_id) VALUES (101, 101);
INSERT INTO products_suppliers (product_id, supplier_id) VALUES (101, 102);
INSERT INTO products_suppliers (product_id, supplier_id) VALUES (102, 102);
INSERT INTO products_suppliers (product_id, supplier_id) VALUES (102, 103);
INSERT INTO products_suppliers (product_id, supplier_id) VALUES (103, 103);
INSERT INTO products_suppliers (product_id, supplier_id) VALUES (103, 104);
INSERT INTO products_suppliers (product_id, supplier_id) VALUES (104, 101);
INSERT INTO products_suppliers (product_id, supplier_id) VALUES (104, 105);
INSERT INTO products_suppliers (product_id, supplier_id) VALUES (105, 104);
INSERT INTO products_suppliers (product_id, supplier_id) VALUES (105, 105);

----------------------------------------------------------------------------------------------------------
------------------------------------- CREACIÓN DE ORDERS -------------------------------------
----------------------------------------------------------------------------------------------------------
-- 3 pedidos del usuario 1
INSERT INTO orders (client_id, employee_id, created_at, status) VALUES (1, null, '2024-08-19 10:30:00', 'PLACED');
INSERT INTO orders (client_id, employee_id, created_at, status) VALUES (1, null, NOW(), 'QUEUED');
INSERT INTO orders (client_id, employee_id, created_at, status) VALUES (1, 5, '2024-08-20 09:15:00', 'PREPARING');
INSERT INTO orders (client_id, employee_id, created_at, status) VALUES (1, 4, '2024-09-01 14:50:00', 'PREPARING');
INSERT INTO orders (client_id, employee_id, created_at, status) VALUES (1, null, NOW(), 'PLACED');

-- 2 pedidos del usuario 2
INSERT INTO orders (client_id, employee_id, created_at, status) VALUES (2, 5, '2024-09-15 18:45:00', 'PICKED_UP');
INSERT INTO orders (client_id, employee_id, created_at, status) VALUES (2, 4, '2024-10-02 08:30:00', 'PREPARING');
INSERT INTO orders (client_id, employee_id, created_at, status) VALUES (2, 4, NOW(), 'READY');
INSERT INTO orders (client_id, employee_id, created_at, status) VALUES (2, 5, '2024-10-20 12:00:00', 'PICKED_UP');

-- 2 pedidos del usuario 3
INSERT INTO orders (client_id, employee_id, created_at, status) VALUES (3, 5, '2024-11-05 15:10:00', 'READY');
INSERT INTO orders (client_id, employee_id, created_at, status) VALUES (3, 4, NOW(), 'CANCELLED');
INSERT INTO orders (client_id, employee_id, created_at, status) VALUES (3, null, '2024-12-01 11:00:00', 'PLACED');

-- 2 pedidos sin cliente
INSERT INTO orders (client_id, employee_id, created_at, status) VALUES (NULL, 5, '2025-01-10 17:30:00', 'PICKED_UP');
INSERT INTO orders (client_id, employee_id, created_at, status) VALUES (NULL, 4, '2025-03-22 16:00:00', 'PICKED_UP');
INSERT INTO orders (client_id, employee_id, created_at, status) VALUES (NULL, 5, '2025-06-05 13:20:00', 'PICKED_UP');


-- Para los pedidos 1 al 15
INSERT INTO order_items (order_id, product_id, quantity) VALUES (1, 101, 2);
INSERT INTO order_items (order_id, product_id, quantity) VALUES (1, 102, 1);

INSERT INTO order_items (order_id, product_id, quantity) VALUES (2, 103, 5);

INSERT INTO order_items (order_id, product_id, quantity) VALUES (3, 104, 3);

INSERT INTO order_items (order_id, product_id, quantity) VALUES (4, 105, 1);

INSERT INTO order_items (order_id, product_id, quantity) VALUES (5, 102, 4);
INSERT INTO order_items (order_id, product_id, quantity) VALUES (5, 101, 2);

INSERT INTO order_items (order_id, product_id, quantity) VALUES (6, 101, 3);

INSERT INTO order_items (order_id, product_id, quantity) VALUES (7, 104, 2);

INSERT INTO order_items (order_id, product_id, quantity) VALUES (8, 104, 1);
INSERT INTO order_items (order_id, product_id, quantity) VALUES (8, 102, 2);

INSERT INTO order_items (order_id, product_id, quantity) VALUES (9, 105, 4);

INSERT INTO order_items (order_id, product_id, quantity) VALUES (10, 105, 1);
INSERT INTO order_items (order_id, product_id, quantity) VALUES (10, 101, 2);

INSERT INTO order_items (order_id, product_id, quantity) VALUES (11, 103, 5);

INSERT INTO order_items (order_id, product_id, quantity) VALUES (12, 102, 1);

INSERT INTO order_items (order_id, product_id, quantity) VALUES (13, 105, 3);
INSERT INTO order_items (order_id, product_id, quantity) VALUES (13, 103, 2);

INSERT INTO order_items (order_id, product_id, quantity) VALUES (14, 103, 2);

INSERT INTO order_items (order_id, product_id, quantity) VALUES (15, 104, 4);
