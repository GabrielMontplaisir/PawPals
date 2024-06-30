package com.pawpals.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import com.pawpals.beans.User;
import com.pawpals.beans.Walk;

@WebServlet("/dashboard-owner/CancelWalk")
public class CancelWalk extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public CancelWalk() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        if (user == null) {
            response.sendRedirect("../index.jsp");
            return;
        }

        int walkId = Integer.parseInt(request.getParameter("walkId"));

        Walk walk = user.getWalk_by_WalkId_as_Owner(walkId);

        if (walk == null) {
            response.sendRedirect("./");
            return;
        }
        walk.doCancel();
        response.sendRedirect("./");
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

