package com.pawpals.beans;

public class Dog {
    private final int dogId;
    private final int ownerId;
    private String name, size, specialNeeds;
    private boolean immunized;
    
    // Constructor

    public Dog(int dogId, int ownerId, String name, String size, String specialNeeds, boolean immunized) {
        this.dogId = dogId;
        this.ownerId = ownerId;
        this.name = name;
        this.size = size;
        this.specialNeeds = specialNeeds;
        this.immunized = immunized;
    }
    
    // Getter Methods

    public int getDogId() {return dogId;}
    public int getOwnerId() {return ownerId;}
    public String getName() {return name;}
    public String getSize() {return size;}
    public String getSpecialNeeds() {return specialNeeds;}
    public boolean isImmunized() {return immunized;}
 
    // Setter methods
    
	public void setName(String name) {
		this.name = name;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public void setSpecialNeeds(String specialNeeds) {
		this.specialNeeds = specialNeeds;
	}

	public void setImmunized(boolean immunized) {
		this.immunized = immunized;
	}
}
