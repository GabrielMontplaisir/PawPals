package com.pawpals.interfaces;

import com.pawpals.beans.User;

public class UserBuilder {
	private int userId;
	private String firstName, lastName, email, dob;
	
	public UserBuilder setUserId(int userId) {
		this.userId = userId;
		return this;
	}
	
	public UserBuilder setEmail(String email) {
		this.email = email;
		return this;
	}
	
	public UserBuilder setFirstName(String firstName) {
		this.firstName = firstName;
		return this;
	}
	
	public UserBuilder setLastName(String lastName) {
		this.lastName = lastName;
		return this;
	}
	
	public UserBuilder setDOB(String dob) {
		this.dob = dob;
		return this;
	}

	public User create() {
		return new User(userId, email, firstName, lastName, dob);
	}
}
