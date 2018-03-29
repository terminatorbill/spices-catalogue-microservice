package com.spices.persistence.configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import io.dropwizard.lifecycle.Managed;

public class EntityManagerProvider implements Managed {

    private EntityManagerFactory entityManagerFactory;
    private String persistenceUnit;

    protected EntityManagerProvider() {

    }

    public EntityManagerProvider(String persistenceUnit) {
        this.persistenceUnit = persistenceUnit;
    }

    public EntityManager get() {
        return entityManagerFactory.createEntityManager();
    }

    @Override
    public void start() {
        entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnit);
    }

    @Override
    public void stop() {
        if (entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}
