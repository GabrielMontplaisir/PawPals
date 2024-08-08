package com.pawpals.beans;

public class WalkOffer {
	private User walkerUser;
	private Walk walk;
	private boolean declined;
	private String comment;
	
	// Constructor 
	
	public WalkOffer(Walk walk, User walkerUser, boolean declined, String comment) {
		this.walk = walk;
		this.walkerUser = walkerUser;
		this.declined = declined;
		this.comment = comment; 
	}
	
	// Getter Methods
	
	public User getWalkOfferUser() { return this.walkerUser; }
	public Walk getWalk() { return this.walk; }
	public boolean getDeclined() { return this.declined;}
	public String getComment() { return this.comment; } 
}
