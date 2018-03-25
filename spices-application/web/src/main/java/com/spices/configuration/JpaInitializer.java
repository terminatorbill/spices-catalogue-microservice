package com.spices.configuration;

import javax.inject.Inject;

import com.google.inject.persist.PersistService;

public class JpaInitializer {

    @Inject
    JpaInitializer(PersistService persistService) {
        persistService.start();
    }
}
