package com.pawpals.servlets.owner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.pawpals.beans.User;
import com.pawpals.beans.Walk;
import com.pawpals.dao.WalkDao;
import com.pawpals.services.SessionService;

@WebServlet("/dashboard/accept-offer")
public class AcceptWalkOffer extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	User user = SessionService.srv.getSessionUser(req);
    	if (user == null) { 
    		resp.sendRedirect("../"); 
    		return;
    	}
        int walkId = Integer.parseInt(req.getParameter("id"));
        Walk walk =  WalkDao.dao.getWalkById(walkId);
        if (walk == null || walk.getOwnerId() != user.getId()) {
        	System.out.println("Error: Could not accept walker. Walk not found or user not owner.");
            resp.sendRedirect("./owner");
            return;
        }
        
        int walkerId = Integer.parseInt(req.getParameter("walker"));
        if (!WalkDao.dao.walkerOffered(walkId, walkerId)) {
        	System.out.println("Error: Could not accept walker. Walker did not apply.");
        	return;
        }
        
        WalkDao.dao.acceptWalkOffer(walkId, walkerId);
        resp.sendRedirect("./walkdetails?id="+walkId);
    }
    
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	doPost(req, resp);
    }
    
}

