package com.pawpals.servlets.owner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import com.pawpals.beans.User;
import com.pawpals.beans.Walk;
import com.pawpals.beans.WalkOffer;
import com.pawpals.dao.WalkDao;
import com.pawpals.services.SessionService;
import com.pawpals.services.WalkService;

@WebServlet("/owner-dashboard/walk-detail")
public class WalkDetail extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public WalkDetail() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	User user = SessionService.srv.getSessionUser(request);
    	if (user == null) { response.sendRedirect("../index.jsp"); return;}
    	
    	
    	System.out.println("User " + user.getEmail());
    	
    	Object obj_walkId = request.getAttribute("walkId");
    	if ( obj_walkId == null) {
    		System.out.println("walk id not passed as attribute");
    		obj_walkId = request.getParameter("walkId");
    	} else {
    		System.out.println("walk id passed as attribute: " + obj_walkId);
    	}
    	
    	if ( obj_walkId == null) {
    		System.out.println("walk id not passed as parameter");
    		response.sendRedirect("../index.jsp");
    		return;
    	} else {
    		System.out.println("walk id passed as parameter: " + obj_walkId);
    		
    		//String) 
    	}
    	
    	

    	int walkId;
    	try {
    		walkId = Integer.parseInt( (String) obj_walkId );
    	} catch (Exception e) {
    		response.sendRedirect("../index.jsp"); 
    		return;
    	}

    	if ( ! (walkId > 0) ) { response.sendRedirect("../index.jsp"); return; }

    	Walk walk = WalkDao.dao.getWalkById(walkId);
    	if ( walk == null )  { response.sendRedirect("../index.jsp"); return; }
    	
    	if ( walk.getOwnerId() != user.getId()  ) {
    		 response.sendRedirect("../index.jsp"); return;
    	}
    	
    	List<WalkOffer> offers = WalkService.svc.getWalkOffers_by_WalkId(walkId);
    	request.setAttribute("offers", offers);
    	
    	request.setAttribute("walk", walk);
    	request.getRequestDispatcher("walk-detail.jsp").forward(request, response);
    	
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	User user = SessionService.srv.getSessionUser(request);
    	if (user == null) { response.sendRedirect("../index.jsp"); return;}
        
    	response.sendRedirect("../index.jsp"); return;
    }
    
}

