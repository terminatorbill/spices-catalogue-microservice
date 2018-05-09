package com.spices.integration.category;

import static com.spices.common.TestHelpers.generateRandomString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;

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
import com.spices.persistence.provider.EntityManagerProvider;
import com.spices.persistence.repository.CategoryRepository;
import com.spices.persistence.repository.CategoryRepositoryFacade;
import com.spices.persistence.repository.CategoryRepositoryFacadeImpl;
import com.spices.persistence.repository.CategoryRepositoryImpl;
import com.spices.persistence.util.TransactionManager;

public class DeleteCategoryIntegrationTest {
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

    @DisplayName("should delete all the provided categories")
    @Test
    public void shouldDeleteAllProvidedCategories() {
        Category category1 = new Category(
                null, null, generateRandomString(5), "Foo description", Collections.emptyList()
        );

        Category category2 = new Category(
                null, null, generateRandomString(5), "Bar description", Collections.emptyList()
        );

        List<Category> categories = Lists.newArrayList(category1, category2);

        categoryRepositoryFacade.createCategories(categories);

        Assertions.assertThat(categoryRepositoryFacade.checkAndReturnAnyExistingCategory(categories)).isNotEmpty();

        categoryRepositoryFacade.deleteCategories(categoryRepositoryFacade.getCategories().stream().map(Category::getId).collect(Collectors.toList()));

        assertThat(categoryRepositoryFacade.getCategories(), is(empty()));
    }
}
