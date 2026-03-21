package org.example.dao;

import jakarta.persistence.EntityManager;
import org.example.model.Endereço;
import org.example.util.JPAUtil;

import java.util.List;

public class EnderecoDAO {

    public void salvar(Endereço endereço){
        EntityManager em = JPAUtil.getEntityManager();

        em.getTransaction().begin();

        em.persist(endereço);

        em.getTransaction().commit();
        em.close();
    }

    public Endereço consultar(int id){
        EntityManager em = JPAUtil.getEntityManager();

        Endereço e = em.find(Endereço.class, id);

        em.close();
        return e;
    }

    public List<Endereço> listar(){
        EntityManager em = JPAUtil.getEntityManager();
        List<Endereço> lista = em.createQuery("FROM Endereço", Endereço.class).getResultList();

        em.close();
        return lista;
    }

    public void atualizar(Endereço endereço){
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();

        em.merge(endereço);

        em.getTransaction().commit();
        em.close();
    }

    public void deletar(int id){
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();

        Endereço e = em.find(Endereço.class, id);
        if (e != null){
            em.refresh(e);
        }

        em.getTransaction().commit();
        em.close();
    }
}
