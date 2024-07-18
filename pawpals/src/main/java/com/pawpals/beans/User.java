package com.pawpals.beans;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class User {
	private final int userId;
	private final String firstName, lastName, email;
	private final LocalDate dob;
	private boolean isOwnerMode = false;
	private Map<Integer, Dog> dogList = new HashMap<>();
	
	// Constructor
	
	public User(int userId, String email, String firstName, String lastName,  String dob) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.dob = LocalDate.parse(dob);
	}
	
	// Getter Methods
	
	public int getId() {return userId;}
	public String getFirstName() {return firstName;}
	public String getLastName() {return lastName;}
	public String getEmail() {return email;}
	public LocalDate getDob() {return dob;}
	public boolean isOwnerMode() {return isOwnerMode;}
	public Map<Integer, Dog> getDogList() {return dogList;}
	
	// Setter Methods
	
	public void setOwnerMode(boolean isOwnerMode) {
		this.isOwnerMode = isOwnerMode;
	}
	
	public void setDogList(Map<Integer, Dog> dogList) {
		this.dogList = dogList;
	}
	
}
