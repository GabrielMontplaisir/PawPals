package com.pawpals.libs.builders;

import com.pawpals.beans.Notification;

public class NotificationBuilder {
	private int userId;
	private String title, description, date, url;
	private boolean readStatus;
	
	public NotificationBuilder setUserId(int userId) {
		this.userId = userId;
		return this;
	}
	
	public NotificationBuilder setTitle(String title) {
		this.title = title;
		return this;
	}
	
	public NotificationBuilder setDescription(String description) {
		this.description = description;
		return this;
	}
	
	public NotificationBuilder setDate(String date) {
		this.date = date;
		return this;
	}
	
	public NotificationBuilder setUrl(String url) {
		this.url = url;
		return this;
	}
	
	public NotificationBuilder setReadStatus(boolean readStatus) {
		this.readStatus = readStatus;
		return this;
	}
	
	public Notification create() {
		return new Notification(userId, title, description, date, readStatus, url);
	}
}
