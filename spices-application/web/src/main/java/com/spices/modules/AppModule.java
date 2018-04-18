package com.spices.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.spices.api.CategoryApi;
import com.spices.api.converter.CategoryCreationRequestToCategoryConverter;
import com.spices.persistence.repository.CategoryRepository;
import com.spices.persistence.repository.CategoryRepositoryImpl;
import com.spices.service.CategoryService;
import com.spices.service.CategoryServiceImpl;

public class AppModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(CategoryApi.class);

        bind(CategoryCreationRequestToCategoryConverter.class);

        bind(CategoryService.class)
                .to(CategoryServiceImpl.class)
                .in(Singleton.class);
    }
}
