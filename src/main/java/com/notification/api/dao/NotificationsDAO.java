package com.notification.api.dao;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

import com.notification.api.models.Notification;
import com.notification.api.setup.CRUDConfiguration;

@Lazy
@Repository("findmeCityDAO")
public class NotificationsDAO extends CRUDConfiguration<Notification>{
	
	private static final String notificationQuery = "SELECT n FROM Notification n WHERE n.deleted IS FALSE";
	
	@Autowired
	private EntityManagerFactory emf;
	
	public List<Notification> getAllWithPagination(final int offset, final int pageSize){
		return executeQueryWithPagination(this.emf.createEntityManager(), this.notificationQuery, new HashMap<>(), offset, pageSize);
	}
	
	public List<Notification> getAll(){
		return executeQuery(notificationQuery, new HashMap<>());
	}
	
	public List<Notification> save(final Notification notification) {
		return save(Collections.singletonList(notification));
	}
	
	public Notification getById(final Long id) {
		return findById(Notification.class, id);
	}

}
