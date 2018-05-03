package com.spices.modules;

import javax.persistence.EntityManagerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
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

public class PersistentModule extends AbstractModule {
    private final EntityManagerFactory entityManagerFactory;

    public PersistentModule(EntityManagerFactory entityManagerFactory) {

        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    protected void configure() {
        bind(TransactionManager.class).in(Singleton.class);
        bind(CategoryRepositoryFacade.class).to(CategoryRepositoryFacadeImpl.class).in(Singleton.class);
        bind(ProductRepositoryFacade.class).to(ProductRepositoryFacadeImpl.class).in(Singleton.class);
        bind(CategoryRepository.class)
                .to(CategoryRepositoryImpl.class)
                .in(Singleton.class);
        bind(ProductRepository.class)
                .to(ProductRepositoryImpl.class)
                .in(Singleton.class);
        bind(EntityManagerProvider.class).toInstance(new EntityManagerProvider(entityManagerFactory));

        //Names.bindProperties();
    }
}
