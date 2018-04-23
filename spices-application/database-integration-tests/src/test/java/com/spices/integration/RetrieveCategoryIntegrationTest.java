package com.spices.integration;

import static com.spices.common.TestHelpers.generateRandomString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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

public class RetrieveCategoryIntegrationTest {
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

    @DisplayName("should retrieve all the persisted categories")
    @Test
    public void shouldRetrieveAllCategories() {
        Category category = new Category(
                null, null, generateRandomString(5), "Foo description", Collections.emptyList(),
                Lists.newArrayList(
                        new Category(null, null, generateRandomString(5), "Bar description", Collections.emptyList(), Lists.newArrayList(
                                new Category(null, null, generateRandomString(5), "Child Bar description", Collections.emptyList(), Collections.emptyList())
                        ))
                )
        );

        categoryRepositoryFacade.createCategory(category);

        List<Category> actualCategories = categoryRepositoryFacade.getCategories();

        assertThat(actualCategories.size(), is(3));
    }
}
