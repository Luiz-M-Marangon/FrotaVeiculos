package org.example.dao;

import jakarta.persistence.EntityManager;
import org.example.model.Motorista;
import org.example.util.JPAUtil;
import org.hibernate.Session;

public class MotoristaDAO {

    public void salvar(Motorista motorista){
        EntityManager em = JPAUtil.getEntityManager();

        em.getTransaction().begin();

        em.persist(motorista);

        em.getTransaction().commit();
        em.close();
    }
}
