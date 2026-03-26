package org.example.dao;

import jakarta.persistence.EntityManager;
import org.example.model.Endereco;
import org.example.util.JPAUtil;

import java.util.List;

public class EnderecoDAO {

    public void salvar(Endereco endereco){
        EntityManager em = JPAUtil.getEntityManager();      // código reaproveitado de util.JPAUTIL

        em.getTransaction().begin();

        em.persist(endereco);

        em.getTransaction().commit();
        em.close();
    }

    public Endereco consultar(int id){
        EntityManager em = JPAUtil.getEntityManager();      // código reaproveitado de util.JPAUTIL

        Endereco e = em.find(Endereco.class, id);

        em.close();
        return e;
    }

    public List<Endereco> listar(){
        EntityManager em = JPAUtil.getEntityManager();
        List<Endereco> lista = em.createQuery("FROM Endereco", Endereco.class).getResultList();

        em.close();
        return lista;
    }

    public void atualizar(Endereco endereco){
        EntityManager em = JPAUtil.getEntityManager();      // código reaproveitado de util.JPAUTIL
        em.getTransaction().begin();

        em.merge(endereco);

        em.getTransaction().commit();
        em.close();
    }

    public void deletar(int id){
        EntityManager em = JPAUtil.getEntityManager();      // código reaproveitado de util.JPAUTIL
        em.getTransaction().begin();

        Endereco e = em.find(Endereco.class, id);
        if (e != null){
            em.remove(e);
        }

        em.getTransaction().commit();
        em.close();
    }
}
