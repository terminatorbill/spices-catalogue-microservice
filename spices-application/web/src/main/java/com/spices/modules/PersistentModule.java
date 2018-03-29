package com.spices.modules;

import com.google.inject.AbstractModule;
import com.google.inject.Singleton;
import com.spices.persistence.configuration.EntityManagerProvider;
import com.spices.persistence.repository.CategoryRepositoryFacade;
import com.spices.persistence.repository.CategoryRepositoryFacadeImpl;
import com.spices.persistence.util.TransactionManager;

public class PersistentModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(TransactionManager.class).in(Singleton.class);
        bind(CategoryRepositoryFacade.class).to(CategoryRepositoryFacadeImpl.class).in(Singleton.class);
        bind(EntityManagerProvider.class);

        //Names.bindProperties();
    }
}
