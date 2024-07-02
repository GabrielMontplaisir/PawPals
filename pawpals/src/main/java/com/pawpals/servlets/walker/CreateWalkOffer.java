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
import com.pawpals.services.SessionService;
import com.pawpals.services.WalkService;

@WebServlet("/walker-dashboard/create-walk-offer")
public class CreateWalkOffer extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CreateWalkOffer() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	System.out.println("doPost Create walk offer");
    	User user = SessionService.srv.getSessionUser(request);
    	if (user == null) { response.sendRedirect("../index.jsp"); return;}
    	System.out.println("user not null, session valid");
        int walkId = Integer.parseInt(request.getParameter("walkId"));
        System.out.println("walkid is" + walkId);
        Walk walk =  WalkService.svc.getWalk_by_WalkId(walkId);
        if (walk == null) {
        	System.out.println("walk is null");
            response.sendRedirect("./");
            return;
        }
        System.out.println("walk DAO setting to posted .. nevermind..");
        
        WalkDao.dao.addWalkOffer(walkId, user.getId());
        
        request.getRequestDispatcher("walk-detail").forward(request, response);
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	User user = SessionService.srv.getSessionUser(request);
    	if (user == null) { response.sendRedirect("../index.jsp"); return;}

        response.sendRedirect("./");
    }
    
}

