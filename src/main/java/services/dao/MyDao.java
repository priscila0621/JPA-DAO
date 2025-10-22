package services.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import services.interfaces.ICRUD;
import util.JPAConexion;

import java.util.List;

public class MyDao implements ICRUD {

    @Override
    public <T> List<T> getAll(String nameQuery, Class<T> clazz) {
        EntityManager em = JPAConexion.getEntityManager();
        try {
            TypedQuery<T> query = em.createNamedQuery(nameQuery, clazz);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public <T> void insert(T entity) {
        EntityManager em = JPAConexion.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(entity);
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Error al insertar " + entity.getClass().getSimpleName(), ex);
        } finally {
            em.close();
        }
    }

    @Override
    public <T> void update(T entity) {
        EntityManager em = JPAConexion.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.merge(entity);
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Error al actualizar " + entity.getClass().getSimpleName(), ex);
        } finally {
            em.close();
        }
    }

    @Override
    public <T> void delete(T entity) {
        EntityManager em = JPAConexion.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            // re-ata el entity si está detached, para poder removerlo
            T managed = em.contains(entity) ? entity : em.merge(entity);
            em.remove(managed);
            tx.commit();
        } catch (Exception ex) {
            if (tx.isActive()) tx.rollback();
            throw new RuntimeException("Error al eliminar " + entity.getClass().getSimpleName(), ex);
        } finally {
            em.close();
        }
    }

    @Override
    public <T> T findById(Integer id, Class<T> clazz) {
        EntityManager em = JPAConexion.getEntityManager();
        try {
            return em.find(clazz, id);
        } finally {
            em.close();
        }
    }
}
