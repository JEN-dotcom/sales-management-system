package com.ingryd.sms;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ingryd.sms.entity.Invoice;
import com.ingryd.sms.entity.Order;
import com.ingryd.sms.entity.OrderItem;
import com.ingryd.sms.entity.Product;
import com.ingryd.sms.entity.User;
import com.ingryd.sms.repository.OrderRepository;
import com.ingryd.sms.repository.ProductRepository;
import com.ingryd.sms.repository.UserRepository;

@SpringBootApplication
public class SalesManagementSystemApplication {

	public static void main(String[] args) {

		SpringApplication.run(SalesManagementSystemApplication.class, args);
	}

	@Bean
	CommandLineRunner run(OrderRepository orderRepository, UserRepository userRepository, ProductRepository productRepository) {
		return args -> {

			ClassLoader classLoader = getClass().getClassLoader();
			File file = new File(classLoader.getResource("data/products.json").getFile());

			ObjectMapper objectMapper = new ObjectMapper();
			Product[] products = objectMapper.readValue(file, Product[].class);

            for (Product product : products) {
				
                productRepository.save(product);
			}

			User user = User.builder()
					.firstName("Efe")
					.lastName("Okorobie")
					.email("eokoro@gmail.com")
					.build();

			userRepository.save(user);

			Invoice invoice = new Invoice();

			List<OrderItem> orderItemsList = new ArrayList<>();

			Order order = Order.builder()
					.invoice(invoice)
					.user(userRepository.findById(1L).orElseThrow(() -> new RuntimeException("no user")))
					.orderItems(orderItemsList)
					.build();

			orderRepository.save(order);

			// User admin = new ApplicationUser("admin", passwordEncoder.encode("password"),
			// Orders);

			// userRepository.save(admin);

		};
	}

}