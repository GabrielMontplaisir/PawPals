package com.pawpals.interfaces;

public enum WalkStatus {
		OWNER_INITIALIZED(1), // Dog owner has created walk but can still edit details before it's visible
		OWNER_POSTED(2), // Dog owner has posted the walk for walkers to propose on
		WALKER_CHOSEN(3), // Dog owner has selected a walker
		WALKER_STARTED(4), // Walker has marked the walk as started (optional)
		WALKER_COMPLETED(5), // Walker has marked the walk as completed
		CANCELLED(6); // Walker or Dog owner have cancelled

		private final int statusCode;

		WalkStatus(int statusCode) {
			this.statusCode = statusCode;
		}

		public int toInt() {
			return statusCode;
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