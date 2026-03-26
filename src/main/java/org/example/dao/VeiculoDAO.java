package org.example.dao;

import jakarta.persistence.EntityManager;
import org.example.model.Veiculo;
import org.example.util.JPAUtil;

import java.util.List;

public class VeiculoDAO {

    public void salvar(Veiculo veiculo){
        EntityManager em = JPAUtil.getEntityManager();      // código reaproveitado de util.JPAUTIL

        em.getTransaction().begin();

        em.persist(veiculo);

        em.getTransaction().commit();
        em.close();
    }

    public Veiculo consultar(int id){
        EntityManager em = JPAUtil.getEntityManager();      // código reaproveitado de util.JPAUTIL

        Veiculo v = em.find(Veiculo.class, id);

        em.close();
        return v;
    }

    public List<Veiculo> listar(){
        EntityManager em = JPAUtil.getEntityManager();      // código reaproveitado de util.JPAUTIL
        List<Veiculo> lista = em.createQuery("FROM Veiculo", Veiculo.class).getResultList();

        em.close();
        return lista;
    }

    public void atualizar(Veiculo veiculo){
        EntityManager em = JPAUtil.getEntityManager();      // código reaproveitado de util.JPAUTIL
        em.getTransaction().begin();

        em.merge(veiculo);

        em.getTransaction().commit();
        em.close();
    }

    public void deletar(int id){
        EntityManager em = JPAUtil.getEntityManager();      // código reaproveitado de util.JPAUTIL
        em.getTransaction().begin();

        Veiculo v = em.find(Veiculo.class, id);
        if (v != null){
            em.remove(v);
        }

        em.getTransaction().commit();
        em.close();
    }
}
