package com.unsa.etf.InventoryAndCatalogService.repositories;

import com.unsa.etf.InventoryAndCatalogService.TestConfig;
import com.unsa.etf.InventoryAndCatalogService.model.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ContextConfiguration(
        classes = { TestConfig.class },
        loader = AnnotationConfigContextLoader.class)
@DataJpaTest
public class CategoryRepositoryTest {
    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    public void initialStateShouldBeEmpty(){
        List<Category> foundCategory = categoryRepository.findAll();
        assertTrue(foundCategory.isEmpty());
    }

    @Test
    public void findAllTest(){
        Category category = new Category("stolice");
        testEntityManager.persist(category);
        testEntityManager.flush();

        List<Category> foundCategory = categoryRepository.findAll();

        assertTrue(foundCategory.contains(category));
    }

    @Test
    public void deleteTest(){
        Category category = new Category("stolice");
        Category category1 = new Category("stolovi");
        testEntityManager.persist(category);
        testEntityManager.persist(category1);
        testEntityManager.flush();

        categoryRepository.delete(category);
        List<Category> foundCategory = categoryRepository.findAll();
        assertTrue(foundCategory.contains(category1));
        assertFalse(foundCategory.contains(category));
    }

    @Test
    public void addTest(){
        Category category = new Category("stolice");
        assertTrue(categoryRepository.findCategoriesByName("stolice").isEmpty());
        categoryRepository.save(category);
        assertTrue(categoryRepository.findCategoriesByName("stolice").contains(category));
    }
}
