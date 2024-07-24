package com.pawpals.libs;

public enum WalkStatus {
		OWNER_INITIALIZED(1, "Owner Draft"), // Dog owner has created walk but can still edit details before it's visible
		OWNER_POSTED(2, "Accepting Offers"), // Dog owner has posted the walk for walkers to propose on
		WALKER_CHOSEN(3, "Walker Selected"), // Dog owner has selected a walker
		WALKER_STARTED(4, "Walking Now"), // Walker has marked the walk as started (optional)
		WALKER_COMPLETED(5, "Completed"), // Walker has marked the walk as completed
		CANCELLED(6, "Cancelled"); // Walker or Dog owner have cancelled

		private final int statusCode;
		private final String statusKey;

		WalkStatus(int statusCode, String statusKey) {
			this.statusCode = statusCode;
			this.statusKey = statusKey;
		}

		public int toInt() {
			return statusCode;
		}
		
		public String toString() {
			return statusKey;
		}

		public static WalkStatus fromInt(int statusCode) {
			for (WalkStatus type : WalkStatus.values()) {
				if (type.statusCode == statusCode) {
					return type;
				}
			}
			throw new IllegalArgumentException("Invalid WalkStatus: " + statusCode);
		}
	}