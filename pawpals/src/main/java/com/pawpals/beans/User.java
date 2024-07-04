package com.pawpals.beans;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import com.pawpals.dao.DogDao;
import com.pawpals.dao.WalkDao;

public class User {
	private final int userId;
	private final String firstName, lastName, email;
	private final LocalDate dob;
	
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
	public List<Dog> getDogsFromDB() {return DogDao.dogDao.getDogsByOwner(userId);}
	public List<Walk> getWalksFromDB_AsOwner() {return WalkDao.dao.getWalksByOwnerId(userId);}
	public List<Walk> getWalksFromDB_AsWalker() {return WalkDao.dao.getWalksByWalkerId(userId);}
	public List<Walk> getAvailableWalksFromDB(HashMap<Integer, Boolean> walkOffers) {return WalkDao.dao.getWalksPostedForReceivingOffers(userId, walkOffers);}
	
}
