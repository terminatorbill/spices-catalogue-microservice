package com.spices.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.spices.api.CategoryAdminApi;
import com.spices.api.CategoryApi;
import com.spices.api.ProductApi;
import com.spices.api.converter.CategoryCreationRequestToCategoryConverter;
import com.spices.api.converter.CategoryToCategoryResponseDtoConverter;
import com.spices.api.converter.CategoryUpdateRequestToCategoryConverter;
import com.spices.api.converter.ProductRequestDtoToProductConverter;
import com.spices.service.CategoryService;
import com.spices.service.CategoryServiceImpl;

public class AppModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(CategoryApi.class);
        bind(CategoryAdminApi.class);
        bind(ProductApi.class);

        bind(CategoryCreationRequestToCategoryConverter.class);
        bind(CategoryToCategoryResponseDtoConverter.class);
        bind(CategoryUpdateRequestToCategoryConverter.class);
        bind(ProductRequestDtoToProductConverter.class);

        bind(CategoryService.class)
                .to(CategoryServiceImpl.class)
                .in(Singleton.class);


    }
}
