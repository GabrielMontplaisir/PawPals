package com.pawpals.beans;

import java.util.List;

import com.pawpals.services.WalkService;

public class Walk {
	public Walk(int walkId, int intStatus, int owner_id, String date, String location, String length) {
		super();
		this.walkId = walkId;
		this.status = EnumStatus.fromInt(intStatus);
		this.owner_id = owner_id;
		this.date = date;
		this.location = location;
	}
	public int getStatus() {
		return status.toInt();
	}
	public String getFriendlyStatus() {
		switch (this.status) {
			case OWNER_INITIALIZED: return "Owner Draft";
			case OWNER_POSTED: return "Accepting Walk Offers";
			case WALKER_CHOSEN: return "Walker Selected";
			case WALKER_STARTED: return "Walking Now";
			case WALKER_COMPLETED: return "Walk Completed";
			case CANCELLED: return "Walk Cancelled";
			default: System.err.println("getFriendlyStatus() encountered unexpected status: " + this.status);
			return "Error";
		}
	}
	public void setIntStatus(int intStatus) {
		this.status = EnumStatus.fromInt(intStatus);
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
	public int getOfferCount() {
		return WalkService.svc.getOfferCount(walkId);
	}
	public List<Dog> getDogs() {
		return WalkService.svc.getDogs(walkId);
	}
	
	public boolean isCancelled() {
		return this.status == EnumStatus.CANCELLED;
	}
	
	private int walkId;
	private EnumStatus status;
	private int owner_id;
	private String date;
	private String location;
	private String length;
	private int walker_id;
	
	
	public static enum EnumStatus {
		OWNER_INITIALIZED(1), // Dog owner has created walk but can still edit details before it's visible
		OWNER_POSTED(2), // Dog owner has posted the walk for walkers to propose on
		WALKER_CHOSEN(3), // Dog owner has selected a walker
		WALKER_STARTED(4), // Walker has marked the walk as started (optional)
		WALKER_COMPLETED(5), // Walker has marked the walk as completed
		CANCELLED(6); // Walker or Dog owner have cancelled

		private final int statusCode;

		EnumStatus(int statusCode) {
			this.statusCode = statusCode;
		}

		public int toInt() {
			return statusCode;
		}

		public static EnumStatus fromInt(int statusCode) {
			for (EnumStatus type : EnumStatus.values()) {
				if (type.statusCode == statusCode) {
					return type;
				}
			}
			throw new IllegalArgumentException("Invalid EnumStatus: " + statusCode);
		}
	}

	
}
