# Application Layer Architecture

The Application Layer in the Sales Management System (SMS) comprises controllers, [services](#service-architecture), and [Data Transfer Objects](#data-transfer-objects) (DTOs) that collectively handle various aspects of the system's functionality.

## Controller Architecture

### UserController

#### Product Endpoints:

@RequestMapping("/user")

- **GET /products:**
  - Description: Retrieve a list of products.
  - Functionality: Returns a paginated list of available products.

- **GET /products/{id}:**
  - Description: Retrieve details of a specific product.
  - Functionality: Fetches and returns detailed information about a particular product based on its unique identifier.

- **GET /products/{name}:**
  - Description: Retrieve details of a specific product.
  - Functionality: Fetches and returns detailed information about a particular product or products based on its name.

#### Order Endpoints:

- **POST /orders:**
  - Description: Create a new order.
  - Functionality: Accepts data to create a new order and returns the newly created order's details.

### AdminController

#### Product Endpoints:

- **All user endpoints and:**
    
    @RequestMapping("/admin")

- **POST /products:**
  - Description: Create a new product.
  - Functionality: Accepts data to create a new product and returns the newly created product's details.

- **PUT /products/{id}:**
  - Description: Update an existing product.
  - Functionality: Updates the attributes of an existing product based on its unique identifier and returns the updated product.

- **DELETE /products/{id}:**
  - Description: Delete a product.
  - Functionality: Removes a product from the system based on its unique identifier.


#### Users Endpoints:

@RequestMapping("/admin")

- **GET /users:**
  - Description: Retrieve a list of users.

- **GET /user/{id}:**
  - Description: Retrieve details of a specific user.
  - Functionality: Fetches and returns detailed information about a specific user based on their unique identifier.

- **POST /user:**
  - Description: Create a new user.
  - Functionality: Accepts data to create a new user and returns the newly created user's details.

- **PUT /user/{id}:**
  - Description: Update an existing user.
  - Functionality: Updates the attributes of an existing user based on their unique identifier and returns the updated user information.

- **DELETE /user/{id}:**
  - Description: Delete a user.
  - Functionality: Removes a user from the system based on their unique identifier.

#### Order Endpoints:

- **All user endpoints and:**
    
    @RequestMapping("/admin")

- **GET /orders:**
  - Description: Retrieve a list of orders.
  - Functionality: Returns a paginated list of all orders placed in the system.

- **GET /orders/{id}:**
  - Description: Retrieve details of a specific order.
  - Functionality: Fetches and returns detailed information about a specific order based on its unique identifier.

- **PUT /orders/{id}:**
  - Description: Update an existing order.
  - Functionality: Updates the attributes of an existing order based on its unique identifier and returns the updated order information.

- **DELETE /orders/{id}:**
  - Description: Delete an order.
  - Functionality: Removes an order from the system based on its unique identifier.

### InvoiceController

#### Endpoints:

- **GET /invoices:**
  - Description: Retrieve a list of invoices.
  - Functionality: Returns a paginated list of all invoices generated in the system.

- **GET /invoices/{id}:**
  - Description: Retrieve details of a specific invoice.
  - Functionality: Fetches and returns detailed information about a specific invoice based on its unique identifier.

- **POST /invoices:**
  - Description: Create a new invoice.
  - Functionality: Accepts data to create a new invoice and returns the newly created invoice's details.

- **PUT /invoices/{id}:**
  - Description: Update an existing invoice.
  - Functionality: Updates the attributes of an existing invoice based on its unique identifier and returns the updated invoice information.

- **DELETE /invoices/{id}:**
  - Description: Delete an invoice.
  - Functionality: Removes an invoice from the system based on its unique identifier.

The Controller Architecture defines the endpoints and their functionalities for managing products, Users, orders, and invoices within the Sales Management System (SMS).


## Service Architecture

The Service Architecture defines the methods and functionality for managing products, Users, orders, invoices, and order items within the Sales Management System (SMS).

### ProductService

#### Methods:

- **getAllProducts():**
  - Description: Retrieve a list of all products.
  - Functionality: Fetches and returns a paginated list of all available products.

- **getProductById(Long id):**
  - Description: Retrieve product details by ID.
  - Functionality: Retrieves and returns detailed information about a specific product based on its unique identifier.

- **getProductByBrandName(String brandName):**
  - Description: Retrieve list of products by a brand.
  - Functionality: Retrieves and returns a paginated list of products based on the unique brand name.
  
- **createProduct(ProductDTO productDTO):**
  - Description: Create a new product.
  - Functionality: Accepts product data and creates a new product in the system, returning the details of the newly created product.

- **updateProduct(Long id, ProductDTO productDTO):**
  - Description: Update an existing product.
  - Functionality: Updates the attributes of an existing product based on its unique identifier and the provided product data. Returns the updated product information.

- **deleteProduct(Long id):**
  - Description: Delete a product.
  - Functionality: Removes a product from the system based on its unique identifier.

### UserService

#### Methods:

- **getAllUsers():**
  - Description: Retrieve a list of all Users.
  - Functionality: Retrieves and returns a paginated list of all registered Users.

- **getUserById(Long id):**
  - Description: Retrieve User details by ID.
  - Functionality: Fetches and returns detailed information about a specific User based on their unique identifier.

- **createUser(UserDTO UserDTO):**
  - Description: Create a new User.
  - Functionality: Accepts User data and creates a new User record, returning the details of the newly created User.

- **updateUser(Long id, UserDTO UserDTO):**
  - Description: Update an existing User.
  - Functionality: Updates the attributes of an existing User based on their unique identifier and the provided User data. Returns the updated User information.

- **deleteUser(Long id):**
  - Description: Delete a User.
  - Functionality: Removes a User from the system based on their unique identifier.

### OrderService

#### Methods:

- **getAllOrdersPaginated(int pageNumber, int pageSize):**
  - Description: Retrieve a list of all orders.
  - Functionality: Retrieves and returns a paginated list of all orders placed in the system.

- **getOrdersByDatePaginated(...args):**
  - Description: Retrieve a list of all by date.
  - Functionality: Retrieves and returns a paginated list of all orders by date or date range.

- **getOrderById(Long id):**
  - Description: Retrieve order details by ID.
  - Functionality: Fetches and returns detailed information about a specific order based on its unique identifier.

- **createOrder(User user, List<OrderItemDTO> orderItemDTOList):**
  - Description: Create a new order.
  - Functionality: Accepts user data and a list of orderItemDTO, then creates a new order, returning the details of the newly created order.

- **deleteOrder(Long id):**
  - Description: Delete an order.
  - Functionality: Removes an order from the system based on its unique identifier.

### InvoiceService

#### Methods:

- **getAllInvoices():**
  - Description: Retrieve a list of all invoices.
  - Functionality: Retrieves and returns a paginated list of all invoices generated in the system.

- **getInvoiceById(Long id):**
  - Description: Retrieve invoice details by ID.
  - Functionality: Fetches and returns detailed information about a specific invoice based on its unique identifier.

- **createInvoice(InvoiceDTO invoiceDTO):**
  - Description: Create a new invoice.
  - Functionality: Accepts invoice data and creates a new invoice, returning the details of the newly created invoice.

- **updateInvoice(Long id, InvoiceDTO invoiceDTO):**
  - Description: Update an existing invoice.
  - Functionality: Updates the attributes of an existing invoice based on its unique identifier and the provided invoice data. Returns the updated invoice information.

- **deleteInvoice(Long id):**
  - Description: Delete an invoice.
  - Functionality: Removes an invoice from the system based on its unique identifier.

### OrderItemService

#### Methods:

- **getAllOrderItems():**
  - Description: Retrieve a list of all order items.
  - Functionality: Retrieves and returns a paginated list of all order items within the system.

- **getOrderItemById(Long id):**
  - Description: Retrieve order item details by ID.
  - Functionality: Fetches and returns detailed information about a specific order item based on its unique identifier.

- **createOrderItem(OrderItemDTO orderItemDTO, Order order):**
  - Description: Create a new order item.
  - Functionality: Accepts order item data and creates a new order item, returning the details of the newly created item.

- **updateOrderItem(Long id, OrderItemDTO orderItemDTO):**
  - Description: Update an existing order item.
  - Functionality: Updates the attributes of an existing order item based on its unique identifier and the provided order item data. Returns the updated order item information.

<!-- - **deleteOrderItem(Long id):**
  - Description: Delete an order item.
  - Functionality: Removes an order item from the system based on its unique identifier. -->


## DTO (Data Transfer Object) Architecture

The DTO architecture defines the structures of Data Transfer Objects used for efficient data exchange between the frontend and backend components of the Sales Management System (SMS).

### ProductDTO

#### Fields:

- **id:** The unique identifier for the product.
- **name:** The name of the product.
- **price:** The price of the product.
- **[Other Relevant Fields]:** Any other relevant fields related to products.

### UserDTO

#### Fields:

- **id:** The unique identifier for the User.
- **firstName:** The first name of the User.
- **lastName:** The last name of the User.
- **email:** The email address of the User.
- **[Other Relevant Fields]:** Any other relevant fields related to Users.

### OrderDTO

#### Fields:

- **UserId:** The ID of the User who placed the order.
- **orderDate:** The date and time when the order was placed.
- **[Other Relevant Fields]:** Any other relevant fields related to orders.

### InvoiceDTO

#### Fields:

- **id:** The unique identifier for the invoice.
- **orderId:** The ID of the order associated with the invoice.
- **invoiceDate:** The date and time when the invoice was generated.
- **[Other Relevant Fields]:** Any other relevant fields related to invoices.

### OrderItemDTO

#### Fields:

- **product:** The product included in the order item.
- **quantity:** The quantity of the product in the order item.
- **[Other Relevant Fields]:** Any other relevant fields related to order items.


[Go Back to Table of Contents](SMS_Architecture_Guide.md)