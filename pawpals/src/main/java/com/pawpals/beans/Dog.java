package com.pawpals.beans;

public class Dog {
    private final int dogId;
    private final int ownerId;
    private final String name;
    private final String size;
    private final String specialNeeds;
    private final boolean immunized;

    public Dog(int dogId, int ownerId, String name, String size, String specialNeeds, boolean immunized) {
        this.dogId = dogId;
        this.ownerId = ownerId;
        this.name = name;
        this.size = size;
        this.specialNeeds = specialNeeds;
        this.immunized = immunized;
    }

    public int getDogId() {
        return dogId;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public String getSpecialNeeds() {
        return specialNeeds;
    }

    public boolean isImmunized() {
        return immunized;
    }
}
