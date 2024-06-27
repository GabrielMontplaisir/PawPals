package com.pawpals.beans;

import java.time.LocalDate;
import java.util.UUID;

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
	
}
