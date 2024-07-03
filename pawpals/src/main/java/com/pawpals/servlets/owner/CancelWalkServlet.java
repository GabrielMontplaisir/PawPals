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
import com.pawpals.interfaces.WalkStatus;
import com.pawpals.services.SessionService;

@WebServlet("/dashboard/cancel-walk")
public class CancelWalkServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	User user = SessionService.srv.getSessionUser(req);
    	if (user == null) { 
    		resp.sendRedirect("../index.jsp");
    		return;
    	}

        int walkId = Integer.parseInt(req.getParameter("id"));
        Walk walk = WalkDao.dao.getWalkById(walkId);

        if (walk == null || walk.getOwnerId() != user.getId()) {
        	System.out.println("Error: Could not cancel walk. Walk not found or user not owner.");
            resp.sendRedirect("./owner.jsp");
            return;
        }
        
              
        walk.setIntStatus(WalkStatus.CANCELLED.toInt());
        WalkDao.dao.cancel(walk.getWalkId());
        resp.sendRedirect("./owner.jsp");
    }
    
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	doPost(req, resp);
    }
    
}

