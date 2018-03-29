package com.spices.persistence.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.spices.persistence.configuration.EntityManagerProvider;

public class TransactionManager {

    private final EntityManagerProvider entityManagerProvider;

    @Inject
    public TransactionManager(EntityManagerProvider entityManagerProvider) {

        this.entityManagerProvider = entityManagerProvider;
    }


    public <T> T doInJPA(JPATransactionFunction<T> function, int isolationLevel) {
        T result;
        EntityTransaction txn = null;
        EntityManager entityManager = null;
        try {
            function.beforeTransactionCompletion();
            entityManager = entityManagerProvider.get();
            entityManager.unwrap(Connection.class).setTransactionIsolation(isolationLevel);
            txn = entityManager.getTransaction();
            txn.begin();
            result = function.apply(entityManager);
            txn.commit();
        } catch (RuntimeException e) {
            if ( txn != null && txn.isActive()) {
                txn.rollback();
            }
            throw e;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            function.afterTransactionCompletion();
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return result;
    }

    public void doInJPA(JPATransactionVoidFunction function, int isolationLevel) {
        EntityManager entityManager = null;
        EntityTransaction txn = null;
        try {
            function.beforeTransactionCompletion();
            entityManager = entityManagerProvider.get();
            entityManager.unwrap(Connection.class).setTransactionIsolation(isolationLevel);
            txn = entityManager.getTransaction();
            txn.begin();
            function.accept(entityManager);
            txn.commit();
        } catch (RuntimeException e) {
            if ( txn != null && txn.isActive()) txn.rollback();
            throw e;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            function.afterTransactionCompletion();
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }


    @FunctionalInterface
    public interface JPATransactionFunction<T> extends Function<EntityManager, T> {
        default void beforeTransactionCompletion() {

        }

        default void afterTransactionCompletion() {

        }
    }

    @FunctionalInterface
    public interface JPATransactionVoidFunction extends Consumer<EntityManager> {
        default void beforeTransactionCompletion() {

        }

        default void afterTransactionCompletion() {

        }
    }
}
