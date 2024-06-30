package com.pawpals.beans;

import java.time.LocalDate;
import java.util.List;

import com.pawpals.dao.DogDao;
import com.pawpals.dao.WalkDao;

public class User {
	private final int userId;
	private final String firstName, lastName, email;
	private final LocalDate dob;
	
	public User(int userId, String email, String firstName, String lastName,  String dob) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.dob = LocalDate.parse(dob);
	}

	public int getId() {return userId;}
	public String getFirstName() {return firstName;}
	public String getLastName() {return lastName;}
	public String getEmail() {return email;}
	public LocalDate getDob() {return dob;}
	
	public List<Dog> getDogs() {
		return DogDao.dogDao.getDogsByUserId(this.userId);
	}
	public List<Walk> getWalks(){
		return WalkDao.dao.getWalksByOwnerId(this.userId);
	}
	
	public Walk getWalkAsOwner(int walkId) {
		Walk walk = WalkDao.dao.getWalkById(walkId);
		if ( walk.getOwnerId() == userId ) {
			System.out.println("confirmed getting walk as owner");
			return walk;
		}
		System.err.println("Tried to get walkId as owner but ont owner " + walkId) ;
		return null;
	}
}
