<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.pawpals.beans.Dog"%>
<%@ page import="com.pawpals.beans.User"%>
<%@ page import="com.pawpals.beans.Walk"%>
<%@ page import="com.pawpals.dao.WalkDao"%>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<title>PawPals | Walk</title>
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Nunito:ital,wght@0,200..1000;1,200..1000&display=swap"	rel="stylesheet">
	<link href="https://fonts.googleapis.com/css2?family=PT+Sans:ital,wght@0,400;0,700;1,400;1,700&display=swap" rel="stylesheet">
	<link rel="stylesheet" href="../css/root.css">
	<link rel="stylesheet" href="../css/dashboard.css">
	<link rel="stylesheet" href="../css/dashboard-dogOwner.css">
</head>
<%
	User user = (User) session.getAttribute("user") ;
	if (user == null) {
		response.sendRedirect("../index.jsp");
		return;
	}
	int walkId;
	if ( request.getParameter("walkId") != null) {
		walkId = Integer.parseInt(request.getParameter("walkId"));
	} else if ( request.getAttribute("walkId") != null ) {
		walkId = Integer.parseInt( (String) request.getAttribute("walkId"));
	} else {
		response.sendRedirect("./");
		return;
	}
	Walk walk = user.getWalk_by_WalkId_as_Owner(walkId);		// If user request walkID that does not
	if ( walk == null ) {										// Belong to them, redirect
		response.sendRedirect("./");
		return;		
	}
	
	int status = walk.getStatus();
	
	
%>
	
	<body class="dashboard">
	<header class="dashboard_header">
		<h2>PawPals</h2>
		<p class="welcome_msg">Welcome ${user.getFirstName()}!</p>
		<nav>
			<a href="./" class="nav_btn">Home</a> 
			<a href="./settings.jsp" class="nav_btn">Settings</a>
			<a href="../dashboard/logout" class="nav_btn">Logout</a>
		</nav>
	</header>
	<main>
		<section class="container">

			<header>
				<h1 class="subtitle">Walk Details</h1>
			</header>

			<% 	
           		out.write("<table class='temptable'>");
					out.write("<tr><th>Status</th><td>" +walk.getFriendlyStatus());
					
					
					if ( status != WalkDao.EnumStatus.CANCELLED.toInt() ) {
						out.write("<form class='miniForm' action='CancelWalk' method='POST'>"
		       					+ "<input type='submit' value='Cancel' />" 
		     					+ "<input type='hidden' name='walkId' value='" +walk.getWalkId()+ "'></form>");
					}
					
					if ( status == WalkDao.EnumStatus.OWNER_INITIALIZED.toInt() ) {
						out.write("<form class='miniForm' action='PostWalk' method='POST'>"
		       					+ "<input type='submit' value='Post' class='greenBack'/>" 
		     					+ "<input type='hidden' name='walkId' value='" +walk.getWalkId()+ "'></form></td></tr>");						
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