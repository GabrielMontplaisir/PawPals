package com.pawpals.servlets;

import java.io.IOException;
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

@WebServlet("/login")
public class LoginServlet extends FormValidation {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String message;
		RequestDispatcher dispatcher = null;
		
		message = validateForm(req.getParameterMap());
		if (message != null) {
			req.setAttribute("message", message);
			dispatcher = req.getRequestDispatcher("index.jsp");
			dispatcher.forward(req, resp);
			return;
		}
		
		
		UserDao.userDao.authenticateUser(req);
		HttpSession session = req.getSession();
		if (session.getAttribute("user") != null) {
			resp.sendRedirect("./dashboard/");
			return;
		} else {
			req.setAttribute("message", "Cannot find user with this email address. Please register for an account.");
		}
	}
	
	@Override
	protected String validateForm(Map<String,String[]> params) {
		Iterator<Entry<String, String[]>> iterator = params.entrySet().iterator();
		String email = null;
		while (iterator.hasNext()) {
			Map.Entry<String, String[]> entry = (Entry<String, String[]>)iterator.next();
			String paramName = entry.getKey();
			String paramValue = entry.getValue()[0];

			
			if (isEmpty(paramName)) {return paramName+" cannot be empty.";}
			if (paramName.equals("email")) {
				if (!paramValue.matches(emailRegEx)) return "Email is invalid.";
				if (!UserDao.userDao.userExists(paramValue)) return "Cannot find user with this email address.";
				email = paramValue;
			}
			
			if (paramName.equals("password")) {
				if (!UserDao.userDao.passwordMatches(email, paramValue)) return "Email & Password do not match. Please try again.";
			}
				
		}
		return null;
	}
}
