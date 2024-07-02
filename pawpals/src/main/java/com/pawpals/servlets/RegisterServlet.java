package com.pawpals.servlets;

import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import com.pawpals.dao.UserDao;
import com.pawpals.interfaces.FormValidation;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/register")
public class RegisterServlet extends FormValidation {
	private static final long serialVersionUID = 1L;

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String message;	
		RequestDispatcher dispatcher = null;
		message = validateForm(req.getParameterMap());

		if (message != null) {
			req.setAttribute("message", message);
			dispatcher = req.getRequestDispatcher("register.jsp");
			dispatcher.forward(req, resp);
			return;
		}
		
		UserDao.userDao.createUser(req);
		HttpSession session = req.getSession();
		if (session.getAttribute("user") != null) {
			resp.sendRedirect("./account-panel");
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
				if (UserDao.userDao.userExists(paramValue)) return "User already exists. Please login using your email & password.";
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
