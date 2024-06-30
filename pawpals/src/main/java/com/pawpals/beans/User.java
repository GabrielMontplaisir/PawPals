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
	
	public List<Dog> getDogs_as_DogOwner() {
		return DogDao.dogDao.getDogsByUserId(this.userId);
	}
	public List<Walk> getWalks_as_DogOwner(){
		return WalkDao.dao.getWalksByOwnerId(this.userId);
	}
	
	public Walk getWalk_by_WalkId_as_Owner(int walkId) {//  validates user is dog owner of requested walk 
		Walk walk = WalkDao.dao.getWalkById(walkId);
		if ( walk.getOwnerId() == userId ) {
			return walk;
		}
		System.err.println(" * * " + userId + " Tried to get walkId as owner but isn't owner " + walkId) ;
		return null;
	}
}
