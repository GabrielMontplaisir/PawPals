package com.pawpals.servlets.walker;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.pawpals.beans.User;
import com.pawpals.beans.Walk;
import com.pawpals.services.SessionService;
import com.pawpals.services.WalkService;

@WebServlet("/walker-dashboard/cancel-walk-offer")
public class cancelWalkOffer extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public cancelWalkOffer() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	User user = SessionService.srv.getSessionUser(request);
    	if (user == null) { response.sendRedirect("../index.jsp"); return;}
        int walkId = Integer.parseInt(request.getParameter("walkId"));
        Walk walk =  WalkService.svc.getWalk_by_WalkId(walkId);
        if (walk == null) {
            response.sendRedirect("./");
            return;
        }
        WalkService.svc.cancelWalkOffer(walkId, user.getId());
        request.getRequestDispatcher("walk-detail").forward(request, response);
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	User user = SessionService.srv.getSessionUser(request);
    	if (user == null) { response.sendRedirect("../index.jsp"); return;}

        response.sendRedirect("./");
    }
    
}

