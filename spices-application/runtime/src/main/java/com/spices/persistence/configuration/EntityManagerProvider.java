package com.spices.persistence.configuration;

import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Maps;

import io.dropwizard.lifecycle.Managed;

public class EntityManagerProvider implements Managed {
    private static final Logger LOG = LoggerFactory.getLogger(EntityManagerProvider.class);

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
        Map<String, String> environmentVariables = System.getenv();
        Map<String, Object> configOverrides = Maps.newHashMap();

        LOG.info("Postgresql port = {}", environmentVariables.get("it-database.port"));

        //TODO: Replace localhost with the ip of the docker postgresql container
        configOverrides.put("javax.persistence.jdbc.url", "jdbc:postgresql://localhost:"+ environmentVariables.getOrDefault("it-database.port", "5432") +"/catalogue");
        entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnit, configOverrides);
    }

    @Override
    public void stop() {
        if (entityManagerFactory.isOpen()) {
            entityManagerFactory.close();
        }
    }
}
