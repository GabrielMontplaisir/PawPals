package com.pawpals.servlets.pages;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pawpals.beans.User;
import com.pawpals.services.SessionService;

@WebServlet("/index")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		User user = SessionService.srv.getSessionUser(req);
		
		if (user != null) {
			resp.sendRedirect("./dashboard/profile");
			return;
		}
		
		req.getRequestDispatcher("index.jsp").forward(req, resp);
	}
}