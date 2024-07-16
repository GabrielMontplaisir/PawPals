package com.pawpals.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.pawpals.beans.*;
import com.pawpals.dao.WalkDao;
import com.pawpals.services.*;

@WebServlet("/dashboard/walkdetails")
public class WalkDetailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	User user = SessionService.srv.getSessionUser(req);
    	if (user == null) { 
    		resp.sendRedirect("../");
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
    	
    	Walk walk = WalkDao.dao.getWalkById(walkId);
    	if ( walk == null )  { 
    		resp.sendRedirect("../404");
    		return; 
    	}
    	
//    	if ( walk.getOwnerId() != user.getId()  ) {
//    		 resp.sendRedirect("./profile.jsp"); return;
//    	}
    	
    	List<Dog> dogs = WalkDao.dao.getWalkDogs(walkId);
    	List<WalkOffer> offers = WalkDao.dao.getWalkOffers(walkId);
    	boolean walkOffered = WalkDao.dao.walkerOffered(walkId, user.getId());
    	
    	req.setAttribute("walk", walk);
    	req.setAttribute("dogs", dogs);
    	req.setAttribute("offers", offers);
    	req.setAttribute("offer", walkOffered);
    	
    	req.getRequestDispatcher("walk-detail.jsp").forward(req, resp);
    }
    
}

