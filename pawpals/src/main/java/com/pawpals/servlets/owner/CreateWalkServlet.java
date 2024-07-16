package com.pawpals.servlets.owner;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.pawpals.beans.*;
import com.pawpals.dao.DogDao;
import com.pawpals.dao.WalkDao;
import com.pawpals.dao.WalkDogDao;
import com.pawpals.services.*;

@WebServlet("/dashboard/create-walk")
public class CreateWalkServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	User user = SessionService.srv.getSessionUser(req);
    	if (user == null) {
    		resp.sendRedirect("../login"); 
    		return;
    	}

        String[] dogIds = req.getParameterValues("selecteddogs");
        Walk newWalk = WalkDao.dao.createWalk(user.getId(), req);
        
        if ( dogIds == null || dogIds.length == 0) {		// TODO: Make addDogs method accepts string array of dogIds.
        	req.setAttribute("message", "No dogs selected. Please select a dog.");
        	req.getRequestDispatcher("./owner.jsp").forward(req, resp);
        	return;
        }
        
        	// TODO: Implement as a batch instead of updating the DB on every iteration.
        	for (String dogIdString : dogIds ) {
        		int dogId = Integer.parseInt(dogIdString);
        		Dog dog = DogDao.dogDao.getDogById(dogId);
        		WalkDogDao.dao.addDogToWalk(newWalk.getWalkId(), dog.getDogId());
        	}
        	
        	
        resp.sendRedirect("./owner");
        
    }
}

