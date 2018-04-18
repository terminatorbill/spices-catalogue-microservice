package com.spices.integration;

import static com.spices.common.TestHelpers.generateRandomString;

import java.util.Collections;

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

public class AddCategoryIntegrationTest {
    private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("catalogueManager");
    private static final EntityManagerProvider ENTITY_MANAGER_PROVIDER = new EntityManagerProvider(ENTITY_MANAGER_FACTORY);
    private final CategoryRepository categoryRepository = new CategoryRepositoryImpl();
    private final TransactionManager transactionManager = new TransactionManager(ENTITY_MANAGER_PROVIDER);
    private final CategoryRepositoryFacade categoryRepositoryFacade = new CategoryRepositoryFacadeImpl(categoryRepository, transactionManager);

    @AfterAll
    public static void tearDown() {
        if (ENTITY_MANAGER_FACTORY.isOpen()) {
            ENTITY_MANAGER_FACTORY.close();
        }
    }

    @DisplayName("should create a new category without subcategories")
    @Test
    public void shouldCreateNewCategory() {
        Category category = new Category(
            null, null, generateRandomString(5), "Foo description", Collections.emptyList(), Collections.emptyList()
        );

        categoryRepositoryFacade.createCategory(category);

        Assertions.assertThat(categoryRepositoryFacade.checkAndReturnAnyExistingCategory(category)).isNotEmpty();
    }

    @DisplayName("should create a new category with one level of subcategories")
    @Test
    public void shouldCreateNewCategoryWithOneLevelSubCategories() {
        Category category = new Category(
            null, null, generateRandomString(5), "Foo description", Collections.emptyList(),
                Lists.newArrayList(
                    new Category(null, null, generateRandomString(5), "Bar description", Collections.emptyList(), Collections.emptyList())
                )
        );

        categoryRepositoryFacade.createCategory(category);

        Assertions.assertThat(categoryRepositoryFacade.checkAndReturnAnyExistingCategory(category)).isNotEmpty();
        Assertions.assertThat(categoryRepositoryFacade.checkAndReturnAnyExistingCategory(category.getSubCategories().get(0))).isNotEmpty();
    }

    @DisplayName("should create a new category with two levels of subcategories")
    @Test
    public void shouldCreateNewCategoryWithTwoLevelsSubCategories() {
        Category category = new Category(
            null, null, generateRandomString(5), "Foo description", Collections.emptyList(),
            Lists.newArrayList(
                new Category(null, null, generateRandomString(5), "Bar description", Collections.emptyList(), Lists.newArrayList(
                    new Category(null, null, generateRandomString(5), "Child Bar description", Collections.emptyList(), Collections.emptyList())
                ))
            )
        );

        categoryRepositoryFacade.createCategory(category);

        Assertions.assertThat(categoryRepositoryFacade.checkAndReturnAnyExistingCategory(category)).isNotEmpty();
        Assertions.assertThat(categoryRepositoryFacade.checkAndReturnAnyExistingCategory(category.getSubCategories().get(0))).isNotEmpty();
        Assertions.assertThat(categoryRepositoryFacade.checkAndReturnAnyExistingCategory(category.getSubCategories().get(0).getSubCategories().get(0))).isNotEmpty();
    }
}
