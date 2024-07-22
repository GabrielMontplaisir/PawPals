package com.pawpals.servlets.walker;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.pawpals.beans.*;
import com.pawpals.dao.NotificationDao;
import com.pawpals.dao.WalkDao;
import com.pawpals.dao.WalkOfferDao;
import com.pawpals.interfaces.NotificationBuilder;
import com.pawpals.services.*;

@WebServlet("/dashboard/create-offer")
public class CreateWalkOfferServlet extends HttpServlet {
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
        	System.out.println("Error: Could not offer services. Walk not found.");
        	resp.sendRedirect("./walker");
            return;
        }
        
        WalkOfferDao.getDao().addWalkOffer(walkId, user.getId());
        NotificationDao.getDao().createNotificationForUser(new NotificationBuilder()
        		.setUserId(walk.getOwnerId())
        		.setTitle(user.getFirstName()+" "+user.getLastName()+" offered their services for your walk.")
        		.setDescription("Walk in "+walk.getLocation()+" on "+walk.getShortDate())
        		.setReadStatus(false)
        		.setUrl(req.getContextPath()+"/dashboard/walkdetails?id="+walkId)
        		.create());
        resp.sendRedirect("./walkdetails?id="+walkId);
    }
    
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	doPost(req, resp);
    }
    
}

