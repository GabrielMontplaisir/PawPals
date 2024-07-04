<%@page import="com.pawpals.interfaces.WalkStatus"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.pawpals.beans.*,java.util.List,java.util.HashMap" %>
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
	<title>PawPals | Walker Dashboard</title>
</head>
<%
	if (session.getAttribute("user") == null) {
		response.sendRedirect("../index.jsp");
		return;
	}

	User user = (User) session.getAttribute("user");
	HashMap<Integer, Boolean> walkOffers = new HashMap<>();
	List<Walk> walks = (List<Walk>) user.getAvailableWalksFromDB(walkOffers);
%>
<body class="dashboard">
	<jsp:include page="./components/header.jsp" /> 
	<main>
		<h1 class="subtitle">Walker Dashboard</h1>
		<section>
		<h2 class="subtitle">Find Walks</h2>
		<%
		
		if (walks != null) {
			out.write("<ul class='walk_list'>");
			
			for (Walk walk: walks ){
				List<Dog> dogList = walk.getDogs();
				Boolean walkHasOffer = walkOffers.get(walk.getWalkId());
				

				out.write("<li>");
				out.write("<a href='./walkdetails?id="+walk.getWalkId()+"' class='walk_card'><span>Walk in "+walk.getLocation()+" at "+walk.getDate()+"</span><span class='ml-auto'>"+walk.getStatusMessage()+"</span></a>");
			       		out.write("</li>");
			}
				out.write("</ul>");
				} else {
			out.write("null");
				}
		%>
		</section>

		<section>
			<h2 class="subtitle">Current Walks</h2>
			<%
			walks = user.getWalksFromDBAsWalker();
				if (walks != null) {
					out.write("<ul class='walk_list'>");

					for (Walk walk : walks) {
						if (walk.getStatus() != WalkStatus.CANCELLED && walk.getStatus() != WalkStatus.WALKER_COMPLETED) {
					List<Dog> dogList = walk.getDogs();

					out.write("<li>");
					out.write("<a href='./walkdetails?id=" + walk.getWalkId() + "' class='walk_card'><span>Walk in "
							+ walk.getLocation() + " at " + walk.getDate() + "</span><span class='ml-auto'>"
							+ walk.getStatusMessage() + "</span></a>");
					out.write("</li>");
						}
					}
					out.write("</ul>");
				} else {
					out.write("null");
				}
			%>
		</section>

		<section>
			<h2 class="subtitle">Past Walks</h2>
			<%
			walks = user.getWalksFromDBAsWalker();
				if (walks != null) {
					out.write("<ul class='walk_list'>");

					for (Walk walk : walks) {
						if (walk.getStatus() == WalkStatus.CANCELLED || walk.getStatus() == WalkStatus.WALKER_COMPLETED) {
					List<Dog> dogList = walk.getDogs();

					out.write("<li>");
					out.write("<a href='./walkdetails?id=" + walk.getWalkId() + "' class='walk_card'><span>Walk in "
							+ walk.getLocation() + " at " + walk.getDate() + "</span><span class='ml-auto'>"
							+ walk.getStatusMessage() + "</span></a>");
					out.write("</li>");
						}
					}
					out.write("</ul>");
				} else {
					out.write("null");
				}
			%>
		</section>



	</main>
</body>
</html>