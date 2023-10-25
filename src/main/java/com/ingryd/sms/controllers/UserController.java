package com.ingryd.sms.controllers;

import com.ingryd.sms.entity.Order;
import com.ingryd.sms.entity.Product;
import com.ingryd.sms.entity.User;
import com.ingryd.sms.model.OrderItemDTO;
import com.ingryd.sms.service.OrderService;

import com.ingryd.sms.service.ProductService;
import com.ingryd.sms.service.TokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private TokenService tokenService;

    /********************************* PRODUCT *********************************/

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();

        if (!products.isEmpty()) {
            return ResponseEntity.ok(products);
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/product/id/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);

        if (product != null) {
            return ResponseEntity.ok(product);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/product/name/{name}")
    public ResponseEntity<List<Product>> getProductByName(@PathVariable String name) {
        List<Product> product = productService.getProductByName(name);

        if (product != null) {
            return ResponseEntity.ok(product);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/product/category/{category}")
    public ResponseEntity<List<Product>> getProductByCategory(String category) {
        List<Product> product = productService.getProductByCategory(category);
        if (product != null) {
            return ResponseEntity.ok(product);
        }
        return ResponseEntity.notFound().build();
    }

    /********************************* ORDER *********************************/

    @PostMapping("/order")
    public ResponseEntity<Order> createOrder(@RequestHeader("Authorization") String token,
            @RequestBody List<OrderItemDTO> orderItemDTOList) {
        User user = tokenService.extractUserFromToken(token);
        Order createdOrder = orderService.createOrder(user, orderItemDTOList);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }
}
