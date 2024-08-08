package com.pawpals.servlets.owner;

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

@WebServlet("/dashboard/cancel-walk")
public class CancelWalkServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	User user = SessionService.srv.getSessionUser(req);
    	if (user == null) { 
    		resp.sendRedirect("../login");
    		return;
    	}

        int walkId = Integer.parseInt(req.getParameter("id"));
        Walk walk = user.getWalkList().get(walkId);

        if (walk == null || walk.getOwnerId() != user.getUserId()) {
        	System.out.println("Error: Could not cancel walk. Walk not found or user not owner.");
            resp.sendRedirect("./owner");
            return;
        }
        
              
        walk.setStatus(WalkStatus.CANCELLED);
        WalkDao.getDao().cancel(walk.getWalkId());
        
        walk.unsubscribe(user);
        if (walk.getWalkerId() != 0) {
        	walk.notifyObservers(NotificationDao.getDao().createNotificationForUser(
            		new NotificationBuilder()
    	        		.setUserId(walk.getWalkerId())
    	        		.setTitle(user.getFirstName()+" "+user.getLastName()+" cancelled a walk.")
    	        		.setDescription("Walk in "+walk.getLocation()+" on "+walk.getShortDate())
    	        		.setUrl(req.getContextPath()+"/dashboard/walkdetails?id="+walkId)
    	        		.create()
        			)
            );
        }
        
        
        resp.sendRedirect("./owner");
    }
    
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	doPost(req, resp);
    }
    
}

