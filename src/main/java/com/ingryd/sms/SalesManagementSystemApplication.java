package com.ingryd.sms;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.ingryd.sms.entity.User;
import com.ingryd.sms.model.OrderItemDTO;
import com.ingryd.sms.model.ProductDTO;
import com.ingryd.sms.repository.ProductRepository;
import com.ingryd.sms.repository.UserRepository;
import com.ingryd.sms.service.OrderServiceImpl;
import com.ingryd.sms.service.ProductServiceImpl;

@SpringBootApplication
public class SalesManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SalesManagementSystemApplication.class, args);
	}

//	@Bean
//	CommandLineRunner run(OrderServiceImpl orderService, ProductServiceImpl productService, UserRepository userRepository, ProductRepository productRepository) {
//		return args -> {
//
//			ClassLoader classLoader = getClass().getClassLoader();
//			ObjectMapper objectMapper = new ObjectMapper();
//
//			File productsFile = new File(Objects.requireNonNull(classLoader.getResource("data/products.json")).getFile());
//			ProductDTO[] productDTOs = objectMapper.readValue(productsFile, ProductDTO[].class);
//
//			for (ProductDTO productDTO : productDTOs) {
//				productService.createProduct(productDTO);
//			}
//
//			User user = User.builder()
//					.id(1L)
//					.firstName("Efe")
//					.lastName("Okorobie")
//					.email("eokoro@gmail.com")
//					.phoneNo("55757577573")
//					.build();
//
//			User createdUser =userRepository.save(user);
//
//			File orderItemOneFile = new File(Objects.requireNonNull(classLoader.getResource("data/orderItemOne.json")).getFile());
//			OrderItemDTO[] orderItemOneDTO = objectMapper.readValue(orderItemOneFile, OrderItemDTO[].class);
//            List<OrderItemDTO> orderItemsOneDTOList = new ArrayList<>(Arrays.asList(orderItemOneDTO));
//
//			File orderItemTwoFile = new File(Objects.requireNonNull(classLoader.getResource("data/orderItemTwo.json")).getFile());
//			OrderItemDTO[] orderItemTwoDTO = objectMapper.readValue(orderItemTwoFile, OrderItemDTO[].class);
//            List<OrderItemDTO> orderItemsTwoDTOList = new ArrayList<>(Arrays.asList(orderItemTwoDTO));
//
//			File orderItemThreeFile = new File(Objects.requireNonNull(classLoader.getResource("data/orderItemThree.json")).getFile());
//			OrderItemDTO[] orderItemThreeDTO = objectMapper.readValue(orderItemThreeFile, OrderItemDTO[].class);
//            List<OrderItemDTO> orderItemsThreeList = new ArrayList<>(Arrays.asList(orderItemThreeDTO));
//
//			File orderItemFourFile = new File(Objects.requireNonNull(classLoader.getResource("data/orderItemFour.json")).getFile());
//			OrderItemDTO[] orderItemFourDTO = objectMapper.readValue(orderItemFourFile, OrderItemDTO[].class);
//            List<OrderItemDTO> orderItemsFourList = new ArrayList<>(Arrays.asList(orderItemFourDTO));
//
//			orderService.createOrder(createdUser, orderItemsOneDTOList);
//			orderService.createOrder(createdUser, orderItemsTwoDTOList);
//			orderService.createOrder(createdUser, orderItemsThreeList);
//			orderService.createOrder(createdUser, orderItemsFourList);
//		};
//	}

}