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
import com.pawpals.interfaces.WalkStatus;
import com.pawpals.services.SessionService;

@WebServlet("/dashboard/start-walk-walker")
public class StartWalkServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	User user = SessionService.srv.getSessionUser(req);
    	if (user == null) { 
    		resp.sendRedirect("../index.jsp");
    		return;
    	}

        int walkId = Integer.parseInt(req.getParameter("id"));
        Walk walk = WalkDao.dao.getWalkById(walkId);

        if (walk == null || walk.getWalkerId() != user.getId()) {
        	System.out.println("Error: Could not start walk. Walk not found or user not walker.");
            resp.sendRedirect("./walker.jsp");
            return;
        }
        
              
        walk.setStatus(WalkStatus.WALKER_STARTED);
        WalkDao.dao.setStatus(walk.getWalkId(), walk.getStatus());
        resp.sendRedirect("./walkdetails?id="+walkId);
    }
    
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	doPost(req, resp);
    }
    
}

