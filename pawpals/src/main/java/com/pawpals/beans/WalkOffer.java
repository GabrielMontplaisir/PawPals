package com.pawpals.beans;

public class WalkOffer {
	private User walkerUser;
	private Walk walk;
	private boolean declined;
	
	// Constructor 
	
	public WalkOffer(Walk walk, User walkerUser, boolean declined) {
		this.walk = walk;
		this.walkerUser = walkerUser;
		this.declined = declined;
	}
	
	// Getter Methods
	
	public User getWalkOfferUser() { return this.walkerUser; }
	public Walk getWalk() { return this.walk; }
	public boolean getDeclined() { return this.declined;}
}
