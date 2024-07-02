<%@ page import="com.pawpals.beans.*,java.util.List,java.util.HashMap" %>

<%
	User user = (User) session.getAttribute("user");
	HashMap<Integer, Boolean> walkOffers = new HashMap<>();
	List<Walk> walks = (List<Walk>) user.getWalks_for_Soliciting_WalkOffers(walkOffers);
	
	if (walks != null) {
		for (Walk walk: walks ){
			List<Dog> doggies = walk.getDogs();
			Boolean walkHasOffer = walkOffers.get(walk.getWalkId());
			
			
			out.write("<section class='container'>");
       		out.write("<table class='temptable'>");
       		out.write("<tr><th>Action</th><td><form action='walk-detail' method='POST'>"
       					+ "<input type='submit' value='Open' />" 
     					+ "<input type='hidden' name='walkId' value='" +walk.getWalkId()+ "'></form></td></tr>");
       			
       			out.write("<tr><th>Offer</th><td>" + walkHasOffer + "</td></tr>");
       			out.write("<tr><th>Location</th><td>" +walk.getLocation()+ "</td></tr>");
       			out.write("<tr><th>Date</th><td>" +walk.getDate()+ "</td></tr>");
       		out.write("</table>");
       		
       		if ( doggies.size() == 1 ) {
       			out.write("<p>Doggy</p>"); 
       		} else if (doggies.size() > 1) {
       			out.write("<p>Doggies</p>");
       		}
       		out.write("<table class=\"temptable\">");
       		out.write("<tr><th>Doggy Name</th><th>Size</th><th>Special Needs</th></tr>");
           																                    		
               		for ( Dog dog : doggies ) {
               			out.write("<tr><td>"+ dog.getName()+ "</td><td>"+ dog.getSize() +"</td><td>"+dog.getSpecialNeeds()+"</td></tr>");
               		}
           		out.write("</tr>");
       		out.write("</table>");
       		out.write("</section>");
		}
		
	} else {
		out.write("null");
	}
%>



