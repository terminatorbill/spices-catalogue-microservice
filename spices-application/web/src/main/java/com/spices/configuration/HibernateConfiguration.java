package com.spices.configuration;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

public class HibernateConfiguration {

    @NotEmpty
    @JsonProperty("persistenceUnit")
    private String persistenceUnit;

    @NotEmpty
    @JsonProperty("jdbcDriver")
    private String jdbcDriver;

    @NotEmpty
    @JsonProperty("jdbcUrl")
    private String jdbcUrl;

    @NotEmpty
    @JsonProperty("jdbcUser")
    private String jdbcUser;

    @NotEmpty
    @JsonProperty("jdbcPassword")
    private String jdbcPassword;

    @NotEmpty
    @JsonProperty("hibernateDialect")
    private String hibernateDialect;

    @NotEmpty
    @JsonProperty("hbmAuto")
    private String hbmAuto;

    @JsonProperty("showSql")
    private boolean showSql;

    @JsonProperty("formatSql")
    private boolean formatSql;

    @JsonProperty("flushMode")
    private String flushMode;

    public String getPersistenceUnit() {
        return persistenceUnit;
    }

    public void setPersistenceUnit(String persistenceUnit) {
        this.persistenceUnit = persistenceUnit;
    }

    public String getJdbcDriver() {
        return jdbcDriver;
    }

    public void setJdbcDriver(String jdbcDriver) {
        this.jdbcDriver = jdbcDriver;
    }

    public String getJdbcUrl() {
        return jdbcUrl;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public String getJdbcUser() {
        return jdbcUser;
    }

    public void setJdbcUser(String jdbcUser) {
        this.jdbcUser = jdbcUser;
    }

    public String getJdbcPassword() {
        return jdbcPassword;
    }

    public void setJdbcPassword(String jdbcPassword) {
        this.jdbcPassword = jdbcPassword;
    }

    public String getHibernateDialect() {
        return hibernateDialect;
    }

    public void setHibernateDialect(String hibernateDialect) {
        this.hibernateDialect = hibernateDialect;
    }

    public String getHbmAuto() {
        return hbmAuto;
    }

    public void setHbmAuto(String hbmAuto) {
        this.hbmAuto = hbmAuto;
    }

    public boolean isShowSql() {
        return showSql;
    }

    public void setShowSql(boolean showSql) {
        this.showSql = showSql;
    }

    public boolean isFormatSql() {
        return formatSql;
    }

    public void setFormatSql(boolean formatSql) {
        this.formatSql = formatSql;
    }

    public String getFlushMode() {
        return flushMode;
    }

    public void setFlushMode(String flushMode) {
        this.flushMode = flushMode;
    }
}
