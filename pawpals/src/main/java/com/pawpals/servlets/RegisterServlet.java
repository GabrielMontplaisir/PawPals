package com.pawpals.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import com.pawpals.dao.UserDao;
import com.pawpals.interfaces.AuthValidation;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/register")
public class RegisterServlet extends AuthValidation {
	private static final long serialVersionUID = 1L;

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String message;
		message = validateForm(req.getParameterMap());

		if (message != null) {
			req.setAttribute("message", message);
			req.getRequestDispatcher("register.jsp").forward(req, resp);
			return;
		}
		
		UserDao.dao.createUser(req);
		HttpSession session = req.getSession();
		if (session.getAttribute("user") != null) {
			resp.sendRedirect("./dashboard/profile.jsp");
			return;
		} else {
			req.setAttribute("message", "We could not create a user at this time. Please try again later.");
		}
	}
	
	@Override
	protected String validateForm(Map<String,String[]> params) {
		Iterator<Entry<String, String[]>> iterator = params.entrySet().iterator();
		
		while (iterator.hasNext()) {
			Map.Entry<String, String[]> entry = (Entry<String, String[]>)iterator.next();
			String paramName = entry.getKey();
			String paramValue = entry.getValue()[0];
			
			if (isEmpty(paramName)) {return paramName+" cannot be empty.";}
			if (paramName.equals("email")) {
				if (!paramValue.matches(emailRegEx)) return "Email is invalid.";
				if (UserDao.dao.userExists(paramValue)) return "User already exists. Please login using your email & password.";
			}
				
			if (paramName.equals("dob") && !meetsAgeReq(paramValue, 18)) {
				return "You are too young to use this site.";
			}
		}
		return null;
	}
	
	private boolean meetsAgeReq(String userDOB, int minAge) {
		LocalDate now = LocalDate.now();
		LocalDate userBirthday = LocalDate.parse(userDOB);
		Period period = Period.between(userBirthday, now);
		return period.getYears() >= minAge;		
	}
}
