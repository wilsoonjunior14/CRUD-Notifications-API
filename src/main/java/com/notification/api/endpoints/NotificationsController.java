package com.notification.api.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.notification.api.business.NotificationsBO;
import com.notification.api.models.Notification;
import com.notification.api.vo.NotificationFilter;

@Lazy
@RestController
@CrossOrigin
@RequestMapping(value = "notifications")
public class NotificationsController {

	@Autowired
	private NotificationsBO notificationsBO;
	
	@PostMapping(value = "/search")
	public ResponseEntity getAll(final @RequestBody NotificationFilter filter) {
		return new ResponseEntity<>(new Gson().toJson(this.notificationsBO.getByPagination(filter)), HttpStatus.OK);
	}
	
	@GetMapping(value = "/{id}")
	public ResponseEntity getById(final @PathVariable("id") Long id) {
		return new ResponseEntity<>(new Gson().toJson(this.notificationsBO.getById(id)), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity create(final @RequestBody Notification notification) {
		return new ResponseEntity<>(new Gson().toJson(this.notificationsBO.create(notification)), HttpStatus.OK);
	}
	
	@PutMapping
	public ResponseEntity update(final @RequestBody Notification notification) {
		return new ResponseEntity<>(new Gson().toJson(this.notificationsBO.update(notification)), HttpStatus.OK);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity delete(final @PathVariable("id") Long id) {
		return new ResponseEntity<>(new Gson().toJson(this.notificationsBO.delete(id)), HttpStatus.OK);
	}
	
}
