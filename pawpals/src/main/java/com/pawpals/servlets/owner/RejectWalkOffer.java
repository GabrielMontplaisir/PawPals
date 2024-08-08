package com.pawpals.servlets.owner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.pawpals.beans.User;
import com.pawpals.beans.Walk;
import com.pawpals.dao.WalkOfferDao;
import com.pawpals.libs.services.SessionService;

@WebServlet("/dashboard/reject-offer")
public class RejectWalkOffer extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	User user = SessionService.srv.getSessionUser(req);
    	if (user == null) { 
    		resp.sendRedirect("../login"); 
    		return;
    	}
        int walkId = Integer.parseInt(req.getParameter("id"));
        Walk walk =  user.getWalkList().get(walkId);
        if (walk == null || walk.getOwnerId() != user.getUserId()) {
        	System.out.println("Error: Could not reject walker. Walk not found or user not owner.");
            resp.sendRedirect("./owner");
            return;
        }
        
        int walkerId = Integer.parseInt(req.getParameter("walker"));
        if (!WalkOfferDao.getDao().walkerOffered(walkId, walkerId)) {
        	System.out.println("Error: Could not reject walker. Walker did not apply.");
        	return;
        }
        
        WalkOfferDao.getDao().rejectWalkOffer(walkId, walkerId);
        resp.sendRedirect("./walkdetails?id="+walkId);
    }
    
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	doPost(req, resp);
    }
    
}

