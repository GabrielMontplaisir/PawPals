package com.pawpals.servlets.walker;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.pawpals.beans.User;
import com.pawpals.beans.Walk;
import com.pawpals.dao.WalkDao;
import com.pawpals.dao.WalkOfferDao;
import com.pawpals.services.SessionService;

@WebServlet("/dashboard/cancel-offer")
public class CancelWalkOfferServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	User user = SessionService.srv.getSessionUser(req);
    	if (user == null) { 
    		resp.sendRedirect("../login"); 
    		return;
    	}
    	
        int walkId = Integer.parseInt(req.getParameter("id"));
        
        Walk walk =  WalkDao.getDao().getWalkById(walkId);
        
        if (walk == null) {
        	System.out.println("Error: Could not cancel services. Walk not found.");
        	resp.sendRedirect("./walker");
            return;
        }
        WalkOfferDao.getDao().cancelWalkOffer(walkId, user.getId());
        resp.sendRedirect("./walkdetails?id="+walkId);
    }
    
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	doPost(req, resp);
    }
    
}

