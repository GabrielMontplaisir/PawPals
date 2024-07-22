package com.pawpals.servlets.owner;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.pawpals.beans.*;
import com.pawpals.dao.WalkDao;
import com.pawpals.dao.WalkDogDao;
import com.pawpals.interfaces.Validation;
import com.pawpals.services.*;

@WebServlet("/dashboard/create-walk")
public class CreateWalkServlet extends Validation {
    private static final long serialVersionUID = 1L;
    User user;

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    	user = SessionService.srv.getSessionUser(req);
    	String message;
    	if (user == null) {
    		resp.sendRedirect("../login"); 
    		return;
    	}

    	message = validateForm(req.getParameterMap());
		if (message != null) {
			req.getSession(false).setAttribute("message", message);
			resp.sendRedirect("./owner");
			return;
		}
		
		req.getSession(false).removeAttribute("message");
		
		String[] dogIds = req.getParameterValues("selecteddogs");
        Walk newWalk = WalkDao.getDao().createWalk(user.getId(), req);
        
		WalkDogDao.getDao().addDogsToWalk(newWalk.getWalkId(), dogIds);
        	
        	
        resp.sendRedirect("./owner");
        
    }

	@Override
	protected String validateForm(Map<String, String[]> params) {
		Iterator<Entry<String, String[]>> iterator = params.entrySet().iterator();
		
		while (iterator.hasNext()) {
			Map.Entry<String, String[]> entry = (Entry<String, String[]>)iterator.next();
			String paramName = entry.getKey();
			String paramValue = entry.getValue()[0];
			
			if (isEmpty(paramName)) {return paramName+" cannot be empty.";}
			if (paramName.equals("selecteddogs")) {
				String[] dogs = entry.getValue();
				
				if (user.getDogList() == null || user.getDogList().isEmpty()) return "User has no dogs. Please create a dog.";
				
				for (String dog : dogs) {
					if (!user.getDogList().containsKey(Integer.parseInt(dog))) {
						return "Cannot create walk for dog which doesn't belong to you.";
					}
				}
			}
			
			if (paramName.equals("starttime")) {
				if (LocalDateTime.parse(paramValue).isBefore(LocalDateTime.now())) {
					return "Walk cannot be in the past.";
				}
			}
		}
		return null;
	}
}

