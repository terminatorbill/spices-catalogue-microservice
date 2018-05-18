package com.spices.modules;

import javax.persistence.EntityManagerFactory;

import org.hibernate.jpa.HibernatePersistenceProvider;

import com.google.common.collect.ImmutableMap;
import com.google.inject.Singleton;
import com.spices.configuration.AppConfiguration;
import com.spices.configuration.HibernateConfiguration;
import com.spices.managedServices.EntityManagerFactoryManagedService;
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

import ru.vyarus.dropwizard.guice.module.support.DropwizardAwareModule;

public class PersistentModule extends DropwizardAwareModule<AppConfiguration> {

    @Override
    protected void configure() {

        HibernateConfiguration hibernateConfiguration = configuration().getHibernateConfiguration();
        EntityManagerFactory entityManagerFactory = new HibernatePersistenceProvider().createEntityManagerFactory(
                hibernateConfiguration.getPersistenceUnit(),
                ImmutableMap.<String, Object> builder()
                        .put("javax.persistence.jdbc.driver", hibernateConfiguration.getJdbcDriver())
                        .put("javax.persistence.jdbc.url", hibernateConfiguration.getJdbcUrl())
                        .put("javax.persistence.jdbc.user", hibernateConfiguration.getJdbcUser())
                        .put("javax.persistence.jdbc.password", hibernateConfiguration.getJdbcPassword())
                        .put("hibernate.dialect", hibernateConfiguration.getHibernateDialect())
                        .put("hibernate.hbm2ddl.auto", hibernateConfiguration.getHbmAuto())
                        .put("hibernate.show_sql", hibernateConfiguration.isShowSql())
                        .put("hibernate.format_sql", hibernateConfiguration.isFormatSql())
                        .put("org.hibernate.flushMode", hibernateConfiguration.getFlushMode())
                        .build()
        );

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


        environment().lifecycle().manage(new EntityManagerFactoryManagedService(entityManagerFactory));
        //Names.bindProperties();
    }
}
