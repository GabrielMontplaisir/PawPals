package com.pawpals.servlets.walker;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.pawpals.beans.User;
import com.pawpals.dao.WalkDao;
import com.pawpals.services.SessionService;

@WebServlet("/walker-dashboard/walk-detail")
public class WalkDetail extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public WalkDetail() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	User user = SessionService.srv.getSessionUser(request);
    	if (user == null) { response.sendRedirect("../index.jsp"); return;}
    	if (request.getParameter("walkId") == null ) {return;}
    	int walkId = Integer.parseInt( (String) request.getParameter("walkId"));
    	if ( ! (walkId > 0) ) { response.sendRedirect("../index.jsp"); return; }
    	request.setAttribute("offer", WalkDao.dao.checkWalkOffer(walkId, user.getId()));
    	request.setAttribute("walk", WalkDao.dao.getWalkById(walkId));
    	request.getRequestDispatcher("walk-detail.jsp").forward(request, response);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	User user = SessionService.srv.getSessionUser(request);
    	if (user == null) { response.sendRedirect("../index.jsp"); return;}
    	response.sendRedirect("../index.jsp"); return;
    }
    
}

