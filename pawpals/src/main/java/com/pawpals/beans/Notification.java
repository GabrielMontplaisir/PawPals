package com.pawpals.beans;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Notification {
	private int userId;
	private String title, description, date, url;
	private boolean readStatus;
	
	
	public Notification(int userId, String title, String description, String date, boolean readStatus, String url) {
		this.userId = userId;
		this.title = title;
		this.description = description;
		this.date = date.replace(" ", "T");
		this.readStatus = readStatus;
		this.url = url;
	}

	public int getUserId() {return userId;}
	public String getTitle() {return title;}
	public boolean getReadStatus() {return readStatus;}
	public String getUrl() {return url;}
	public String getDescription() {return description;}

	public String getDateTime() {
		return LocalDateTime.parse(date).format(DateTimeFormatter.ofPattern("E, MMM dd, yyyy 'at' HH:mm"));
	}
	

	
}
