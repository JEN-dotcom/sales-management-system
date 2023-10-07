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
import com.ingryd.sms.model.OrderItemDTO;
import com.ingryd.sms.model.ProductDTO;
import com.ingryd.sms.repository.OrderRepository;
import com.ingryd.sms.repository.ProductRepository;
import com.ingryd.sms.repository.UserRepository;
import com.ingryd.sms.service.OrderService;
import com.ingryd.sms.service.OrderServiceImpl;
import com.ingryd.sms.service.ProductService;
import com.ingryd.sms.service.ProductServiceImpl;

@SpringBootApplication
public class SalesManagementSystemApplication {

	public static void main(String[] args) {

		SpringApplication.run(SalesManagementSystemApplication.class, args);
	}

	@Bean
	CommandLineRunner run(OrderServiceImpl orderService, ProductServiceImpl productService, UserRepository userRepository, ProductRepository productRepository) {
		return args -> {

			ClassLoader classLoader = getClass().getClassLoader();
			ObjectMapper objectMapper = new ObjectMapper();

			File productsFile = new File(classLoader.getResource("data/products.json").getFile());
			ProductDTO[] productDTOs = objectMapper.readValue(productsFile, ProductDTO[].class);

			for (ProductDTO productDTO : productDTOs) {
				productService.createProduct(productDTO);
			}

			User user = User.builder()
					.firstName("Efe")
					.lastName("Okorobie")
					.email("eokoro@gmail.com")
					.build();

			User created =userRepository.save(user);

			File orderItemOneFile = new File(classLoader.getResource("data/orderItemOne.json").getFile());
			OrderItemDTO[] orderItemOneDTO = objectMapper.readValue(orderItemOneFile, OrderItemDTO[].class);
			List<OrderItemDTO> orderItemsOneList = new ArrayList<>();
			for (OrderItemDTO orderItemDTO : orderItemOneDTO) {
				orderItemsOneList.add(orderItemDTO);
			}

			File orderItemTwoFile = new File(classLoader.getResource("data/orderItemTwo.json").getFile());
			OrderItemDTO[] orderItemTwoDTO = objectMapper.readValue(orderItemTwoFile, OrderItemDTO[].class);
			List<OrderItemDTO> orderItemsTwoList = new ArrayList<>();
			for (OrderItemDTO orderItemDTO : orderItemTwoDTO) {
				orderItemsTwoList.add(orderItemDTO);
			}

			File orderItemThreeFile = new File(classLoader.getResource("data/orderItemThree.json").getFile());
			OrderItemDTO[] orderItemThreeDTO = objectMapper.readValue(orderItemThreeFile, OrderItemDTO[].class);
			List<OrderItemDTO> orderItemsThreeList = new ArrayList<>();
			for (OrderItemDTO orderItemDTO : orderItemThreeDTO) {
				orderItemsThreeList.add(orderItemDTO);
			}

			File orderItemFourFile = new File(classLoader.getResource("data/orderItemFour.json").getFile());
			OrderItemDTO[] orderItemFourDTO = objectMapper.readValue(orderItemFourFile, OrderItemDTO[].class);
			List<OrderItemDTO> orderItemsFourList = new ArrayList<>();

			for (OrderItemDTO orderItemDTO : orderItemFourDTO) {
				orderItemsFourList.add(orderItemDTO);
			}

			orderService.createOrder(created, orderItemsOneList);
			orderService.createOrder(created, orderItemsTwoList);
			orderService.createOrder(created, orderItemsThreeList);
			orderService.createOrder(created, orderItemsFourList);

		};
	}

}