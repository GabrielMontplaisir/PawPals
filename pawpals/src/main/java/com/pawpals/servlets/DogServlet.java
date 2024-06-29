package com.pawpals.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import com.pawpals.beans.User;
import com.pawpals.dao.ApplicationDao;
import com.pawpals.dao.DogDao;

@WebServlet("/dashboard/createwalk")
public class DogServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public DogServlet() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
		ApplicationDao.dao.createDogsTable();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("../index.jsp");
            return;
        }

        String name = request.getParameter("name");
        String size = request.getParameter("size");
        String specialNeeds = request.getParameter("special_needs");
        boolean immunized = request.getParameter("immunized") != null;
        DogDao.dogDao.addDog(user.getId(), name, size, specialNeeds, immunized);
        request.getRequestDispatcher("./createwalk.jsp").forward(request, response);

        
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("../index.jsp");
            return;
        }

        request.getRequestDispatcher("./createwalk.jsp").forward(request, response);
    }
    
}

