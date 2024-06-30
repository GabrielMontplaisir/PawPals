package com.pawpals.beans;

import java.util.List;

import com.pawpals.dao.WalkDao;

public class Walk {
	public Walk(int walkId, int status, int owner_id, String date, String location, String length) {
		super();
		this.walkId = walkId;
		this.status = status;
		this.owner_id = owner_id;
		this.date = date;
		this.location = location;
	}
	
	public List<Dog> getDogs(){
		return WalkDao.dao.getDogs(this);
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getWalker_id() {
		return walker_id;
	}
	public void setWalker_id(int walker_id) {
		this.walker_id = walker_id;
	}
	public int getWalkId() {
		return walkId;
	}
	public int getOwnerId() {
		return owner_id;
	}
	public String getDate() {
		return date;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	private int walkId;
	private int status;
	private int owner_id;
	private String date;
	private String location;
	private String length;
	private int walker_id;
	
	
}
