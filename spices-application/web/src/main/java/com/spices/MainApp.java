package com.spices;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.spices.api.CategoryAdminApi;
import com.spices.api.CategoryApi;
import com.spices.api.ProductApi;
import com.spices.api.exceptionmapper.CannotDeleteParentCategoryExceptionMapper;
import com.spices.api.exceptionmapper.CategoryAlreadyExistsExceptionMapper;
import com.spices.api.exceptionmapper.CategoryDoesNotExistsExceptionMapper;
import com.spices.api.exceptionmapper.ProductAlreadyExistsExceptionMapper;
import com.spices.configuration.AppConfiguration;
import com.spices.exceptionmapper.GenericExceptionMapper;
import com.spices.modules.AppModule;
import com.spices.modules.PersistentModule;

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

    @Override
    public void initialize(Bootstrap<AppConfiguration> bootstrap) {
        bootstrap.setConfigurationSourceProvider(new SubstitutingSourceProvider(
            bootstrap.getConfigurationSourceProvider(),
            new EnvironmentVariableSubstitutor(true)
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
        environment.jersey().getResourceConfig().register(CategoryAdminApi.class);
        environment.jersey().getResourceConfig().register(ProductApi.class);
        environment.jersey().getResourceConfig().register(CategoryAlreadyExistsExceptionMapper.class);
        environment.jersey().getResourceConfig().register(CategoryDoesNotExistsExceptionMapper.class);
        environment.jersey().getResourceConfig().register(CannotDeleteParentCategoryExceptionMapper.class);
        environment.jersey().getResourceConfig().register(ProductAlreadyExistsExceptionMapper.class);
        environment.jersey().getResourceConfig().register(GenericExceptionMapper.class);
        environment.jersey().register(new JsonProcessingExceptionMapper(true));
    }

    public static void main(String[] args) throws Exception {
        new MainApp().run(args);
    }
}
