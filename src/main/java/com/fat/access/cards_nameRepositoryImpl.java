package com.fat.access;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.fat.repository.cards_name_surname_nrhacosoft;

@Repository
public class cards_nameRepositoryImpl {
	
	private static SessionFactory factory;

	   @PersistenceContext
	    private EntityManager entityManager;
	  
	   
	   public List<cards_name_surname_nrhacosoft> findFirst10Workers()
	   {
		   return entityManager.createQuery("select c from cards_name_surname_nrhacosoft c", cards_name_surname_nrhacosoft.class).setMaxResults(10).getResultList();
	   }
	   
	   public List<String> finddupa()
	   {
		   String hql = "FROM cards_name_surname_nrhacosoft";
		   
		   Session session = factory.openSession();
		   
		   Query query = session.createQuery(hql);
				   
				   List listResult = ((org.hibernate.query.Query) query).list();
		   
		   return listResult;
		   
	   }
	
}
