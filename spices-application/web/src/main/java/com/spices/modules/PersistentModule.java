package com.spices.modules;

import com.google.inject.AbstractModule;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.spices.configuration.JpaInitializer;

public class PersistentModule extends AbstractModule {
    @Override
    protected void configure() {

        bind(JpaInitializer.class).asEagerSingleton();

        install(new JpaPersistModule("catalogueManager"));
    }
}
