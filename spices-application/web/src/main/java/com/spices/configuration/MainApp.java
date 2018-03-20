package com.spices.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.hubspot.dropwizard.guice.GuiceBundle;
import com.spices.api.CategoryApi;
import com.spices.api.exceptionmapper.CategoryAlreadyExistsExceptionMapper;
import com.spices.api.exceptionmapper.GenericExceptionMapper;
import com.spices.modules.AppModule;

import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.jersey.jackson.JsonProcessingExceptionMapper;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

public class MainApp extends Application<Configuration> {

    private static final Logger LOG = LoggerFactory.getLogger(MainApp.class);

    @Override
    public void initialize(Bootstrap<Configuration> bootstrap) {
        GuiceBundle<Configuration> guiceBundle = GuiceBundle.newBuilder()
            .addModule(new AppModule())
            .setConfigClass(Configuration.class)
            .build();
        bootstrap.addBundle(guiceBundle);
    }

    @Override
    public void run(Configuration configuration, Environment environment) {
        Guice.createInjector(new AppModule());

        LOG.info("Registering REST resource classes");
        environment.jersey().getResourceConfig().register(CategoryApi.class);
        environment.jersey().getResourceConfig().register(CategoryAlreadyExistsExceptionMapper.class);
        environment.jersey().getResourceConfig().register(GenericExceptionMapper.class);
        environment.jersey().register(new JsonProcessingExceptionMapper(true));
    }

    public static void main(String[] args) throws Exception {
        new MainApp().run(args);
    }
}
