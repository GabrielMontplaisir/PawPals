package com.pawpals.servlets.dog;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pawpals.beans.Dog;
import com.pawpals.beans.User;
import com.pawpals.dao.DogDao;
import com.pawpals.dao.WalkDao;
import com.pawpals.services.SessionService;

@WebServlet("/dashboard/remove-dog")
public class RemoveDogServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = SessionService.srv.getSessionUser(req);
		
		if (user == null) {
    		resp.sendRedirect("../login"); 
    		return;
		}
		
		int dogId = Integer.parseInt(req.getParameter("id"));
		Dog dog = DogDao.dao.getDogById(dogId);
		
		if (dog.getOwnerId() != user.getId()) {
			throw new Error("Cannot delete dog which doesn't belong to you.");
		}
		
		WalkDao.dao.deleteWalksNoDogFromID(dogId);
		DogDao.dao.removeDog(dogId);
		user.getDogList().remove(dogId);
		
		resp.sendRedirect("./profile");
	}
}
