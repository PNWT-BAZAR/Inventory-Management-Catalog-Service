package com.unsa.etf.InventoryAndCatalogService.repositories;


import com.unsa.etf.InventoryAndCatalogService.TestConfig;
import com.unsa.etf.InventoryAndCatalogService.model.Category;
import com.unsa.etf.InventoryAndCatalogService.model.Product;
import com.unsa.etf.InventoryAndCatalogService.model.Subcategory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.List;

import static com.unsa.etf.InventoryAndCatalogService.utils.InventoryTestMocks.getProductMock;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ContextConfiguration(
        classes = { TestConfig.class },
        loader = AnnotationConfigContextLoader.class)
@DataJpaTest
public class ProductRepositoryTest {
    private Category CATEGORY_HELPER;
    private Product PRODUCT_MOCK;
    private Subcategory SUBCATEGORY_HELPER ;

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    public void __init__(){
        CATEGORY_HELPER = new Category("helper");
        SUBCATEGORY_HELPER = new Subcategory("helper", CATEGORY_HELPER);
        testEntityManager.persist(CATEGORY_HELPER);
        testEntityManager.persist(SUBCATEGORY_HELPER);

        testEntityManager.flush();
        PRODUCT_MOCK = Product.builder()
                .name("test")
                .description("test")
                .category(CATEGORY_HELPER)
                .subcategory(SUBCATEGORY_HELPER)
                .build();
    }

    @Test
    public void initialStateShouldBeEmpty(){
        List<Product> foundProducts = productRepository.findAll();
        assertTrue(foundProducts.isEmpty());
    }

    @Test
    public void findAllTest(){

        productRepository.save(PRODUCT_MOCK);
        List<Product> foundProducts = productRepository.findAll();
        assertTrue(foundProducts.contains(PRODUCT_MOCK));
    }

    @Test
    public void deleteTest(){
        Product product2 = getProductMock("test2","test2", CATEGORY_HELPER);
        productRepository.save(PRODUCT_MOCK);
        productRepository.save(product2);
        productRepository.delete(PRODUCT_MOCK);
        List<Product> foundProducts = productRepository.findAll();
        assertTrue(foundProducts.contains(product2));
        assertFalse(foundProducts.contains(PRODUCT_MOCK));
    }

    @Test
    public void addTest(){
        assertTrue(productRepository.findProductsByName("test").isEmpty());
        productRepository.save(PRODUCT_MOCK);
        assertTrue(productRepository.findProductsByName("test").contains(PRODUCT_MOCK));
    }
}
