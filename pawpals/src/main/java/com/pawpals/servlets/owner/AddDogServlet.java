package com.pawpals.servlets.owner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.pawpals.beans.User;
import com.pawpals.dao.DogDao;
import com.pawpals.services.SessionService;

@WebServlet("/dashboard/add-dog")
public class AddDogServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	User user = SessionService.srv.getSessionUser(req);
    	if (user == null) { 
    		resp.sendRedirect("../index.jsp"); 
    		return;
    	}

    	DogDao.dogDao.addDog(user.getId(), req);
        resp.sendRedirect("./owner.jsp");
        
    }
    
}
