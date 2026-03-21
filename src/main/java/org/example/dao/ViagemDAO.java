package org.example.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.example.model.Viagem;
import org.example.util.JPAUtil;

import java.util.List;

public class ViagemDAO {

    public void salvar(Viagem viagem){
        EntityManager em = JPAUtil.getEntityManager();

        em.getTransaction().begin();

        em.persist(viagem);

        em.getTransaction().commit();
        em.close();
    }

    public Viagem consultar(int id){
        EntityManager em = JPAUtil.getEntityManager();

        Viagem v = em.find(Viagem.class, id);

        em.close();
        return v;
    }

    public List<Viagem> listar(){
        EntityManager em = JPAUtil.getEntityManager();
        List<Viagem> lista = em.createQuery("FROM Viagem", Viagem.class).getResultList();

        em.close();
        return lista;
    }

    public void atualizar(Viagem viagem){
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();

        em.merge(viagem);

        em.getTransaction().commit();
        em.close();
    }

    public void deletar(int id){
        EntityManager em = JPAUtil.getEntityManager();
        em.getTransaction().begin();

        Viagem v = em.find(Viagem.class, id);
        if (v != null){
            em.refresh(v);
        }

        em.getTransaction().commit();
        em.close();
    }

    // UTILIZADO NO RELATÓRIO DE VIAGENS POR MOTORISTA

    public List<Viagem> listarPorMotorista(int motoristaId){
        EntityManager em = JPAUtil.getEntityManager();

        String jpql = "SELECT v FROM Viagem v WHERE v.motorista.id = :id";

        TypedQuery<Viagem> query = em.createQuery(jpql, Viagem.class);
        query.setParameter("id", motoristaId);

        List<Viagem> lista = query.getResultList();
        em.close();

        return lista;
    }
}
