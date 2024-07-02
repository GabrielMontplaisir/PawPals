<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
request.setAttribute("_owner_", true);
%> <jsp:include page="../common/header.jsp" /> 
<%@ page import="com.pawpals.beans.*" %>
<%@ page import="java.util.List" %>

<%
	Walk walk = (Walk) request.getAttribute("walk");
	Boolean offer = ((int) request.getAttribute("offer") > 0);
%>

	<main>
		<section class="container">

			<header>
				<h1 class="subtitle">Walk Details</h1>
			</header>

			<% 	
           		out.write("<table class='temptable'>");
					out.write("<tr><th>Action</th><td>");
					
					if ( !offer ){
						out.write("<form class='miniForm' action='create-walk-offer' method='POST'>"
		       					+ "<input type='submit' value='Offer Service' />" 
		     					+ "<input type='hidden' name='walkId' value='" +walk.getWalkId()+ "'></form>");
						out.write("</td></tr>");
					} else {
						out.write("<form class='miniForm' action='cancel-walk-offer' method='POST'>"
		       					+ "<input type='submit' value='Cancel Offer' />" 
		     					+ "<input type='hidden' name='walkId' value='" +walk.getWalkId()+ "'></form>");
						out.write("</td></tr>");						
					}
					
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
            	
           	%>

		</section>

	</main>

</body>
</html>