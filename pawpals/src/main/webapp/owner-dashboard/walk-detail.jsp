<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
%> <jsp:include page="../common/header.jsp" /> 
<%@ page import="com.pawpals.beans.*,java.util.List" %>
<%
	Walk walk = (Walk) request.getAttribute("walk");
	List<WalkOffer> offers = (List<WalkOffer>) request.getAttribute("offers");
%>

	<main>
		<section class="container">

			<header>
				<h1 class="subtitle">Walk Details</h1>
			</header>

			<% 	
           		out.write("<table class='temptable'>");
					out.write("<tr><th>Status</th><td>" +walk.getFriendlyStatus());
					
					
					if ( walk.getStatus() != Walk.EnumStatus.CANCELLED.toInt() ) {
						out.write("<form class='miniForm' action='cancel-walk' method='POST'>"
		       					+ "<input type='submit' value='Cancel' />" 
		     					+ "<input type='hidden' name='walkId' value='" +walk.getWalkId()+ "'></form>");
					}
					
					if ( walk.getStatus() == Walk.EnumStatus.OWNER_INITIALIZED.toInt() ) {
						out.write("<form class='miniForm' action='post-walk' method='POST'>"
		       					+ "<input type='submit' value='Post' class='greenBack'/>" 
		     					+ "<input type='hidden' name='walkId' value='" +walk.getWalkId()+ "'></form></td></tr>");						
					}
					
					if ( walk.getStatus() == Walk.EnumStatus.WALKER_CHOSEN.toInt() ) {
						out.write("<tr><th>Walker</th><td>" +walk.getWalker().getFirstName() + " " 
						+ walk.getWalker().getLastName() + "</td></tr>");
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

		<%
		
			if ( walk.getStatus() == Walk.EnumStatus.OWNER_POSTED.toInt() ) {
				out.write("<h1 class='subtitle'>Active Offers</h1>");
			
					for (WalkOffer walkOffer: offers ){
	    
						
						out.write("<section class='container'>");
	
						out.write("<p>");
						
							out.write("<form class='miniForm' action='accept-walk-offer' method='POST'>"
			       					+ "<input type='submit' value='Accept' class='greenBack'/>" 
			     					+ "<input type='hidden' name='walkId' value='" +walk.getWalkId()+ "'>" 
									+ "<input type='hidden' name='walkerId' value='" +walkOffer.getWalkOfferUser().getId()+ "'>"
							+"</form></td></tr>");						
	
						out.write(walkOffer.getWalkOfferUser().getEmail() +" </p>");
	
						
						out.write("</section>");
					
					}
			}
		%>
		
		

	</main>

</body>
</html>