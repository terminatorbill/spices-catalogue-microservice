package com.spices.persistence.provider;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class EntityManagerProvider {
    private final EntityManagerFactory entityManagerFactory;

    public EntityManagerProvider(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public EntityManager get() {
        return entityManagerFactory.createEntityManager();
    }
}
