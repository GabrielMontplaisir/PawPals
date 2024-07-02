package com.pawpals.servlets.owner;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.pawpals.beans.*;
import com.pawpals.services.*;

@WebServlet("/owner-dashboard/create-walk")
public class CreateWalk extends HttpServlet {
    private static final long serialVersionUID = 1L;
    public CreateWalk() {
        super();
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	User user = SessionService.srv.getSessionUser(request);
    	if (user == null) { response.sendRedirect("../index.jsp"); return;}

        String[] dogIds = request.getParameterValues("dogIds[]");
        String startTime = request.getParameter("startTime");
        String location = request.getParameter("location");
        String length = request.getParameter("length");
        Walk newWalk = WalkService.svc.addWalk(user, startTime, location, length);
        
        if ( dogIds == null || dogIds.length == 0) {		// ToDo: Make addDogs method accepts string array of dogIds.
        	System.err.println("No doggies!! Error");
        	return;
        }
        
        	for (String dogIdString : dogIds ) {
        		int dogId = Integer.parseInt(dogIdString);
        		Dog dog = DogService.svc.getDogById(dogId);
        		WalkService.svc.addDog(newWalk, dog);
        	}
        request.setAttribute("walkId", String.valueOf(newWalk.getWalkId()));
        request.getRequestDispatcher("walk-detail").forward(request, response);
        
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	User user = SessionService.srv.getSessionUser(request);
    	if (user == null) { response.sendRedirect("../index.jsp"); return;}
return;
       // response.sendRedirect("../owner-walkdetail/");
    }
    
}

