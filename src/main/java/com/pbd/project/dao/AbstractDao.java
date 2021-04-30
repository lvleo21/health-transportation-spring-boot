package com.pbd.project.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public abstract class AbstractDao<T, PK extends Serializable>{

    private final Class<T> entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    @PersistenceContext
    private EntityManager entityManager;

    protected  EntityManager getEntityManager(){
        return  entityManager;
    }

    public void save(T entity){
        entityManager.persist(entity);
    }

    public void update(T entity){
        entityManager.merge(entity);
    }

    public void delete (PK id){
        entityManager.remove(entityManager.getReference(entityClass, id));
    }

    public T findById(PK id){
        return entityManager.find(entityClass, id);
    }

    public List<T> findAll(){
        String sql = "from " + entityClass.getSimpleName();
        return entityManager.createQuery(sql, entityClass).getResultList();
    }

    protected List<T> createQuery(String jpql, Object... args){
        TypedQuery<T> query = entityManager.createQuery(jpql, entityClass);

        for (int i = 0; i < args.length; i++){
            query.setParameter(i+1, args[i]);
        }

        return query.getResultList();
    }


}