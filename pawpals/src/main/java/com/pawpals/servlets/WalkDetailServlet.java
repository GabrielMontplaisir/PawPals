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
    		resp.sendRedirect("../index.jsp");
    		return;
    	}
    	
    	if (req.getParameter("id") == null) {
    		resp.sendRedirect("./profile.jsp");
    		return;
    	}
    	
    	int walkId = Integer.parseInt((String) req.getParameter("id"));
    	
    	if ( ! (walkId > 0) ) { resp.sendRedirect("./profile.jsp"); return; }
    	Walk walk = WalkDao.dao.getWalkById(walkId);
    	if ( walk == null )  { resp.sendRedirect("./profile.jsp"); return; }
    	
//    	if ( walk.getOwnerId() != user.getId()  ) {
//    		 resp.sendRedirect("./profile.jsp"); return;
//    	}
    	
    	List<WalkOffer> offers = WalkDao.dao.getWalkOffers(walkId);
    	
    	req.setAttribute("walk", walk);
    	req.setAttribute("offers", offers);
    	req.setAttribute("offer", WalkDao.dao.walkerOffered(walkId, user.getId()));
    	
    	req.getRequestDispatcher("./walk-detail.jsp").forward(req, resp);
    }
    
}

