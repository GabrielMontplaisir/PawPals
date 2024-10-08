package com.pawpals.servlets.pages;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pawpals.beans.User;
import com.pawpals.beans.Walk;
import com.pawpals.dao.WalkDao;
import com.pawpals.libs.services.SessionService;

@WebServlet("/dashboard/walker")
public class WalkerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = SessionService.srv.getSessionUser(req);
		
		if (user == null) {
			resp.sendRedirect("../login");
			return;
		}
		
		user.setOwnerMode(false);
		
		HashMap<Integer, Boolean> walkOffers = new HashMap<>();
		List<Walk> walks = (List<Walk>) WalkDao.getDao().getWalksPostedForReceivingOffers(user.getUserId(), walkOffers);
		List<Walk> userWalks = (List<Walk>) WalkDao.getDao().getWalksByWalkerId(user.getUserId());
		
		req.setAttribute("walks", walks);		
		req.setAttribute("walkOffers", walkOffers);
		req.setAttribute("userWalks", userWalks);
		
		req.getRequestDispatcher("walker.jsp").forward(req, resp);
	}

}
