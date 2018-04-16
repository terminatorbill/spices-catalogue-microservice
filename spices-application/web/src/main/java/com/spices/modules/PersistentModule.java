package com.spices.modules;

import javax.persistence.EntityManagerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.spices.persistence.provider.EntityManagerProvider;
import com.spices.persistence.repository.CategoryRepositoryFacade;
import com.spices.persistence.repository.CategoryRepositoryFacadeImpl;
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
        bind(EntityManagerProvider.class).toInstance(new EntityManagerProvider(entityManagerFactory));

        //Names.bindProperties();
    }
}
