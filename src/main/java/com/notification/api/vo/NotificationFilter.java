package com.notification.api.vo;

import java.util.List;

import com.notification.api.models.Notification;

public class NotificationFilter {
	
	private int page;
	
	private int pageSize;
	
	private int total;
	
	private List<Notification> data;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<Notification> getData() {
		return data;
	}

	public void setData(List<Notification> data) {
		this.data = data;
	}

}
