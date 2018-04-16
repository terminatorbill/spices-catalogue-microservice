package com.spices.persistence.util;

import java.util.function.Consumer;
import java.util.function.Function;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.spices.persistence.provider.EntityManagerProvider;

public class TransactionManager {

    private final EntityManagerProvider entityManagerProvider;

    @Inject
    public TransactionManager(EntityManagerProvider entityManagerProvider) {

        this.entityManagerProvider = entityManagerProvider;
    }


    public <T> T doInJPA(JPATransactionFunction<T> function) {
        T result;
        EntityTransaction txn = null;
        EntityManager entityManager = null;
        try {
            function.beforeTransactionCompletion();
            entityManager = entityManagerProvider.get();
            txn = entityManager.getTransaction();
            txn.begin();
            result = function.apply(entityManager);
            txn.commit();
        } catch (RuntimeException e) {
            if ( txn != null && txn.isActive()) {
                txn.rollback();
            }
            throw e;
        } finally {
            function.afterTransactionCompletion();
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return result;
    }

    public void doInJPA(JPATransactionVoidFunction function) {
        EntityManager entityManager = null;
        EntityTransaction txn = null;
        try {
            function.beforeTransactionCompletion();
            entityManager = entityManagerProvider.get();
            txn = entityManager.getTransaction();
            txn.begin();
            function.accept(entityManager);
            txn.commit();
        } catch (RuntimeException e) {
            if ( txn != null && txn.isActive()) txn.rollback();
            throw e;
        } finally {
            function.afterTransactionCompletion();
            if (entityManager != null) {
                entityManager.close();
            }
        }
    }

    public <T> T doInJPAWithoutTransaction(JPAWithoutTransactionFunction<T> function) {
        T result;
        EntityManager entityManager = null;
        try {
            entityManager = entityManagerProvider.get();
            result = function.apply(entityManager);
        } finally {
            if (entityManager != null) {
                entityManager.close();
            }
        }
        return result;
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

    @FunctionalInterface
    public interface JPAWithoutTransactionFunction<T> extends Function<EntityManager, T> {

    }
}
