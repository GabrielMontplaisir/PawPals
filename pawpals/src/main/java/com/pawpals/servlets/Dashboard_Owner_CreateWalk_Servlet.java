package com.pawpals.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Date;

import com.pawpals.beans.Dog;
import com.pawpals.beans.User;
import com.pawpals.beans.Walk;
import com.pawpals.dao.ApplicationDao;
import com.pawpals.dao.DogDao;
import com.pawpals.dao.WalkDao;

@WebServlet("/dashboard-owner/CreateWalk")
public class Dashboard_Owner_CreateWalk_Servlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public Dashboard_Owner_CreateWalk_Servlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
		WalkDao.dao.createWalksTable();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("../index.jsp");
            return;
        }

        String[] dogIds = request.getParameterValues("dogIds[]");
        String startTime = request.getParameter("startTime");
        String location = request.getParameter("location");
        String length = request.getParameter("length");
        Walk newWalk = WalkDao.dao.addWalk(user, startTime, location, length);
        
        
        
        if ( dogIds == null || dogIds.length == 0) {		// ToDo: Make addDogs method accepts string array of dogIds.
        	System.err.println("No doggies!! Error");
        	return;
        }
        
        	for (String dogIdString : dogIds ) {
        		System.out.println("Adding dogid " + dogIdString );
        		int dogId = Integer.parseInt(dogIdString);
        		Dog dog = DogDao.dogDao.getDogById(dogId);
        		System.out.println("Doggie name " + dog.getName() );
        		WalkDao.dao.addDog(newWalk, dog);
        	}
        
        request.setAttribute("walkId", String.valueOf(newWalk.getWalkId()));
        request.getRequestDispatcher("./page_WalkDetails.jsp").forward(request, response);
        
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.sendRedirect("../index.jsp");
            return;
        }
        response.sendRedirect("./");
    }
    
}

