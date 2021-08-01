package com.notification.api.setup;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.sun.istack.logging.Logger;

public abstract class CRUDConfiguration<T>{
	
	@Autowired
	public EntityManagerFactory entityManagerFactory;
	
	/**
	 * Finds all instances based on string query
	 * 
	 * @param em EntityManager
	 * @param query String
	 * @return List
	 */
	public List<T> findAll(String query){
		EntityManager em = this.entityManagerFactory.createEntityManager();
		try {
			return em.createQuery(query).getResultList();
		} catch (Exception e) {
			throw new PersistenceException(e);
		} finally {
			em.close();
		}
	}
	
	public List<T> executeQuery(String query, Map<String, Object> params){
		EntityManager entityManager = this.entityManagerFactory.createEntityManager();
		try {
			Query jpqlQuery = entityManager.createQuery(query);
			params.forEach((key, value) -> {
				jpqlQuery.setParameter(key, value);
			});
			List list = jpqlQuery.getResultList();
			System.err.println("QUERY RESULTS: "+list.size());
			return list;
		} catch (Exception e) {
			throw new PersistenceException(e);
		} finally {
			entityManager.close();
		}
	}
	
	public List<T> executeQueryWithPagination(EntityManager em, String query, HashMap<String, Object> params, Integer offset, Integer pageSize){
		try {
			Query jpqlQuery = em.createQuery(query);
			params.forEach((key, value) -> {
				jpqlQuery.setParameter(key, value);
			});
			List list = jpqlQuery.setFirstResult(offset).setMaxResults(pageSize).getResultList();
			System.err.println("QUERY RESULTS: "+list.size());
			return list;
		} catch (Exception e) {
			throw new PersistenceException(e);
		} finally {
			em.close();
		}
	}
	
	/**
	 * Finds an instance based on long id
	 * 
	 * @param em EntityManager
	 * @param t Class of reference
	 * @param id Long
	 * @return 
	 * @return T Class
	 */
	public T findById(Class<?> t, Long id) {
		EntityManager em = this.entityManagerFactory.createEntityManager();
		try {
			return (T) em.find(t, id);
		} catch (Exception e) {
			throw new PersistenceException(e);
		} finally {
			em.close();
		}
	}
	
	
	/**
	 * Creates a new instance inside on database
	 * @param <T>
	 * 
	 * @param em EntityManager
	 * @param collections List
	 * @return List<T>
	 */
	public List<T> save(List<T> collections) {
		EntityManager entityManager = this.entityManagerFactory.createEntityManager();
		try {
			entityManager.getTransaction().begin();
			
			collections.forEach(c -> {
				System.err.println("Persisting Entity: "+c.toString());
				entityManager.merge(c);
			});
			
			entityManager.getTransaction().commit();
		} catch (Exception e) {
			entityManager.getTransaction().rollback();
			throw new PersistenceException(e);
		} finally {
			entityManager.close();
		}
		return collections;
	}
	
}
