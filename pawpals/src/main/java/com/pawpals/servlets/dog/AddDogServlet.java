package com.pawpals.servlets.dog;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.pawpals.beans.Dog;
import com.pawpals.beans.User;
import com.pawpals.dao.DogDao;
import com.pawpals.libs.services.SessionService;

@WebServlet("/dashboard/add-dog")
public class AddDogServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	User user = SessionService.srv.getSessionUser(req);
    	if (user == null) { 
    		resp.sendRedirect("../login"); 
    		return;
    	}

    	Dog newDog = DogDao.getDao().addDog(user.getUserId(), req);
    	
    	user.getDogList().put(newDog.getDogId(), newDog);
        resp.sendRedirect("./owner");
        
    }
    
}

