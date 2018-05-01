package com.spices.integration;

import static com.spices.common.TestHelpers.generateRandomString;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.spices.domain.Category;
import com.spices.persistence.provider.EntityManagerProvider;
import com.spices.persistence.repository.CategoryRepository;
import com.spices.persistence.repository.CategoryRepositoryFacade;
import com.spices.persistence.repository.CategoryRepositoryFacadeImpl;
import com.spices.persistence.repository.CategoryRepositoryImpl;
import com.spices.persistence.util.TransactionManager;
import com.spices.repository.CategoryTestRepository;
import com.spices.repository.CategoryTestRepositoryImpl;

public class AddCategoryIntegrationTest {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("catalogueManager");
    private static final EntityManagerProvider ENTITY_MANAGER_PROVIDER = new EntityManagerProvider(ENTITY_MANAGER_FACTORY);
    private final CategoryRepository categoryRepository = new CategoryRepositoryImpl();
    private final TransactionManager transactionManager = new TransactionManager(ENTITY_MANAGER_PROVIDER);
    private final CategoryRepositoryFacade categoryRepositoryFacade = new CategoryRepositoryFacadeImpl(categoryRepository, transactionManager);
    private final CategoryTestRepository categoryTestRepository = new CategoryTestRepositoryImpl(transactionManager);

    @AfterAll
    public static void tearDown() {
        if (ENTITY_MANAGER_FACTORY.isOpen()) {
            ENTITY_MANAGER_FACTORY.close();
        }
    }

    @DisplayName("should create a new category")
    @Test
    public void shouldCreateNewCategory() {
        Category category = new Category(
            null, null, generateRandomString(5), "Foo description", Collections.emptyList()
        );

        List<Category> categories = Lists.newArrayList(category);
        categoryRepositoryFacade.createCategories(categories);

        Assertions.assertThat(categoryRepositoryFacade.checkAndReturnAnyExistingCategory(categories)).isNotEmpty();
    }

    @DisplayName("should create 2 categories with one of them being a sub-category of another parent category")
    @Test
    public void shouldCreateNewCategoryWithOneLevelSubCategories() {
        Category category = new Category(
            null, null, generateRandomString(5), "Foo description", Collections.emptyList()
        );

        List<Category> categories = Lists.newArrayList(category);

        categoryRepositoryFacade.createCategories(categories);

        Assertions.assertThat(categoryRepositoryFacade.checkAndReturnAnyExistingCategory(categories)).isNotEmpty();

        Category subCategory = new Category(
                null,
                categoryTestRepository.getCategory(category.getName()).getId(),
                generateRandomString(5),
                generateRandomString(7),
                Collections.emptyList()
        );

        categories = Lists.newArrayList(subCategory);

        categoryRepositoryFacade.createCategories(categories);

        Assertions.assertThat(categoryRepositoryFacade.checkAndReturnAnyExistingCategory(categories)).isNotEmpty();
    }
}
