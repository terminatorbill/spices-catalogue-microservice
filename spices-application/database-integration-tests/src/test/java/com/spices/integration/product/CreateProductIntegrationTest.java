package com.spices.integration.product;

import static com.spices.common.TestHelpers.generateRandomString;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.spices.domain.Category;
import com.spices.domain.Image;
import com.spices.domain.Media;
import com.spices.domain.Product;
import com.spices.domain.Video;
import com.spices.persistence.provider.EntityManagerProvider;
import com.spices.persistence.repository.CategoryRepository;
import com.spices.persistence.repository.CategoryRepositoryFacade;
import com.spices.persistence.repository.CategoryRepositoryFacadeImpl;
import com.spices.persistence.repository.CategoryRepositoryImpl;
import com.spices.persistence.repository.ProductRepository;
import com.spices.persistence.repository.ProductRepositoryFacade;
import com.spices.persistence.repository.ProductRepositoryFacadeImpl;
import com.spices.persistence.repository.ProductRepositoryImpl;
import com.spices.persistence.util.TransactionManager;

public class CreateProductIntegrationTest {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("catalogueManager");
    private static final EntityManagerProvider ENTITY_MANAGER_PROVIDER = new EntityManagerProvider(ENTITY_MANAGER_FACTORY);
    private final ProductRepository productRepository = new ProductRepositoryImpl();
    private final CategoryRepository categoryRepository = new CategoryRepositoryImpl();
    private final TransactionManager transactionManager = new TransactionManager(ENTITY_MANAGER_PROVIDER);
    private final ProductRepositoryFacade productRepositoryFacade = new ProductRepositoryFacadeImpl(productRepository, transactionManager);
    private final CategoryRepositoryFacade categoryRepositoryFacade = new CategoryRepositoryFacadeImpl(categoryRepository, transactionManager);

    @AfterAll
    public static void tearDown() {
        if (ENTITY_MANAGER_FACTORY.isOpen()) {
            ENTITY_MANAGER_FACTORY.close();
        }
    }

    @DisplayName("should create new products")
    @Test
    public void shouldCreateNewProducts() {
        createCategories();
        List<Long> categoryIds = categoryRepositoryFacade.getCategories().stream().map(Category::getId).collect(Collectors.toList());
        createProducts(categoryIds);
    }

    private void createCategories() {
        Category category1 = new Category(
                null, null, generateRandomString(5), "Foo description", Collections.emptyList()
        );

        Category category2 = new Category(
                null, null, generateRandomString(5), "Bar description", Collections.emptyList()
        );

        List<Category> categories = Lists.newArrayList(category1, category2);

        categoryRepositoryFacade.createCategories(categories);

        Assertions.assertThat(categoryRepositoryFacade.checkAndReturnAnyExistingCategory(categories)).isNotEmpty();
    }

    private void createProducts(List<Long> categoryIds) {
        Product product1 = new Product(
                null,
                generateRandomString(5),
                generateRandomString(7),
                categoryIds,
                1000L,
                new Media(null, null)
        );

        Product product2 = new Product(
                null,
                generateRandomString(5),
                generateRandomString(7),
                categoryIds,
                5000L,
                new Media(
                        Lists.newArrayList(
                                new Image(null, "http://", generateRandomString(5), "png", generateRandomString(5))
                        ),
                        Lists.newArrayList(
                                new Video(null, "http://", generateRandomString(5), "mp4")
                        )
                )
        );

        productRepositoryFacade.createProducts(Lists.newArrayList(product1, product2));

        Assertions.assertThat(productRepositoryFacade.isThereAnyProductThatAlreadyExists(Lists.newArrayList(product1, product2))).isNotEmpty();
    }
}
