package com.pawpals.libs.interfaces;

import com.pawpals.beans.Notification;

public interface Subject {
	void subscribe(Observer o);
	void unsubscribe(Observer o);
	void notifyObservers(Notification notif);
}
