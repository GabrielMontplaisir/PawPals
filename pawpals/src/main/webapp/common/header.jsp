<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.pawpals.beans.User" %>
<%
	User user = (User) session.getAttribute("user") ;
	if (user == null) {	response.sendRedirect("../index.jsp");	return; }
%>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0" >
	<title>PawPals | Create a Walk</title>
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Nunito:ital,wght@0,200..1000;1,200..1000&display=swap" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css2?family=PT+Sans:ital,wght@0,400;0,700;1,400;1,700&display=swap" rel="stylesheet">
	<link rel="stylesheet" href="../css/root.css">
	<link rel="stylesheet" href="../css/dashboard.css">
	<link rel="stylesheet" href="../css/dashboard-dogOwner.css">
</head>
    <body class="dashboard">
	<header class="dashboard_header">
		<h2>PawPals</h2>
		<p class="welcome_msg">Welcome ${user.getFirstName()}!</p>
		
		<nav>
		<%

		boolean isOwner = request.getRequestURL().toString().contains("/owner-");
		
//			boolean isOwner = request.getAttribute("_owner_") == null;
		
			if (    isOwner   ) {
				out.write("<a href='../walker-dashboard' class='nav_btn'>Switch to Doggy Walker</a>");				
			} else {
				out.write("<a href='../owner-dashboard' class='nav_btn'>Switch to Doggy Owner</a>");
			}
			
			
		 %>
			<a href="./" class="nav_btn">Home</a>
			<a href="./settings.jsp" class="nav_btn">Settings</a>
			<a href="../logout" class="nav_btn">Logout</a>
		</nav>
	</header>