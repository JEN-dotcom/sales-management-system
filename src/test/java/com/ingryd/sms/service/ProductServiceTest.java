package com.ingryd.sms.service;

import com.ingryd.sms.entity.Product;
import com.ingryd.sms.model.ProductDTO;
import com.ingryd.sms.repository.ProductRepository;
import com.ingryd.sms.service.ProductService;
import com.ingryd.sms.service.ProductServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductServiceImpl.class)
public class ProductServiceTest {

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;

    private Product product;

    @Test
    public void testGetAllProducts(){
        when(productRepository.findAll()).thenReturn(
                Stream.of(new Product(1L,"Rice", 350000.00, "naija", 100, "Aba", "local", 25),
                        new Product(2L,"Beans", 350000.00, "naija", 100, "Aba", "local", 25)).collect(Collectors.toList()));

        assertEquals(2, productService.getAllProducts().size());
    }




}

