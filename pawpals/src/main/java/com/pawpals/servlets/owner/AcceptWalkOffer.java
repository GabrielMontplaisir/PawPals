package com.pawpals.servlets.owner;

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

@WebServlet("/owner-dashboard/accept-walk-offer")
public class AcceptWalkOffer extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AcceptWalkOffer() {
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
        WalkService.svc.acceptWalkOffer(walk, user);
        request.getRequestDispatcher("walk-detail").forward(request, response);
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	User user = SessionService.srv.getSessionUser(request);
    	if (user == null) { response.sendRedirect("../index.jsp"); return;}

        response.sendRedirect("./");
    }
    
}

