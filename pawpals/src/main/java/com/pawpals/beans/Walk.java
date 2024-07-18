package com.pawpals.beans;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.pawpals.interfaces.WalkStatus;

public class Walk {
	private int walkId, ownerId, walkerId, offerCount;
	private WalkStatus status;
	private String date, location, length;
	private User owner, walker;
	
	// Constructor
	
	public Walk(int walkId, int intStatus, int ownerId, String date, String location, String length, int walkerId) {
		this.walkId = walkId;
		this.status = WalkStatus.fromInt(intStatus);
		this.ownerId = ownerId;
		this.date = date;
		this.location = location;
		this.walkerId = walkerId;
	}
	
	// Getter Methods
	
	public int getIntStatus() {return status.toInt();}
	public String getLocation() {return location;}
	public int getWalkerId() {return walkerId;}
	public int getWalkId() {return walkId;}
	public int getOwnerId() {return ownerId;}
	public String getFullDate() {return LocalDateTime.parse(date).format(DateTimeFormatter.ofPattern("E, MMM dd, yyyy 'at' HH:mm"));}
	public String getShortDate() {return LocalDateTime.parse(date).format(DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm"));}
	public String getLength() {return length;}
	public User getOwner() {return owner;}
	public User getWalker() {return walker;}
	public int getOfferCount() {return offerCount;}
	
	public WalkStatus getStatus() {
		return this.status;
	}
	
	// Setter Methods
		
	public void setStatus(WalkStatus status) {
		this.status = status;
	}
	public void setIntStatus(int intStatus) {
		this.status = WalkStatus.fromInt(intStatus);
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public boolean isFinished() {
		return this.status == WalkStatus.CANCELLED || this.status == WalkStatus.WALKER_COMPLETED;
	}
	
	public void setWalker(User walker) {
		this.walker = walker;
	}
	
	
	

	
}
