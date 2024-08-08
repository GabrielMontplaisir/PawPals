package com.pawpals.servlets.walker;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.pawpals.beans.*;
import com.pawpals.dao.NotificationDao;
import com.pawpals.dao.WalkOfferDao;
import com.pawpals.libs.builders.NotificationBuilder;
import com.pawpals.libs.services.*;

@WebServlet("/dashboard/create-offer")
public class CreateWalkOfferServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = SessionService.srv.getSessionUser(req);
        if (user == null) { 
            resp.sendRedirect("../login"); 
            return;
        }
        
        String idParam = req.getParameter("id");
        if (idParam == null || idParam.isEmpty()) {
            resp.sendRedirect("./walker");
            return;
        }
        
        int walkId = Integer.parseInt(idParam);
        String comment = req.getParameter("comment"); // Retrieve the comment from the request
        Walk walk =  user.getCachedWalks().get(walkId);
        
        if (walk == null) {
            System.out.println("Error: Could not offer services. Walk not found.");
            resp.sendRedirect("./walker");
            return;
        }
        
        WalkOfferDao.getDao().addWalkOffer(walkId, user.getUserId(), comment); // Pass the comment to the DAO
        
        walk.notifyObservers(NotificationDao.getDao().createNotificationForUser(
                new NotificationBuilder()
                    .setUserId(walk.getOwnerId())
                    .setTitle(user.getFirstName()+" "+user.getLastName()+" offered their services for your walk.")
                    .setDescription("Walk in "+walk.getLocation()+" on "+walk.getShortDate())
                    .setUrl(req.getContextPath()+"/dashboard/walkdetails?id="+walkId)
                    .create()
                )
        );
        
        walk.subscribe(user);
        
        resp.sendRedirect("./walkdetails?id="+walkId);
    }
    
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
}