package com.spices.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spices.api.CategoryApi;
import com.spices.api.exceptionmapper.CategoryAlreadyExistsExceptionMapper;
import com.spices.exceptionmapper.GenericExceptionMapper;
import com.spices.modules.AppModule;
import com.spices.modules.PersistentModule;
import com.spices.persistence.configuration.EntityManagerProvider;

import io.dropwizard.Application;
import io.dropwizard.configuration.EnvironmentVariableSubstitutor;
import io.dropwizard.configuration.SubstitutingSourceProvider;
import io.dropwizard.jersey.jackson.JsonProcessingExceptionMapper;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import ru.vyarus.dropwizard.guice.GuiceBundle;
import ru.vyarus.dropwizard.guice.GuiceyOptions;

public class MainApp extends Application<AppConfiguration> {

    private static final Logger LOG = LoggerFactory.getLogger(MainApp.class);
    private static final String PERSISTENCE_UNIT = "catalogueManager";

    @Override
    public void initialize(Bootstrap<AppConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
            bootstrap.getConfigurationSourceProvider(),
            new EnvironmentVariableSubstitutor(false)
        ));

        bootstrap.addBundle(
            GuiceBundle.builder()
                .option(GuiceyOptions.UseHkBridge, true)
                .modules(new AppModule(), new PersistentModule())
            .build());
    }

    @Override
    public void run(AppConfiguration configuration, Environment environment) {
        LOG.info("Registering REST resource classes");
        environment.jersey().getResourceConfig().register(CategoryApi.class);
        environment.jersey().getResourceConfig().register(CategoryAlreadyExistsExceptionMapper.class);
        environment.jersey().getResourceConfig().register(GenericExceptionMapper.class);
        environment.jersey().register(new JsonProcessingExceptionMapper(true));
        environment.lifecycle().manage(new EntityManagerProvider(PERSISTENCE_UNIT));
    }

    public static void main(String[] args) throws Exception {
        new MainApp().run(args);
    }
}
