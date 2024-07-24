package com.pawpals.libs.builders;

import com.pawpals.beans.Walk;
import com.pawpals.libs.WalkStatus;

public class WalkBuilder {
	private int walkId, ownerId, walkerId;
	private WalkStatus status;
	private String date, location, length;
	
	public WalkBuilder setWalkId(int walkId) {
		this.walkId = walkId;
		return this;
	}
	
	public WalkBuilder setOwnerId(int ownerId) {
		this.ownerId = ownerId;
		return this;
	}
	
	public WalkBuilder setWalkerId(int walkerId) {
		this.walkerId = walkerId;
		return this;
	}
	
	public WalkBuilder setStatus(int status) {
		this.status = WalkStatus.fromInt(status);
		return this;
	}
	
	public WalkBuilder setStatus(WalkStatus status) {
		this.status = status;
		return this;
	}

	public WalkBuilder setLocation(String location) {
		this.location = location;
		return this;
	}

	public WalkBuilder setLength(String length) {
		this.length = length;
		return this;
	}
	
	public WalkBuilder setDate(String date) {
		this.date = date;
		return this;
	}
	
	public Walk create() {
		return new Walk(walkId, status, ownerId, date, location, length, walkerId);
	}
}
