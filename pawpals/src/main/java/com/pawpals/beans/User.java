package com.pawpals.beans;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

import com.pawpals.services.DogService;
import com.pawpals.services.WalkService;

public class User {
	private final int userId;
	private final String firstName, lastName, email;
	private final LocalDate dob;
	public int getId() {return userId;}
	
	
	public String getFirstName() {return firstName;}
	public String getLastName() {return lastName;}
	public String getEmail() {return email;}
	public LocalDate getDob() {return dob;}
	
	public User(int userId, String email, String firstName, String lastName,  String dob) {
		this.userId = userId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.dob = LocalDate.parse(dob);
	}

	public List<Dog> getDogs_as_DogOwner() {
		return DogService.svc.getDogs_by_DogOwnerUserId(userId);
	}
	
	public List<Walk> getWalks_for_Soliciting_WalkOffers(HashMap<Integer, Boolean> walkOffers ){
		return WalkService.svc.getWalks_for_Soliciting_WalkOffers(userId, walkOffers);
	}
	
}
