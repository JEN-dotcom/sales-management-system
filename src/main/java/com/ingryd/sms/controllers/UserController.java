package com.ingryd.sms.controllers;

import com.ingryd.sms.entity.Order;
import com.ingryd.sms.entity.Product;
import com.ingryd.sms.entity.User;
import com.ingryd.sms.model.OrderItemDTO;
import com.ingryd.sms.repository.OrderRepository;
import com.ingryd.sms.repository.ProductRepository;
import com.ingryd.sms.repository.UserRepository;
import com.ingryd.sms.service.OrderServiceImpl;
import com.ingryd.sms.service.ProductServiceImpl;
import com.ingryd.sms.service.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderServiceImpl orderService;


//    @GetMapping("/products")
//    public List<Product> getAllProducts() {
//        List<Product> products = productService.getAllProducts();
//        return products;
//    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAllProducts();

        if (!products.isEmpty()) {
            return ResponseEntity.ok(products);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

//    @GetMapping("/products/{id}")
//    public Product getProductById(@PathVariable Long id) {
//        return productService.getProductById(id);
//    }
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id) {
        Product product = productService.getProductById(id);

        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    @GetMapping("/products/{name}")
//    public List<Product> getProductByName(@PathVariable String name) {
//        return productService.getProductByName(name);
//    }
    @GetMapping("/{name}")
    public ResponseEntity<List<Product>> getProductByName(@PathVariable String name) {
        List<Product> product = productService.getProductByName(name);

        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/orders")
    public ResponseEntity<Order> createOrder(@Valid User user, @RequestBody List<OrderItemDTO> orderItemDTOList){

        Order createdOrder = orderService.createOrder(user, orderItemDTOList);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }


}
