package com.pawpals.session;

import com.pawpals.beans.User;

public class Session {
	public final static Session session = new Session();
	private User user;
	
	private Session() {}
	
	public User getUser() {return user;}
	
	public void setUser(User user) {
		if (this.user != null) return;
			this.user = user;
	}
	
}
