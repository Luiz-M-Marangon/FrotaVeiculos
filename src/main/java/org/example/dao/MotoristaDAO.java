package org.example.dao;

import jakarta.persistence.EntityManager;
import org.example.model.Motorista;
import org.example.util.JPAUtil;

import java.util.List;

public class MotoristaDAO {

    public void salvar(Motorista motorista){
        EntityManager em = JPAUtil.getEntityManager();      // código reaproveitado de util.JPAUTIL

        em.getTransaction().begin();

        em.persist(motorista);

        em.getTransaction().commit();
        em.close();
    }

    public Motorista consultar(int id){
        EntityManager em = JPAUtil.getEntityManager();      // código reaproveitado de util.JPAUTIL

        Motorista m = em.find(Motorista.class, id);

        em.close();
        return m;
    }

    public List<Motorista> listar(){
        EntityManager em = JPAUtil.getEntityManager();
        List<Motorista> lista = em.createQuery("FROM Motorista", Motorista.class).getResultList();

        em.close();
        return lista;
    }

    public void atualizar(Motorista motorista){
        EntityManager em = JPAUtil.getEntityManager();      // código reaproveitado de util.JPAUTIL
        em.getTransaction().begin();

        em.merge(motorista);

        em.getTransaction().commit();
        em.close();
    }

    public void deletar(int id){
        EntityManager em = JPAUtil.getEntityManager();      // código reaproveitado de util.JPAUTIL
        em.getTransaction().begin();

        Motorista m = em.find(Motorista.class, id);
        if (m != null){
            em.refresh(m);
        }

        em.getTransaction().commit();
        em.close();
    }
}
