package com.pawpals.servlets.pages;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pawpals.beans.Dog;
import com.pawpals.beans.User;
import com.pawpals.dao.DogDao;
import com.pawpals.services.SessionService;

@WebServlet("/dashboard/profile")
public class ProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = SessionService.srv.getSessionUser(req);
		
		if (user == null) {
			resp.sendRedirect("../login");
			return;
		}
		
		Map<Integer, Dog> dogs = user.getDogList();
		
		req.setAttribute("dogs", dogs);
		
		req.getRequestDispatcher("./profile.jsp").forward(req, resp);
	}
}
