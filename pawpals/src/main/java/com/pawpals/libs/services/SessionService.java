package com.pawpals.libs.services;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.pawpals.beans.User;

public class SessionService {
	public static SessionService srv = new SessionService();
	
	public User getSessionUser(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) { return null; }
        User user = (User) session.getAttribute("user");
        if (user == null) {  return null; }
		return user;
	}
	
	public void showErrorMessage(HttpServletRequest req) {
		if (req.getSession(false).getAttribute("message") != null) {
			req.setAttribute("message", req.getSession(false).getAttribute("message"));
			req.getSession(false).removeAttribute("message");
		}
	}
	
	
}
