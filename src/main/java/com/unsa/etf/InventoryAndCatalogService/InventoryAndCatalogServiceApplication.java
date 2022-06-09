package com.unsa.etf.InventoryAndCatalogService;

import com.unsa.etf.InventoryAndCatalogService.model.Category;
import com.unsa.etf.InventoryAndCatalogService.model.Product;
import com.unsa.etf.InventoryAndCatalogService.model.ProductImage;
import com.unsa.etf.InventoryAndCatalogService.model.Subcategory;
import com.unsa.etf.InventoryAndCatalogService.repositories.CategoryRepository;
import com.unsa.etf.InventoryAndCatalogService.repositories.ProductImagesRepository;
import com.unsa.etf.InventoryAndCatalogService.repositories.ProductRepository;
import com.unsa.etf.InventoryAndCatalogService.repositories.SubcategoryRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;

@EnableDiscoveryClient
@SpringBootApplication
public class InventoryAndCatalogServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryAndCatalogServiceApplication.class, args);
    }

//	@Bean
//    CommandLineRunner commandLineRunner (CategoryRepository categoryRepository, ProductImagesRepository productImagesRepository, ProductRepository productRepository, SubcategoryRepository subcategoryRepository) {
//
//		return args -> {
//
//			Category category = new Category("Living room");
//			Category category1 = new Category("Dining room");
//            Category category2 = new Category("Bedroom");
//            Category category3 = new Category("Kitchen");
//            Category category4 = new Category("Bathroom");
//			categoryRepository.saveAll(Arrays.asList(category, category1, category2, category3, category4));
//
//			Subcategory subcategory = new Subcategory("Sofa", category);
//			Subcategory subcategory1 = new Subcategory("Coffee table", category);
//            Subcategory subcategory2 = new Subcategory("Corner sofa", category);
//			Subcategory subcategory3 = new Subcategory("Dining table", category1);
//            Subcategory subcategory4 = new Subcategory("Chair", category1);
//            Subcategory subcategory5 = new Subcategory("Mattress", category2);
//            Subcategory subcategory6 = new Subcategory("Wardrobe", category2);
//			subcategoryRepository.saveAll(Arrays.asList(subcategory, subcategory1, subcategory2, subcategory3, subcategory4, subcategory5, subcategory6));
//
//			Product product = new Product("Alpha Marble Top Coffee Table Square",
//                    "Stylish square coffee table supported by matt brass effect geometric criss cross legs and a heavy real marble top with natural grey marble streaks.",
//                    25, 4.5f, category, subcategory1, 0, 0);
//			Product product1 = new Product("Artisan Oval Coffee Table",
//                    "Beautifully crafted from recycled pine wood, the Artisan oval coffee table will become the focal point of any room.",
//                    10, 700.99f, category, subcategory1, 0, 5);
//			Product product2 = new Product("Faro Corner Sofa",
//                    "The Faro Corner Sofa is a great addition to the home. It's generous chaise section is perfect for relaxing with your feet up.",
//                    35, 330.33f, category, subcategory2, 5, 5);
//			productRepository.saveAll(Arrays.asList(product, product1, product2));
//
////			ProductImage productImages = new ProductImage(product);
////			productImagesRepository.saveAll(Arrays.asList(productImages));
//		};
//	}

}
