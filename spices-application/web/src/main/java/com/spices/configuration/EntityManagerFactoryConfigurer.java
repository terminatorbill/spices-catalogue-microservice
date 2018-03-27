package com.spices.configuration;

import javax.persistence.EntityManagerFactory;

import io.dropwizard.lifecycle.Managed;

public class EntityManagerFactoryConfigurer implements Managed {

    private final EntityManagerFactory entityManagerFactory;

    public EntityManagerFactoryConfigurer(EntityManagerFactory entityManagerFactory) {

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
