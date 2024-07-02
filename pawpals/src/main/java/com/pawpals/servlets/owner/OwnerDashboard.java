package com.pawpals.servlets.owner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.pawpals.beans.User;
import com.pawpals.services.DogService;
import com.pawpals.services.SessionService;
import com.pawpals.services.WalkService;

@WebServlet("/owner-dashboard/")
public class OwnerDashboard extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public OwnerDashboard() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	doGet(request, response);
    	//    	User user = SessionService.srv.getSessionUser(request);
//    	if (user == null) { response.sendRedirect("../index.jsp"); return;}
//        
//        
//        request.setAttribute("walks", WalkService.svc.getWalks_as_DogOwner(user.getId()));
//        request.setAttribute("dogs", DogService.svc.getDogs_as_DogOwner(user.getId()));
//        request.getRequestDispatcher("mainpage.jsp").forward(request, response);
        
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	User user = SessionService.srv.getSessionUser(request);
    	if (user == null) { response.sendRedirect("../index.jsp"); return;}
    	
        request.setAttribute("walks", WalkService.svc.getWalks_as_DogOwner(user.getId()));
        request.setAttribute("dogs", DogService.svc.getDogs_by_DogOwnerUserId(user.getId()));
        request.getRequestDispatcher("mainpage.jsp").forward(request, response);
    }
    
}

