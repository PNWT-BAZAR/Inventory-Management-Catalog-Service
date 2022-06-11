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

import java.util.ArrayList;
import java.util.Arrays;

@EnableDiscoveryClient
@SpringBootApplication
public class InventoryAndCatalogServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryAndCatalogServiceApplication.class, args);
    }

	@Bean
    CommandLineRunner commandLineRunner (CategoryRepository categoryRepository, ProductImagesRepository productImagesRepository, ProductRepository productRepository, SubcategoryRepository subcategoryRepository) {

		return args -> {

			Category category = new Category("Living room");
			Category category1 = new Category("Dining room");
            Category category2 = new Category("Bedroom");
            Category category3 = new Category("Office");
            Category category4 = new Category("Bathroom");
			categoryRepository.saveAll(Arrays.asList(category, category1, category2, category3, category4));

			//Living room subcategories
			Subcategory subcategory = new Subcategory("Sofa", category);
			Subcategory subcategory1 = new Subcategory("Coffee table", category);
            Subcategory subcategory2 = new Subcategory("Corner sofa", category);
			//Dining room subcategories
			Subcategory subcategory3 = new Subcategory("Dining table", category1);

            Subcategory subcategory4 = new Subcategory("Dining chair", category1);
			//Bedroom subcategories
            Subcategory subcategory5 = new Subcategory("Mattress", category2);
            Subcategory subcategory6 = new Subcategory("Wardrobe", category2);
			Subcategory subcategory7 = new Subcategory("Bedside table", category2);
			//Office subcategories
			Subcategory subcategory8 = new Subcategory("Chair", category3);
			Subcategory subcategory9 = new Subcategory("Desk", category3);
			//Bathroom subcategories
			Subcategory subcategory11 = new Subcategory("Mirror", category4);
			Subcategory subcategory12 = new Subcategory("Cabinet", category4);
			subcategoryRepository.saveAll(Arrays.asList(subcategory, subcategory1, subcategory2, subcategory3, subcategory4, subcategory5,
														subcategory6, subcategory7, subcategory8, subcategory9, subcategory11,
														subcategory12));


//			ProductImage productImage0_1 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/8291.jpg", product);
//			ProductImage productImage0_2 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/8291b.jpg", product);

			Product product1 = new Product("Artisan Oval Coffee Table",
                    "Beautifully crafted from recycled pine wood, the Artisan oval coffee table will become the focal point of any room.",
                    10, 700.99f, category, subcategory1, 0, 5);
			ProductImage productImage1_1 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/8006.jpg", product1);
			ProductImage productImage1_2 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/8006b.jpg", product1);

			Product product2 = new Product("Faro Corner Sofa",
                    "The Faro Corner Sofa is a great addition to the home. It's generous chaise section is perfect for relaxing with your feet up.",
                    35, 330.33f, category, subcategory2, 5, 5);
			ProductImage productImage2_1 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/8837b.jpg", product2);
			ProductImage productImage2_2 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/8837.jpg", product2);

			Product product3 = new Product("Faro Corner SofaIndustrial Step Coffee Table",
					"With it's solid timber tops and great split level design, the step coffee table is a great addition to the living room. Supported by powder coated black metal legs.",
					4, 379.00f, category, subcategory1, 12, 3);
			ProductImage productImage3_1 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/8971.jpg", product3);
			ProductImage productImage3_2 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/8971c.jpg", product3);
			ProductImage productImage3_3 = new ProductImage("https://www.woods-furniture.co.uk/images/products/large/8971d.jpg", product3);

			Product product4 = new Product("Chase Dining Chair - Teal Velvet (Set of 2)",
					"The Chase teal velvet dining chairs add a stylish touch to dining. Featuring a slightly bucketed seat with quilted velvet upholstery, and industrial style black powder coated metal legs for support.",
					4, 450.00f, category1, subcategory4, 20, 6);
			ProductImage productImage4_1 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/8891.jpg", product4);
			ProductImage productImage4_2 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/8891b.jpg", product4);
			ProductImage productImage4_3 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/8891h.jpg", product4);

			Product product5 = new Product("Industrial Dining Table",
					"The Industrial dining table has a robust solid wood top with a simple, metal crossed leg base. Available in 2 different sizes for you to choose from, this industrial dining table will add modern, industrial style to your home.",
					7, 750.00f, category1, subcategory3, 13, 3);
			ProductImage productImage5_1 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/8147.jpg", product5);
			ProductImage productImage5_2 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/8147c.jpg", product5);

			Product product6 = new Product("Ridley Round Dining Table 120cm",
					"The Ridley Dining Table is a chunky lesson in industrial styling, with a big star formation base made from metal coated in a grey powder paint and a thick top wrapped in a textured oak effect foil. It has a lovely feel and not being real wood is easy to keep clean and is made to last.",					2, 249.99f, category1, subcategory3, 13, 3);
			ProductImage productImage6_1 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/8614.jpg", product6);
			ProductImage productImage6_2 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/8614b.jpg", product6);
			ProductImage productImage6_3 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/8614c.jpg", product6);

			Product product7 = new Product("Hudson Medium Oak Extendable Table 1.8m - 2.2m",
					"The Hudson Collection is built from hardwood and oak veneers and is designed for real life. The pieces are very sturdy and weighty, and not least this medium size oak extendable dining table. Extending from 1.8m long to a crowd pleasing 2.2m with a butterfly method.",
					4, 1099.99f, category1, subcategory3, 15, 3);
			ProductImage productImage7_1 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/8992.jpg", product7);
			ProductImage productImage7_2 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/8992b.jpg", product7);
			ProductImage productImage7_3 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/8992c.jpg", product7);
			ProductImage productImage7_4 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/8992g.jpg", product7);

			Product product8 = new Product("Didcot Large Bedside Cabinet",
					"Beautifully painted in classic white, the Didcot large white bedside cabinet is a timeless piece of bedroom furniture. This white bedside cabinet features 2 drawers with dark contrasting handles, it will look truly stylish in any bedroom.",
					10, 103.99f, category2, subcategory7, 14, 3);
			ProductImage productImage8_1 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/7286.jpg", product8);
			ProductImage productImage8_2 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/7286c.jpg", product8);
			ProductImage productImage8_3 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/7286e.jpg", product8);

			Product product9 = new Product("Hudson Bedside Cabinet",
					"The Hudson Bedside Chest is a small chest, perfect for use where space around the bed is at a premium. It features three small drawers with cup handles and is crafted from solid oak and oak veneers. The corners are smoothed into curves and this little piece really lets the natural wood tones shine through.",
					11, 199.99f, category2, subcategory7, 14, 3);
			ProductImage productImage9_1 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/8454.jpg", product9);
			ProductImage productImage9_2 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/8454b.jpg", product9);

			Product product10 = new Product("Nairn 1 Drawer Wardrobe",
					"The Nairn Wardrobe, built from solid oak with oak veneers and featuring a large hanging space and internal shelf is an absolutely gorgeous wardrobe. The two doors fronted by two contrasting knobs and this is carried through to the exposed finger joins on the front of the single draw at the base of the wardrobe.",
					7, 799.00f, category2, subcategory6, 28, 6);
			ProductImage productImage10_1 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/8416.jpg", product10);
			ProductImage productImage10_2 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/8416c.jpg", product10);
			ProductImage productImage10_3 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/8416d.jpg", product10);
			ProductImage productImage10_4 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/8416e.jpg", product10);

			Product product11 = new Product("Hypnos Orthocare Supreme Mattress",
					"With a naturally firmer feel, the Orthocare Supreme features a luxury layer of soft bamboo, which is natural, plant-based and breathable, and organic cotton and flax helping keep sleepers cool at night.",
					30, 820.00f, category2, subcategory5, 30, 6);
			ProductImage productImage11_1 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/9238.jpg", product11);
			ProductImage productImage11_2 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/9238h.jpg",product11);

			Product product12 = new Product("Walbury Pillow Top Mattress",
					"With a naturally firmer feel, the Orthocare Supreme features a luxury layer of soft bamboo, which is natural, plant-based and breathable, and organic cotton and flax helping keep sleepers cool at night.",
					30, 1250.00f, category2, subcategory5, 27, 6);
			ProductImage productImage12_1 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/9550.jpg", product12);
			ProductImage productImage12_2 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/9550b.jpg",product12);

			Product product13 = new Product("White Linen Tower with 2 Drawers",
					"Short on storage space in small bathrooms? This linen tower comes to help. With a slim and compact design, it helps you organize all your toiletries and bath items while taking up a little space.",
					30, 450.00f, category4, subcategory12, 27, 8);
			ProductImage productImage13_1 = new ProductImage("https://static.songmics.com/fit-in/750x750/image/Product/UBBC66WT/Tall-Bathroom-Cabinet-UBBC66WT-3.jpg", product13);
			ProductImage productImage13_2 = new ProductImage("https://static.songmics.com/fit-in/750x750/image/Product/UBBC66WT/Tall-Bathroom-Cabinet-UBBC66WT-2.jpg",product13);

			Product product14 = new Product("Fiddock Champagne Mirror",
					"The modern Fiddock mirror is the perfect size for checking your appearance whilst bringing more light into the room and finished with a wide angled champagne coloured frame.",
					35, 299.99f, category4, subcategory11, 30, 9);
			ProductImage productImage14_1 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/7610.jpg", product14);
			ProductImage productImage14_2 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/7610b.jpg",product14);

			Product product15 = new Product("Richmond Wall Mirror Grey",
					"The Richmond style is an understated and simple classical look featuring a curved top to the frame and a thin profile. With distressed paint work and Georgian proportions the Richmond wall mirror is perfect for any space.",
					35, 79.99f, category4, subcategory11, 31, 9);
			ProductImage productImage15_1 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/8626.jpg", product15);
			ProductImage productImage15_2 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/8626b.jpg",product15);
			ProductImage productImage15_3 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/8626c.jpg",product15);

			Product product16 = new Product("White Free Standing Cabinet",
					"Ample storage space provided by an open shelf and a double shutter door cabinet with an adjustable shelf (3 heights available), you can remove the shelf for larger items; perfect as shoe cabinet, console cabinet, storage cabinet and etc. With a slim and compact design, it helps you organize all your toiletries and bath items while taking up a little space.",
					17, 210.00f, category4, subcategory12, 20, 6);
			ProductImage productImage16_1 = new ProductImage("https://static.songmics.com/fit-in/750x750/image/Product/UBBC40WT/BBC40WT-3.jpg", product16);
			ProductImage productImage16_2 = new ProductImage("https://static.songmics.com/fit-in/750x750/image/Product/UBBC40WT/BBC40WT-2.jpg",product16);
			ProductImage productImage16_3 = new ProductImage("https://static.songmics.com/fit-in/750x750/image/Product/UBBC40WT/BBC40WT-1.jpg",product16);

			Product product17 = new Product("Retro Desk Chair - Brandy",
					"Ample storage space provided by an open shelf and a double shutter door cabinet with an adjustable shelf (3 heights available), you can remove the shelf for larger items; perfect as shoe cabinet, console cabinet, storage cabinet and etc. With a slim and compact design, it helps you organize all your toiletries and bath items while taking up a little space.",
					17, 159.99f, category3, subcategory8, 29, 6);
			ProductImage productImage17_1 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/9358.jpg", product17);
			ProductImage productImage17_2 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/9358e.jpg",product17);
			ProductImage productImage17_3 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/9358f.jpg",product17);

			Product product18 = new Product("Orion Office Chair",
					"The Orion Office Chair is a very comfortable modern office chair, with gas lift height adjustment, and double padding on seat and back. The chair is upholstered in a gorgously soft grey faux leather and of course, it has wheels!",
					17, 299.99f, category3, subcategory8, 26, 6);
			ProductImage productImage18_1 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/8909.jpg", product18);
			ProductImage productImage18_2 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/8909b.jpg",product18);
			ProductImage productImage18_3 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/8909g.jpg",product18);

			Product product19 = new Product("Larris Desk",
					"A fun addition for your home study space, the Larris desk combines a deep, warm walnut coloured wood with clear dark and light grey panels. The back of the unit lifts up to allow you to tuck away any cables rather than leaving them to hang out of the back and the two front drawers ensures you have storage space to keep the working area clutter free when you're not using the desk.",
					17, 219.99f, category3, subcategory8, 30, 6);
			ProductImage productImage19_1 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/8196.jpg", product19);
			ProductImage productImage19_2 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/8196b.jpg",product19);
			ProductImage productImage19_3 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/8196d.jpg",product19);

			Product product20 = new Product("Manis Desk",
					"An excellent choice of desk for your home study space or even as a somewhere to sit and study to one side. Its grey metal legs complement the white wash of the wood beautifully giving it a lighter, airy feel.",
					17, 219.99f, category3, subcategory8, 27, 6);
			ProductImage productImage20_1 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/8197.jpg", product20);
			ProductImage productImage20_2 = new ProductImage("https://www.woods-furniture.co.uk/images/products/standard/8197b.jpg",product20);

			productRepository.saveAll(Arrays.asList(product1, product2, product3, product4, product5, product6, product7, product8, product9,
													product10, product11, product12, product13, product14, product15, product16, product17, product18,
													product19, product20));
			productImagesRepository.saveAll(Arrays.asList(productImage1_1, productImage1_2,
														productImage2_1, productImage2_2,
														productImage3_1, productImage3_2, productImage3_3,
														productImage4_1, productImage4_2, productImage4_3,
														productImage5_1, productImage5_2,
														productImage6_1, productImage6_2, productImage6_3,
														productImage7_1, productImage7_2, productImage7_3, productImage7_4,
														productImage8_1, productImage8_2, productImage8_3,
														productImage9_1, productImage9_2,
														productImage10_1, productImage10_2, productImage10_3, productImage10_4,
														productImage11_1, productImage11_2,
														productImage12_1, productImage12_2,
														productImage13_1, productImage13_2,
														productImage14_1, productImage14_2,
														productImage15_1, productImage15_2, productImage15_3,
														productImage16_1, productImage16_2, productImage16_3,
														productImage17_1, productImage17_2, productImage17_3,
														productImage18_1, productImage18_2, productImage18_3,
														productImage19_1, productImage19_2, productImage19_3,
														productImage20_1, productImage20_2));
		};
	}

}
