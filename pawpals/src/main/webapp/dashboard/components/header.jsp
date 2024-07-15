<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
	<header class="dashboard_header">
		<h2>PawPals</h2>
		<p class="welcome_msg">Welcome ${user.getFirstName()}!</p>
		
		<nav>
		<%
			if (request.getRequestURL().toString().contains("/walker")) {
				out.write("<a href='./owner' class='nav_btn'>Switch to Dog Owner</a>");				
			} else if (request.getRequestURL().toString().contains("/owner")) {
				out.write("<a href='./walker' class='nav_btn'>Switch to Dog Walker</a>");
			}
			
			
		 %>
			<a href="./profile" class="nav_btn">Profile</a>
			<a href="./settings" class="nav_btn">Settings</a>
			<a href="../logout" class="nav_btn">Logout</a>
		</nav>
	</header>