package com.spices.configuration;

import javax.persistence.EntityManagerFactory;

import io.dropwizard.lifecycle.Managed;

public class EntityManagerFactoryManagedService implements Managed {
    private EntityManagerFactory entityManagerFactory;

    EntityManagerFactoryManagedService(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {
        if (entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}
