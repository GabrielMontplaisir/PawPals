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
import com.pawpals.dao.WalkDao;
import com.pawpals.dao.WalkDao.EnumStatus;

@WebServlet("/dashboard-owner/PostWalk")
public class PostWalk extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public PostWalk() {
        super();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        User user = (User) session.getAttribute("user");

        if (user == null) {
        	System.out.println("PostWalk DoPost Nullredirect :(");
            response.sendRedirect("../index.jsp");
            return;
        }
        System.out.println("PostWalk DoPost x1");
        int walkId = Integer.parseInt(request.getParameter("walkId"));

        Walk walk = user.getWalk_by_WalkId_as_Owner(walkId);

        if (walk == null) {
        	System.out.println("PostWalk DoPost: walk is null");
            response.sendRedirect("./");
            return;
        }
        System.out.println("PostWalk DoPost: walk is not null");
        WalkDao.dao.setStatus(walkId, EnumStatus.OWNER_POSTED );
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

