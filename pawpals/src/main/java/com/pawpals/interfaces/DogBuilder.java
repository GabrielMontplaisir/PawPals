package com.pawpals.interfaces;

import com.pawpals.beans.Dog;

public class DogBuilder {
	private int dogId, ownerId;
	private String name, size, specialNeeds;
	private boolean immunized;
	
	public DogBuilder setDogId(int dogId) {
		this.dogId = dogId;
		return this;
	}
	
	public DogBuilder setOwnerId(int ownerId) {
		this.ownerId = ownerId;
		return this;
	}
	public DogBuilder setName(String name) {
		this.name = name;
		return this;
	}
	
	public DogBuilder setSize(String size) {
		this.size = size;
		return this;
	}
	
	public DogBuilder setSpecialNeeds(String specialNeeds) {
		this.specialNeeds = specialNeeds;
		return this;
	}
	
	public DogBuilder setImmunized(boolean immunized) {
		this.immunized = immunized;
		return this;
	}
	
	public Dog create() {
		return new Dog(dogId, ownerId, name, size, specialNeeds, immunized);
	}
}
