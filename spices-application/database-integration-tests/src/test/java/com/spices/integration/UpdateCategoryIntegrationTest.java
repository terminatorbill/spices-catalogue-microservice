package com.spices.integration;

import static com.spices.common.TestHelpers.generateRandomString;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.junit.Assert.assertThat;

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

public class UpdateCategoryIntegrationTest {
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

    @DisplayName("should update all the provided categories")
    @Test
    public void shouldUpdateCategories() {
        Category category1 = new Category(
                null, null, generateRandomString(5), "Foo description", Collections.emptyList(), Collections.emptyList()
        );

        Category category2 = new Category(
                null, null, generateRandomString(5), "Bar description", Collections.emptyList(), Collections.emptyList()
        );

        categoryRepositoryFacade.createCategory(category1);
        categoryRepositoryFacade.createCategory(category2);

        Assertions.assertThat(categoryRepositoryFacade.checkAndReturnAnyExistingCategory(category1)).isNotEmpty();
        Assertions.assertThat(categoryRepositoryFacade.checkAndReturnAnyExistingCategory(category2)).isNotEmpty();

        List<Category> categoriesToUpdate = Lists.newArrayList(
                new Category(categoryTestRepository.getCategory(category1.getName()).getId(), null, category1.getName(), "Updated description", category1.getProducts(), category1.getSubCategories()),
                new Category(categoryTestRepository.getCategory(category2.getName()).getId(), null, "Updated name", "Updated description", category2.getProducts(), category2.getSubCategories())
        );

        categoryRepositoryFacade.updateCategories(categoriesToUpdate);

        category1 = categoryTestRepository.getCategory(categoriesToUpdate.get(0).getName());
        category2 = categoryTestRepository.getCategory(categoriesToUpdate.get(1).getName());
        assertThat(category1.getName(), is(category1.getName()));
        assertThat(category1.getParentCategoryId(), is(nullValue()));
        assertThat(category1.getId(), is(notNullValue()));
        assertThat(category1.getDescription(), is("Updated description"));
        assertThat(category1.getSubCategories(), is(empty()));
        assertThat(category1.getProducts(), is(empty()));

        assertThat(category2.getName(), is("Updated name"));
        assertThat(category2.getParentCategoryId(), is(nullValue()));
        assertThat(category2.getId(), is(notNullValue()));
        assertThat(category2.getDescription(), is("Updated description"));
        assertThat(category2.getSubCategories(), is(empty()));
        assertThat(category2.getProducts(), is(empty()));
    }
}
