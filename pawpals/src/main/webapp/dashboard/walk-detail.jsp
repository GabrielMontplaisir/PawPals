<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.pawpals.beans.*,java.util.List, com.pawpals.interfaces.WalkStatus" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0" >
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Nunito:ital,wght@0,200..1000;1,200..1000&display=swap" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css2?family=PT+Sans:ital,wght@0,400;0,700;1,400;1,700&display=swap" rel="stylesheet">
	<link rel="stylesheet" href="../css/root.css">
	<link rel="stylesheet" href="../css/dashboard.css">
	<title>PawPals | Walk Details</title>
</head>
	<%
		if (session.getAttribute("user") == null) {
			response.sendRedirect("../index.jsp");
			return;
		}
		User user = (User) session.getAttribute("user");
		Walk walk = (Walk) request.getAttribute("walk");
		boolean offer = request.getAttribute("offer") != null ? (boolean) request.getAttribute("offer") : false;
		List<WalkOffer> offers = (List<WalkOffer>) request.getAttribute("offers");
	%>
	<body class="dashboard">
		<jsp:include page="./components/header.jsp" />
		<main>
		<section class="container flex">
			<div>
				<header>
					<h1 class="subtitle">Walk Details</h1>
					<p>Status: ${walk.getStatus()}</p>
				</header>
				<p>Location: ${walk.getLocation()} at ${walk.getDate()}</p>

				<% 	
	           		out.write("<table class='temptable'>");
						
						if ( walk.getIntStatus() == WalkStatus.WALKER_CHOSEN.toInt() ) {
							out.write("<tr><th>Walker</th><td>" +walk.getWalker().getFirstName() + " " 
							+ walk.getWalker().getLastName() + "</td></tr>");
						}
	            		List<Dog> dogList = walk.getDogs();
	           		out.write("</table>");
	           		if ( dogList.size() == 1 ) {
	           			out.write("<p>Dog</p>"); 
	           		} else if (dogList.size() > 1) {
	           			out.write("<p>Dogs</p>");
	           		} else {
	           			out.write("<p>Error</p>");
	           		}
	           		out.write("<table class=\"temptable\">");
	           		out.write("<tr><th>Dog Name</th><th>Size</th><th>Special Needs</th></tr>");
	            																                    		
	             		for ( Dog dog : dogList ) {
	             			out.write("<tr><td>"+ dog.getName()+ "</td><td>"+ dog.getSize() +"</td><td>"+dog.getSpecialNeeds()+"</td></tr>");
	             		}
	            		out.write("</tr>");
	           		out.write("</table>");
	            	
	           	%>

			<%
				if (user.getId() == walk.getOwnerId() && walk.getIntStatus() != WalkStatus.CANCELLED.toInt()) {
					out.write("<a href='cancel-walk?id="+walk.getWalkId()+"' class='btn cancel mt-2'>Cancel</a>");
				}
			%>
			</div>
			<% if (user.getId() == walk.getOwnerId()) {
				if (walk.getIntStatus() != WalkStatus.CANCELLED.toInt()) {
			%>
		
			<div>
				<h2 class='subtitle'>Active Offers</h2>
				<% 
				if (walk.getIntStatus() == WalkStatus.OWNER_POSTED.toInt() && offers.size() > 0) {
						out.write("<ul>");
						for (WalkOffer walkOffer: offers ){
							out.write("<li class'mt-2'>"+walkOffer.getWalkOfferUser().getEmail());
							out.write("<a href='accept-offer?id="+walk.getWalkId()+"&walker="+walkOffer.getWalkOfferUser().getId()+"' class='btn ml-2'>Select</a>");						
							out.write("</li>");
						}
						out.write("</ul>");
					} else {
						out.write("<p>No offers yet!</p>");
					}
				%>
			</div>
			<%} %>
			
			<%} else { %>
			<div>
				<h2 class="subtitle">Actions</h2>
				<% 	
				
				if ( !offer ){
					out.write("<a href='create-offer?id="+walk.getWalkId()+"' class='btn'>Offer Service</a>");
				} else {
					out.write("<a href='cancel-offer?id="+walk.getWalkId()+"' class='btn cancel'>Cancel Offer</a>");						
				}
		        %>
	        </div>
        <%} %>
		</section>
	</main>
	</body>
</html>
    


