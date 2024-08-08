package com.pawpals.beans;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.pawpals.libs.interfaces.Observer;

public class User implements Observer {
	private final int userId;
	private String firstName, lastName, email;
	private LocalDate dob;
	private boolean isOwnerMode = false;
	private Map<Integer, Dog> dogList = new HashMap<>();
	private Map<Integer, Walk> walkList = new HashMap<>();
	private Map<Integer, Walk> cachedWalks = new HashMap<>();
	private ArrayList<Notification> notificationList = new ArrayList<>();
	
	// Constructor
	
	public User(int userId, String email, String firstName, String lastName,  String dob) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.dob = LocalDate.parse(dob);
	}
	
	// Getter Methods
	
	public int getUserId() {return userId;}
	public String getFirstName() {return firstName;}
	public String getLastName() {return lastName;}
	public String getEmail() {return email;}
	public LocalDate getDob() {return dob;}
	public boolean isOwnerMode() {return isOwnerMode;}
	public Map<Integer, Dog> getDogList() {return dogList;}
	public Map<Integer, Walk> getWalkList() {return walkList;}
	public Map<Integer, Walk> getCachedWalks() {return cachedWalks;}
	public ArrayList<Notification> getNotifications() {return notificationList;}
	
	// Setter Methods
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}
	
	public void setOwnerMode(boolean isOwnerMode) {
		this.isOwnerMode = isOwnerMode;
	}

	public void setDogList(Map<Integer, Dog> dogList) {
		this.dogList = dogList;
	}
	
	public void setWalkList(Map<Integer, Walk> walkList) {
		this.walkList = walkList;
	}
	
	public void setNotificationList(ArrayList<Notification> notificationList) {
		this.notificationList = notificationList;
	}

	@Override
	public void update(Notification notif) {
		notificationList.add(notif);
	}
	
}
