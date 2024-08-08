package com.pawpals.servlets.pages;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pawpals.beans.Dog;
import com.pawpals.beans.User;
import com.pawpals.dao.DogDao;
import com.pawpals.dao.UserDao;
import com.pawpals.libs.Validation;
import com.pawpals.libs.services.SessionService;

@WebServlet("/dashboard/profile")
public class ProfileServlet extends Validation {
	private static final long serialVersionUID = 1L;
	private final String PROFILE_JSP = "profile.jsp";
	private final String ACTION_INFO = "action_info";
	private final String ACTION_PASS = "action_pass";
	private final String UPDATE_DOG = "update_dog";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = SessionService.srv.getSessionUser(req);
		
		if (user == null) {
			resp.sendRedirect("../login");
			return;
		}
		
		Map<Integer, Dog> dogs = user.getDogList();
		
		req.setAttribute("dogs", dogs);
		if (req.getAttribute(ACTION_INFO) == null) req.setAttribute(ACTION_INFO, "disabled");
		if (req.getAttribute(ACTION_PASS) == null) req.setAttribute(ACTION_PASS, "disabled");
		
		if (req.getParameter("id") != null) {
			Dog dog = dogs.get(Integer.parseInt(req.getParameter("id")));
			req.setAttribute(UPDATE_DOG, "enabled");
			req.setAttribute("dog", dog);
		};
		
		req.getRequestDispatcher("./"+PROFILE_JSP).forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = SessionService.srv.getSessionUser(req);
		
		String message = null;
		boolean updated = false;
		
		if (user == null) {
			resp.sendRedirect("../login");
			return;
		}
		
		// Update User Information
		String action_info = req.getParameter(ACTION_INFO) != null ? req.getParameter(ACTION_INFO) : "";
		String action_pass = req.getParameter(ACTION_PASS) != null ? req.getParameter(ACTION_PASS) : "";
		
		if (action_info.equals("update")) {
			req.setAttribute(ACTION_INFO, "");
		} else if (action_info.equals("save")) {
			req.setAttribute(ACTION_INFO, "disabled");
			message = validateForm(req.getParameterMap());
			
			String firstName = req.getParameter("firstname");
			String lastName = req.getParameter("lastname");
			String email = req.getParameter("email");
			LocalDate dob = LocalDate.parse(req.getParameter("dob"));
			
			if (!user.getEmail().equals(email) && UserDao.getDao().userExists(email)) message = "User with that email already exists. Cannot update email.";
			if (user.getFirstName().equals(firstName) 
					&& user.getLastName().equals(lastName) 
					&& user.getEmail().equals(email) 
					&& user.getDob().equals(dob)
				) {
				message = "No info updated.";
			}
			if (message != null) {
				req.setAttribute("message", message);
			} else {
				user.setFirstName(firstName);
				user.setLastName(lastName);
				user.setEmail(email);
				user.setDob(dob);
				
				updated = UserDao.getDao().updateUser(firstName, lastName, email, dob.toString(), user.getUserId());
				if (updated) {
					req.setAttribute("success", "Profile updated.");
				}
			}
			
			
		}
		
		// Update Password
		if (action_pass.equals("update")) {
			req.setAttribute(ACTION_PASS, "");
		} else if (action_pass.equals("save")) {
			req.setAttribute(ACTION_PASS, "disabled");
			String currentPass = req.getParameter("password");
			String newPass = req.getParameter("new_password");
			String confirmNew = req.getParameter("confirm_new");
			
			message = validatePasswordForm(currentPass, newPass, confirmNew, user);
			
			if (message != null) {
				req.setAttribute("message", message);
			} else {
				updated = UserDao.getDao().updatePassword(user.getUserId(), newPass);
				if (updated) {
					req.setAttribute("success", "Password updated.");
				}
			}
		}
		
		// Update Dog
		if (req.getParameter("dog_id") != null && req.getParameter("action").equals("update-dog")) {
			Map<Integer, Dog> dogs = user.getDogList();
			int dogId = Integer.parseInt(req.getParameter("dog_id"));
			Dog dog = dogs.get(dogId);
			
			
	    	String name = req.getParameter("name");
	    	String size = req.getParameter("size");
	    	String specialNeeds = req.getParameter("specialneeds");
	    	boolean immunized = req.getParameter("immunized") != null;
	    	
	    	if (isEmpty(name) || isEmpty(size)) message = "Name & Size cannot be empty.";
			if (dog.getName().equals(name) 
					&& dog.getSize().equals(size)
					&& dog.getSpecialNeeds().equals(specialNeeds)
					&& dog.isImmunized() == immunized) message = "No changes made to dog.";
			if (message != null) {
				req.setAttribute("message", message);
			} else {
				dog.setName(name);
				dog.setSize(size);
				dog.setSpecialNeeds(specialNeeds);
				dog.setImmunized(immunized);
				
				dogs.replace(dog.getDogId(), dog);
				
				updated = DogDao.getDao().updateDog(dogId, name, size, specialNeeds, immunized);
				if (updated) {
					req.setAttribute("success", "Dog updated.");
				}
			}

		}
		

		
		doGet(req, resp);
	}
	
	@Override
	protected String validateForm(Map<String, String[]> params) {
		Iterator<Entry<String, String[]>> iterator = params.entrySet().iterator();
		
		while (iterator.hasNext()) {
			Map.Entry<String, String[]> entry = (Entry<String, String[]>)iterator.next();
			String paramName = entry.getKey();
			String paramValue = entry.getValue()[0];
			
			if (isEmpty(paramName)) {return paramName+" cannot be empty.";}
			if (paramName.equals("email")) {
				if (!paramValue.matches(emailRegEx)) return "Email is invalid.";
				
			}
			
			
		}
		return null;
	}
	
	protected String validatePasswordForm(String currentPass, String newPass, String confirmNew, User user) {
		if (isEmpty(currentPass)) return "Password cannot be empty.";
		if (!isEmpty(currentPass) && (isEmpty(newPass) || isEmpty(confirmNew))) return "New password cannot be blank and needs to be confirmed.";
		if (currentPass.equals(newPass)) return "New password cannot be identical to current password";
		if (!confirmNew.equals(newPass)) return "New password and confirmation do not match";
		if (!UserDao.getDao().passwordMatches(user.getEmail(), currentPass)) return "Password does not match account password.";
		return null;	
	}
}
