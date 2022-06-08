package com.unsa.etf.InventoryAndCatalogService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class InventoryAndCatalogServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryAndCatalogServiceApplication.class, args);
    }

//	@Bean
//	CommandLineRunner commandLineRunner (CategoryRepository categoryRepository, ProductImagesRepository productImagesRepository, ProductRepository productRepository, SubcategoryRepository subcategoryRepository) {
//
//		return args -> {
//
//			Category category = new Category("spavaca soba");
//			Category category1 = new Category("dnevna soba");
//			categoryRepository.saveAll(Arrays.asList(category, category1));
//
//			Subcategory subcategory = new Subcategory("prekrivaci", category);
//			Subcategory subcategory1 = new Subcategory("kreveti", category);
//			Subcategory subcategory2 = new Subcategory("fotelje", category1);
//			subcategoryRepository.saveAll(Arrays.asList(subcategory, subcategory1, subcategory2));
//
//			Product product = new Product("jastucnica MIA", "veoma dobra jastucnica", 25, 4.5f, category, subcategory, 0, 0);
//			Product product1 = new Product("francuski lezaj PIERRE", "veoma dobar lezaj", 10, 700.99f, category, subcategory1, 0, 5);
//			Product product2 = new Product("kozna fotelja MIRNA", "veoma dobra fotelja", 35, 330.33f, category1, subcategory2, 5, 5);
//			productRepository.saveAll(Arrays.asList(product, product1, product2));
//
//			ProductImage productImages = new ProductImage(product);
//			productImagesRepository.saveAll(Arrays.asList(productImages));
//		};
//	}

}
