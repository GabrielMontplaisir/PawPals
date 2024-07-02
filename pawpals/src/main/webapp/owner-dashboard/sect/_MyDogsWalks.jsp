<%@ page import="com.pawpals.beans.Dog,com.pawpals.beans.User,com.pawpals.beans.Walk,java.util.List" %>
<%@ page import="com.pawpals.dao.WalkDao" %> <!--  For reference to enumeration (i.e. Status 2 = Posted) -->

<%
	User user = (User) session.getAttribute("user");
	List<Walk> walks = (List<Walk>) request.getAttribute("walks");
	
	if (walks != null) {
		for (Walk walk: walks ){
			List<Dog> doggies = walk.getDogs();
			
			out.write("<section class='container'>");
       		out.write("<table class='temptable'>");
       			out.write("<tr><th>Status</th><td><form action='walk-detail' method='POST'>"
       					+ "<input type='submit' value='" + walk.getFriendlyStatus() + "' />" 
     					+ "<input type='hidden' name='walkId' value='" +walk.getWalkId()+ "'></form></td></tr>");
       			
       			if ( walk.getStatus() == Walk.EnumStatus.OWNER_POSTED.toInt() ){
       				out.write("<tr><th>Active Offers</th><td>" + walk.getOfferCount() + "</td></tr>"  );
       			}
       			
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
		out.write("Error");
	}
%>



