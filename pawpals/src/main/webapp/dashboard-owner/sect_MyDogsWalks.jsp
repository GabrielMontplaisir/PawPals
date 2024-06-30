<%@ page import="com.pawpals.beans.Dog" %>
<%@ page import="com.pawpals.beans.User" %>
<%@ page import="com.pawpals.beans.Walk" %>
<%@ page import="java.util.List" %>
		
	<%
		User user = (User) session.getAttribute("user");
		List<Walk> walks = user.getWalks();
		
		if (walks != null) {
				
			
			for (Walk walk: walks ){
				
				out.write("<section class='container'>");

        		out.write("<table class='temptable'>");
	        		out.write("<tr><th>Open</th><td><form action='page_WalkDetails.jsp' method='POST'><input type='submit' value='Open' /><input type='hidden' name='walkId' value='" +walk.getWalkId()+ "'> </form>   </td></tr>");
            		out.write("<tr><th>Location</th><td>" +walk.getLocation()+ "</td></tr>");
            		out.write("<tr><th>Date</th><td>" +walk.getDate()+ "</td></tr>");
            		List<Dog> doggies = walk.getDogs();
        		out.write("</table>");
        		if ( doggies.size() == 1 ) {
        			out.write("<p>Doggy</p>"); 
        		} else if (doggies.size() > 1) {
        			out.write("<p>Doggies</p>");
        		} else {
        			out.write("<p>Error</p>");
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


			
	