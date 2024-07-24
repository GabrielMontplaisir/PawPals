package com.pawpals.servlets.walker;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.pawpals.beans.User;
import com.pawpals.beans.Walk;
import com.pawpals.dao.NotificationDao;
import com.pawpals.dao.WalkDao;
import com.pawpals.libs.WalkStatus;
import com.pawpals.libs.builders.NotificationBuilder;
import com.pawpals.libs.services.SessionService;

@WebServlet("/dashboard/start-walk-walker")
public class StartWalkServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	User user = SessionService.srv.getSessionUser(req);
    	if (user == null) { 
    		resp.sendRedirect("../login");
    		return;
    	}

        int walkId = Integer.parseInt(req.getParameter("id"));
        Walk walk = user.getCachedWalks().get(walkId);

        if (walk == null || walk.getWalkerId() != user.getId()) {
        	System.out.println("Error: Could not start walk. Walk not found or user not walker.");
            resp.sendRedirect("./walker");
            return;
        }
        
              
        walk.setStatus(WalkStatus.WALKER_STARTED);
        WalkDao.getDao().setStatus(walk.getWalkId(), walk.getStatus());
        
    	walk.notifyObservers(NotificationDao.getDao().createNotificationForUser(
        		new NotificationBuilder()
	        		.setUserId(walk.getOwnerId())
	        		.setTitle(user.getFirstName()+" "+user.getLastName()+" started a walk.")
	        		.setDescription("Walk in "+walk.getLocation()+" on "+walk.getShortDate())
	        		.setUrl(req.getContextPath()+"/dashboard/walkdetails?id="+walkId)
	        		.create()
    			)
        );
        
        resp.sendRedirect("./walkdetails?id="+walkId);
    }
    
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	doPost(req, resp);
    }
    
}

