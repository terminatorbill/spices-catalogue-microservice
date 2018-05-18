package com.spices.configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.dropwizard.Configuration;

public class AppConfiguration extends Configuration {

    @NotNull
    @Valid
    @JsonProperty("hibernate")
    private HibernateConfiguration hibernateConfiguration;

    public HibernateConfiguration getHibernateConfiguration() {
        return hibernateConfiguration;
    }

    public void setHibernateConfiguration(HibernateConfiguration hibernateConfiguration) {
        this.hibernateConfiguration = hibernateConfiguration;
    }
}
