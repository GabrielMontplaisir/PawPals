package com.pawpals.beans;

public class Dog {
    private int dogId;
    private final int ownerId;
    private final String name, size, specialNeeds;
    private final boolean immunized;
    
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
    
}
