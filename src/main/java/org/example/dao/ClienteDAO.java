package org.example.dao;

import jakarta.persistence.EntityManager;
import org.example.model.Cliente;
import org.example.util.JPAUtil;

import java.util.List;

public class ClienteDAO {

    public void salvar(Cliente cliente){
        EntityManager em = JPAUtil.getEntityManager();

        em.getTransaction().begin();

        em.persist(cliente);

        em.getTransaction().commit();
        em.close();
    }

    public Cliente consultar(int id){
        EntityManager em = JPAUtil.getEntityManager();

        Cliente c = em.find(Cliente.class, id);

        em.close();
        return c;
    }

    public List<Cliente> listar(){
        EntityManager em = JPAUtil.getEntityManager();
        List<Cliente> lista = em.createQuery("FROM Cliente", Cliente.class).getResultList();

        em.close();
        return lista;
    }

    public void atualizar(Cliente cliente){
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();

        em.merge(cliente);

        em.getTransaction().commit();
        em.close();
    }

    public void deletar(int id){
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();

        Cliente c = em.find(Cliente.class, id);
        if (c != null){
            em.remove(c);
        }

        em.getTransaction().commit();
        em.close();
    }
}