//package com.unsa.etf.InventoryAndCatalogService.repositories;
//
//import com.unsa.etf.InventoryAndCatalogService.TestConfig;
//import com.unsa.etf.InventoryAndCatalogService.model.Category;
//import com.unsa.etf.InventoryAndCatalogService.model.Subcategory;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.support.AnnotationConfigContextLoader;
//
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@ContextConfiguration(
//        classes = { TestConfig.class },
//        loader = AnnotationConfigContextLoader.class)
//@DataJpaTest
//public class SubcategoryRepositoryTest {
//    private Category CATEGORY_HELPER;
//
//    @Autowired
//    private TestEntityManager testEntityManager;
//
//    @Autowired
//    private SubcategoryRepository subcategoryRepository;
//
//    @BeforeEach
//    public void __init__(){
//        CATEGORY_HELPER = new Category("helper");
//        testEntityManager.persist(CATEGORY_HELPER);
//        testEntityManager.flush();
//    }
//
//    @Test
//    public void initialStateShouldBeEmpty(){
//        List<Subcategory> foundSubcategories = subcategoryRepository.findAll();
//        assertTrue(foundSubcategories.isEmpty());
//    }
//
//    @Test
//    public void findAllTest(){
//        Subcategory subcategory = new Subcategory("stolice", CATEGORY_HELPER);
//        subcategoryRepository.save(subcategory);
//        List<Subcategory> foundSubcategories = subcategoryRepository.findAll();
//        assertTrue(foundSubcategories.contains(subcategory));
//    }
//
//    @Test
//    public void deleteTest(){
//        Subcategory subcategory1 = new Subcategory("stolice", CATEGORY_HELPER);
//        Subcategory subcategory2 = new Subcategory("stolovi", CATEGORY_HELPER);
//        subcategoryRepository.save(subcategory1);
//        subcategoryRepository.save(subcategory2);
//
//        subcategoryRepository.delete(subcategory1);
//        List<Subcategory> foundSubcategories = subcategoryRepository.findAll();
//        assertTrue(foundSubcategories.contains(subcategory2));
//        assertFalse(foundSubcategories.contains(subcategory1));
//    }
//
//    @Test
//    public void addTest(){
//        Subcategory subcategory = new Subcategory("stolice", CATEGORY_HELPER);
//        assertTrue(subcategoryRepository.findSubcategoriesByName("stolice").isEmpty());
//        subcategoryRepository.save(subcategory);
//        assertTrue(subcategoryRepository.findSubcategoriesByName("stolice").contains(subcategory));
//    }
//}
