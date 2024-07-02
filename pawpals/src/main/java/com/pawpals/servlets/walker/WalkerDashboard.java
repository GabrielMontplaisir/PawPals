package com.pawpals.servlets.walker;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.pawpals.beans.User;
import com.pawpals.services.SessionService;

@WebServlet("/walker-dashboard/")
public class WalkerDashboard extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public WalkerDashboard() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	User user = SessionService.srv.getSessionUser(request);
    	if (user == null) { response.sendRedirect("../index.jsp"); return;}
        
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	User user = SessionService.srv.getSessionUser(request);
    	if (user == null) { response.sendRedirect("../index.jsp"); return;}

        request.getRequestDispatcher("mainpage.jsp").forward(request, response);
    }
    
}

