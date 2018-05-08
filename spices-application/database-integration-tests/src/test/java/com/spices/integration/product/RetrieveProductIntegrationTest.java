package com.spices.integration.product;

import static com.spices.common.TestHelpers.generateRandomString;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.junit.Before;
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

public class RetrieveProductIntegrationTest {
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

    @Before
    public void beforeEachTest() {
        productRepositoryFacade.deleteProducts();
    }

    //TODO: Make it work when delete is implemented
    @DisplayName("should retrieve at most the products for the given page size and page number")
    @Test
    public void shouldRetrieveProducts() {
        createCategories();
        List<Long> categoryIds = categoryRepositoryFacade.getCategories().stream().map(Category::getId).collect(Collectors.toList());
        createProducts(categoryIds);

        int pageNumber = 0;
        int pageSize = 10;

        List<Product> products = productRepositoryFacade.retrieveProducts(pageNumber, pageSize);

        assertThat(products.size(), is(3));

        assertThat(products.get(0).getId(), is(notNullValue()));
        assertThat(products.get(0).getPrice(), is(1000L));
        assertThat(products.get(0).getName(), is(notNullValue()));
        assertThat(products.get(0).getDescription(), is(notNullValue()));
        assertThat(products.get(0).getCategories(), containsInAnyOrder(categoryIds.toArray(new Long[0])));
        assertThat(products.get(0).getMedia().getImages(), is(empty()));
        assertThat(products.get(0).getMedia().getVideos(), is(empty()));

        assertThat(products.get(1).getId(), is(notNullValue()));
        assertThat(products.get(1).getPrice(), is(5000L));
        assertThat(products.get(1).getName(), is(notNullValue()));
        assertThat(products.get(1).getDescription(), is(notNullValue()));
        assertThat(products.get(1).getCategories(), containsInAnyOrder(categoryIds.toArray(new Long[0])));
        assertThat(products.get(1).getMedia().getImages().size(), is(1));
        assertThat(products.get(1).getMedia().getImages().get(0).getId(), is(notNullValue()));
        assertThat(products.get(1).getMedia().getImages().get(0).getUrl(), is("http://"));
        assertThat(products.get(1).getMedia().getImages().get(0).getName(), is(notNullValue()));
        assertThat(products.get(1).getMedia().getImages().get(0).getFormat(), is("png"));
        assertThat(products.get(1).getMedia().getImages().get(0).getCaption(), is(notNullValue()));
        assertThat(products.get(1).getMedia().getVideos().size(), is(1));
        assertThat(products.get(1).getMedia().getVideos().get(0).getId(), is(notNullValue()));
        assertThat(products.get(1).getMedia().getVideos().get(0).getUrl(), is("http://"));
        assertThat(products.get(1).getMedia().getVideos().get(0).getName(), is(notNullValue()));
        assertThat(products.get(1).getMedia().getVideos().get(0).getFormat(), is("mp4"));

        assertThat(products.get(2).getId(), is(notNullValue()));
        assertThat(products.get(2).getPrice(), is(5000L));
        assertThat(products.get(2).getName(), is(notNullValue()));
        assertThat(products.get(2).getDescription(), is(notNullValue()));
        assertThat(products.get(2).getCategories(), containsInAnyOrder(categoryIds.toArray(new Long[0])));
        assertThat(products.get(2).getMedia().getImages().size(), is(1));
        assertThat(products.get(2).getMedia().getImages().get(0).getId(), is(notNullValue()));
        assertThat(products.get(2).getMedia().getImages().get(0).getUrl(), is("http://"));
        assertThat(products.get(2).getMedia().getImages().get(0).getName(), is(notNullValue()));
        assertThat(products.get(2).getMedia().getImages().get(0).getFormat(), is("png"));
        assertThat(products.get(2).getMedia().getImages().get(0).getCaption(), is(notNullValue()));
        assertThat(products.get(2).getMedia().getVideos().size(), is(1));
        assertThat(products.get(2).getMedia().getVideos().get(0).getId(), is(notNullValue()));
        assertThat(products.get(2).getMedia().getVideos().get(0).getUrl(), is("http://"));
        assertThat(products.get(2).getMedia().getVideos().get(0).getName(), is(notNullValue()));
        assertThat(products.get(2).getMedia().getVideos().get(0).getFormat(), is("mp4"));
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

        Product product3 = new Product(
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

        productRepositoryFacade.createProducts(Lists.newArrayList(product1, product2, product3));

        Assertions.assertThat(productRepositoryFacade.isThereAnyProductThatAlreadyExists(Lists.newArrayList(product1, product2, product3))).isNotEmpty();
    }
}
