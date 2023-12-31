package com.cedacri.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import java.util.function.Function;

public class JpaService {
  private static JpaService instance;
  private EntityManagerFactory entityManagerFactory;

  private JpaService() {
    entityManagerFactory = Persistence.createEntityManagerFactory("jpa-hibernate-mysql");
  }

  public static synchronized JpaService getInstance() {
    return instance == null ? instance = new JpaService() : instance;
  }

  public EntityManager getTransaction(){
    return entityManagerFactory.createEntityManager();
  }

  public <T> T runInTransaction(Function<EntityManager, T> function) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    EntityTransaction transaction = entityManager.getTransaction();
    transaction.begin();
    boolean success = false;
    try {
      T returnValue = function.apply(entityManager);
      success = true;
      return returnValue;
    } finally {
      if (success) {
        transaction.commit();
      } else {
        transaction.rollback();
        throw new RuntimeException("JpaService transaction.rollback();");
      }
    }
  }
  //When we're finished with all of our queries, we need to close both the EntityManager
  // and the EntityManagerFactory; otherwise their threads will live on and our application will never complete
  public void shutdown() {
    if (entityManagerFactory != null) {
      entityManagerFactory.close();
      instance = null;
    }
  }
}
