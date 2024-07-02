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

@WebServlet("/owner-dashboard/add-my-doggy")
public class AddMyDoggy extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AddMyDoggy() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	User user = SessionService.srv.getSessionUser(request);
    	if (user == null) { response.sendRedirect("../index.jsp"); return;}

        String name = request.getParameter("name");
        String size = request.getParameter("size");
        String specialNeeds = request.getParameter("special_needs");
        boolean immunized = request.getParameter("immunized") != null;
        DogDao.dogDao.addDog(user.getId(), name, size, specialNeeds, immunized);
        
        request.getRequestDispatcher("./").forward(request, response);
        
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	User user = SessionService.srv.getSessionUser(request);
    	if (user == null) { response.sendRedirect("../index.jsp"); return;}

    	
    	response.sendRedirect("../index.jsp");
    }
    
}

