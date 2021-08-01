package com.notification.api.business;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.notification.api.dao.NotificationsDAO;
import com.notification.api.models.Notification;

@Lazy
@Service
public class NotificationsBO {

	@Autowired
	private NotificationsDAO notificationsDAO;
	
	public List<Notification> getAll(){
		System.err.print("getting all notifications...");
		return this.notificationsDAO.getAll();
	}
	
	public Notification getById(final Long id) {
		System.err.println("getting a notification");
		
		Notification notification = this.notificationsDAO.getById(id);
		if (notification.getViewDate() == null) {
			notification.setViewDate(Calendar.getInstance().getTime());
			this.update(notification);
		}
		
		return this.notificationsDAO.getById(id);
	}
	
	public Notification create(final Notification notificationToBeCreated) {
		System.err.println("creating a notification");
		notificationToBeCreated.setDeleted(false);
		return this.notificationsDAO.save(notificationToBeCreated).get(0);
	}
	
	public Notification update(final Notification notificationToBeUpdated) {
		System.err.println("updating a notification");
		return this.notificationsDAO.save(notificationToBeUpdated).get(0);
	}
	
	public Notification delete(final Long id) {
		System.err.println("deleting a notification");
		Notification oldNotification = this.notificationsDAO.getById(id);
		
		oldNotification.setDeleted(true);
		
		return this.notificationsDAO.save(oldNotification).get(0);
	}
	
}
