package com.pawpals.servlets.pages;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pawpals.beans.Dog;
import com.pawpals.beans.User;
import com.pawpals.beans.Walk;
import com.pawpals.libs.services.SessionService;

@WebServlet("/dashboard/owner")
public class OwnerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = SessionService.srv.getSessionUser(req);
		
		
		
		//String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		
		// Calculate a minimum time for when a dog walk can be started
		LocalDateTime plus3Hours = LocalDateTime.now().plusHours(3);
		LocalDateTime truncatedTime = plus3Hours.truncatedTo(ChronoUnit.HOURS);
		String minDate = truncatedTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		
		String date = minDate;
		
		if (user == null) {
			resp.sendRedirect("../login");
			return;
		}
		
		SessionService.srv.showErrorMessage(req);
		user.setOwnerMode(true);
		
		Map<Integer, Dog> dogs = user.getDogList();
		Map<Integer, Walk> walks = user.getWalkList();
		
		req.setAttribute("userDogs", dogs);
		req.setAttribute("walks", walks);
		req.setAttribute("date", date);
		req.setAttribute("minDate", minDate);
		
		req.getRequestDispatcher("owner.jsp").forward(req, resp);
	}

}
