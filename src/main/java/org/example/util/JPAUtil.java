package org.example.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;


// Classe utilizada no DAO para reaproveitamente de código.

public class JPAUtil {

    private static final EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("frotaPU");  // Faz a conexão com o persistence.xml apenas uma vez

    public static EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
}