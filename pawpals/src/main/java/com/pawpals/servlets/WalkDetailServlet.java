package com.pawpals.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import com.pawpals.beans.*;
import com.pawpals.dao.WalkDao;
import com.pawpals.dao.WalkDogDao;
import com.pawpals.dao.WalkOfferDao;
import com.pawpals.libs.services.*;

@WebServlet("/dashboard/walkdetails")
public class WalkDetailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	User user = SessionService.srv.getSessionUser(req);
    	
    	if (user == null) { 
    		resp.sendRedirect("../login");
    		return;
    	}
    	
    	// Check if ID exists
    	if (req.getParameter("id") == null) {
    		resp.sendRedirect(req.getHeader("referer"));
    		return;
    	}
    	
    	int walkId = Integer.parseInt((String) req.getParameter("id"));
    	
    	if ( ! (walkId > 0) ) { 
    		resp.sendRedirect(req.getHeader("referer")); 
    		return; 
    	}
    	
    	// Get the walk information
		Walk temp = WalkDao.getDao().getWalkById(walkId);
    	
		// Check if the user owns the walk or has it in their cached walks.
    	Walk walk;
    	if (user.getWalkList().containsKey(walkId)) {
    		walk = replaceWalk(temp, user.getWalkList(), walkId);
    	} else if (user.getCachedWalks().containsKey(walkId)) {
    		walk = replaceWalk(temp, user.getCachedWalks(), walkId);
    	} else {
    		user.getCachedWalks().put(temp.getWalkId(), temp);
    		walk = temp;
    	}
    	
    	// If walk doesn't exist, then we'll send them to a 404 page.
    	if ( walk == null )  { 
    		resp.sendRedirect("../404.jsp");
    		return; 
    	}
    	
    	// For case of edit, calculate minimum start time for walk
		LocalDateTime plus3Hours = LocalDateTime.now().plusHours(3);
		LocalDateTime truncatedTime = plus3Hours.truncatedTo(ChronoUnit.HOURS);
		String minDate = truncatedTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
		
    	
    	
    	// Retrieve the dog information and walk offers in the DAOs.
    	List<Dog> walkDogs = WalkDogDao.getDao().getWalkDogs(walkId);
    	List<WalkOffer> offers = WalkOfferDao.getDao().getWalkOffers(walkId);
    	boolean walkOffered = WalkOfferDao.getDao().walkerOffered(walkId, user.getUserId());
    	List<Integer> walkDogIds = walkDogs.stream().map(Dog::getDogId).toList();
    	
    	String action = req.getParameter("action") != null ? (String) req.getParameter("action") : "";
    	
    	if (!action.isEmpty() && action.equals("edit")) {
    		req.setAttribute("action", "edit");
    	}
    	
    	req.setAttribute("userDogs", user.getDogList());
    	req.setAttribute("walk", walk);
    	req.setAttribute("date", walk.getShortDate());
    	req.setAttribute("minDate", minDate);
    	req.setAttribute("walkDogs", walkDogs);
    	req.setAttribute("dogIds", walkDogIds);
    	req.setAttribute("offers", offers);
    	req.setAttribute("offer", walkOffered);
    	
    	req.getRequestDispatcher("walk-detail.jsp").forward(req, resp);
    }
    
    
    // Compare the temporary walk to the one in the user's walks stored in memory.
    // If they are different, then updated the walk in the application memory.
    private Walk replaceWalk(Walk temp, Map<Integer, Walk> list, int walkId) {
    	Walk walk = list.get(walkId);
    	if (temp.compareTo(walk) != 0) {
    		walk = temp;
    		list.replace(walk.getWalkId(), walk);
    	}
    	
    	return walk;
    }
    
}

