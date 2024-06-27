<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
</head>
<%
	if (session.getAttribute("user") == null) {
		response.sendRedirect("../index.jsp");
		return;
	}
%>
<body class="dashboard">
	<header class="dashboard_header">
		<h2>PawPals</h2>
		<p class="welcome_msg">Welcome ${user.getFirstName()}!</p>
		<nav>
		<a href="./walker.jsp" class="nav_btn">Switch to Walker</a>
			<a href="./settings.jsp" class="nav_btn">Settings</a>
			<a href="logout" class="nav_btn">Logout</a>
		</nav>
	</header>
	<main>
		<section class="container">
			<header>
				<h1 class="subtitle">Create a Walk</h1>
				<a href="" class="btn">Add dog</a>
			</header>
			<form>
				
			</form>
		</section>
	</main>
	
</body>
</html>