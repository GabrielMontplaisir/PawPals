package com.pawpals.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
    	
    	if (req.getParameter("id") == null) {
    		resp.sendRedirect(req.getHeader("referer"));
    		return;
    	}
    	
    	int walkId = Integer.parseInt((String) req.getParameter("id"));
    	
    	if ( ! (walkId > 0) ) { 
    		resp.sendRedirect(req.getHeader("referer")); 
    		return; 
    	}
    	
		Walk temp = WalkDao.getDao().getWalkById(walkId);
    	
    	Walk walk;
    	if (user.getWalkList().containsKey(walkId)) {
    		walk = replaceWalk(temp, user.getWalkList(), walkId);
    	} else if (user.getCachedWalks().containsKey(walkId)) {
    		walk = replaceWalk(temp, user.getCachedWalks(), walkId);
    	} else {
    		user.getCachedWalks().put(temp.getWalkId(), temp);
    		walk = temp;
    	}
    	
    	if ( walk == null )  { 
    		resp.sendRedirect("../404.jsp");
    		return; 
    	}
    	
//    	if ( walk.getOwnerId() != user.getId()  ) {
//    		 resp.sendRedirect("./profile.jsp"); return;
//    	}
    	
    	List<Dog> dogs = WalkDogDao.getDao().getWalkDogs(walkId);
    	List<WalkOffer> offers = WalkOfferDao.getDao().getWalkOffers(walkId);
    	boolean walkOffered = WalkOfferDao.getDao().walkerOffered(walkId, user.getUserId());
    	 	
    	req.setAttribute("walk", walk);
    	req.setAttribute("dogs", dogs);
    	req.setAttribute("offers", offers);
    	req.setAttribute("offer", walkOffered);
    	
    	req.getRequestDispatcher("walk-detail.jsp").forward(req, resp);
    }
    
    private Walk replaceWalk(Walk temp, Map<Integer, Walk> list, int walkId) {
    	Walk walk = list.get(walkId);
    	if (temp.compareTo(walk) != 0) {
    		walk = temp;
    		list.replace(walk.getWalkId(), walk);
    	}
    	
    	return walk;
    }
    
}

