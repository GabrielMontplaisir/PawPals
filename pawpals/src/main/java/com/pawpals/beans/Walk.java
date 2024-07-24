package com.pawpals.beans;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;

import com.pawpals.libs.WalkStatus;
import com.pawpals.libs.interfaces.Observer;
import com.pawpals.libs.interfaces.Subject;

public class Walk implements Subject {
	private int walkId, ownerId, walkerId, offerCount;
	private WalkStatus status;
	private String date, location, length, dogNames;
	private User owner, walker;
	private ArrayList<Observer> observers = new ArrayList<>();
	
	// Constructor
	
	public Walk(int walkId, WalkStatus status, int ownerId, String date, String location, String length, int walkerId) {
		this.walkId = walkId;
		this.status = status;
		this.ownerId = ownerId;
		this.date = date;
		this.location = location;
		this.length = length;
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
	public String getDogNames() { return dogNames; }
	public WalkStatus getStatus() {return this.status;}

	public boolean isFinished() {
		return this.status == WalkStatus.CANCELLED || this.status == WalkStatus.WALKER_COMPLETED;
	}
	
	public void setOfferCount(int offerCount) {
		this.offerCount = offerCount;
	}
	
	public void setDogNames(String dogNames) {
		this.dogNames = dogNames;
	}
	
	public void setOwner(User owner) {
		this.owner = owner;
		this.subscribe(owner);
	}
	
	public void setWalker(User walker) {
		this.walker = walker;
		this.subscribe(walker);
	}
	
	public void setStatus(WalkStatus status) {
		this.status = status;
	}
	
	// From Subject Interface

	@Override
	public void subscribe(Observer o) {
		observers.add(o);		
	}

	@Override
	public void unsubscribe(Observer o) {
		observers.remove(o);		
	}

	@Override
	public void notifyObservers(Notification notif) {
		for (Observer obs : observers) {
			obs.update(notif);
		}
	}

	public int compareTo(Walk o) {
		return Comparator.comparing(Walk::getWalkId)
				.thenComparing(Walk::getStatus)
				.thenComparing(Walk::getOwnerId)
				.thenComparing(Walk::getFullDate)
				.thenComparing(Walk::getShortDate)
				.thenComparing(Walk::getLength)
				.thenComparing(Walk::getOfferCount)
				.thenComparing(Walk::getDogNames)
				.thenComparing(Walk::getLocation)
				.compare(this, o);
	}
	
	

	
}
