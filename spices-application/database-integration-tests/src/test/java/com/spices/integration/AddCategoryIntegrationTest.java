package com.spices.integration;

import java.security.SecureRandom;
import java.util.Collections;

import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.spices.domain.Category;
import com.spices.persistence.configuration.EntityManagerProvider;
import com.spices.persistence.repository.CategoryRepository;
import com.spices.persistence.repository.CategoryRepositoryFacade;
import com.spices.persistence.repository.CategoryRepositoryFacadeImpl;
import com.spices.persistence.repository.CategoryRepositoryImpl;
import com.spices.persistence.util.TransactionManager;

public class AddCategoryIntegrationTest {
    private static final EntityManagerProvider ENTITY_MANAGER_PROVIDER = new EntityManagerProvider("catalogueManager");
    private final CategoryRepository categoryRepository = new CategoryRepositoryImpl();
    private final TransactionManager transactionManager = new TransactionManager(ENTITY_MANAGER_PROVIDER);
    private final CategoryRepositoryFacade categoryRepositoryFacade = new CategoryRepositoryFacadeImpl(categoryRepository, transactionManager);

    static {
        ENTITY_MANAGER_PROVIDER.start();
    }

    @BeforeAll
    public static void setup() {
        ENTITY_MANAGER_PROVIDER.start();
    }

    @AfterAll
    public static void tearDown() {
        ENTITY_MANAGER_PROVIDER.stop();
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

    private String generateRandomString(int len) {
        String letters = "abcdefghijklmnopqrstyvw";
        SecureRandom rnd = new SecureRandom();

        StringBuilder sb = new StringBuilder(len);

        for (int i = 0; i < len; i++) {
            sb.append(letters.charAt(rnd.nextInt(letters.length())));
        }

        return sb.toString();
    }
}
