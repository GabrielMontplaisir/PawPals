package com.pawpals.beans;
public class WalkOffer {
	private User walkerUser;
	private Walk walk;
	private int declined;
	public WalkOffer(Walk walk, User walkerUser, int declined) {
		super();
		this.walk = walk;
		this.walkerUser = walkerUser;
		this.declined = declined;
	}
	public User getWalkOfferUser() { return this.walkerUser; }
	public Walk getWalk() { return this.walk; }
	public int getDeclined() { return this.declined;}
}
