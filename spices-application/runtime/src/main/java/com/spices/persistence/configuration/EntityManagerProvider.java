package com.spices.persistence.configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerProvider {

    public static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("catalogueManager");

    public EntityManager get() {
        return ENTITY_MANAGER_FACTORY.createEntityManager();
    }
}
