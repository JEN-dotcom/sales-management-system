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
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ProductServiceImpl.class)
public class ProductServiceTest {

    @MockBean
    private ProductRepository productRepository;

    @Autowired
    private ProductService productService;


    @Test
    public void testGetAllProducts(){
        when(productRepository.findAll()).thenReturn(
                Stream.of(new Product(1L,"Rice", 350000.00, "naija", 100, "Aba", "local", 25),
                        new Product(2L,"Beans", 350000.00, "naija", 100, "Aba", "local", 25)).collect(Collectors.toList()));

        assertEquals(2, productService.getAllProducts().size());
    }

    @Test
    public void testCreateProduct(){
        ProductDTO productDTO = new ProductDTO("Rice", 350000.00, "naija", 100, "Aba", "local", 25);
        Product product = Product
                .builder()
                .id(1L)
                .brand(productDTO.getBrand())
                .category(productDTO.getCategory())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .stock(productDTO.getStock())
                .discount(productDTO.getDiscount())
                .name(productDTO.getName())
                .build();

        when(productRepository.save(any(Product.class))).thenReturn(product);
        Product createdProduct = productService.createProduct(productDTO);
        assertEquals("Rice", createdProduct.getName());
        assertEquals(product, createdProduct);

    }

    @Test
    public void testGetProductById(){
        long productId = 1L;
        Product product = new Product(1L,"Rice", 350000.00, "naija", 100, "Aba", "local", 25);
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(productId);
        assertEquals(productId, result.getId().longValue());
    }

    @Test
    public void testGetProductByCategory() {
        String category = "local";
        when(productRepository.findByCategory(category)).thenReturn(
                Stream.of(
                                new Product(1L, "Rice", 350000.00, "naija", 100, "Aba", "local", 25),
                                new Product(2L, "Beans", 450000.00, "naija", 50, "Lagos", "local", 30))
                        .collect(Collectors.toList()));

        List<Product> result = productService.getProductByCategory(category);

        assertEquals(2, result.size());
        assertEquals(category, result.get(0).getCategory());
        assertEquals(category, result.get(1).getCategory());
    }

    @Test
    public void testGetProductByName() {
        String name = "Rice";
        when(productRepository.findByName(name)).thenReturn(
                Stream.of(
                        new Product(1L, "Rice", 350000.00, "naija", 100, "Aba", "local", 25),
                        new Product(2L, "Rice", 450000.00, "naija", 50, "Lagos", "local", 30))
                        .collect(Collectors.toList()));

        List<Product> products = productService.getProductByName(name);

        assertEquals(2, products.size());
        assertEquals(name, products.get(0).getName());
        assertEquals(name, products.get(1).getName());
    }

    @Test
    public void testUpdateProduct(){
        long productId = 1L;
        Product product = new Product(1L,"Rice", 350000.00, "naija", 100, "Aba", "local", 25);
        ProductDTO productDTO = new ProductDTO("Beans", 350000.00, "naija", 100, "Aba", "foreign", 25);
        Product newProduct = Product
                .builder()
                .id(1L)
                .brand(productDTO.getBrand())
                .category(productDTO.getCategory())
                .description(productDTO.getDescription())
                .price(productDTO.getPrice())
                .stock(productDTO.getStock())
                .discount(productDTO.getDiscount())
                .name(productDTO.getName())
                .build();

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(newProduct);

        Product updatedProduct = productService.updateProduct(productId, productDTO);

        verify(productRepository, times(1)).save(any(Product.class));

        assertEquals(newProduct, updatedProduct);
        assertEquals(productDTO.getName(), updatedProduct.getName());
        System.out.println(product.getName());
        System.out.println(updatedProduct.getName());
//        assertNotEquals(product.getName(), updatedProduct.getName());

    }

    @Test
    public void testDeleteProduct(){
        long productId = 1L;
        doNothing().when(productRepository).deleteById(productId);
        productService.deleteProduct(productId);
        verify(productRepository, times(1)).deleteById(productId);
    }

}

