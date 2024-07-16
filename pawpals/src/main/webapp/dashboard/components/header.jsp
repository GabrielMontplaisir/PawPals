<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

	<header class="dashboard_header">
		<h2><a href="./profile">PawPals</a></h2>
		<p class="welcome_msg">Welcome ${user.getFirstName()}!</p>
		
		<nav>
			<c:if test="${!fn:contains(pageContext.request.requestURI, '/profile') && !fn:contains(pageContext.request.requestURI, '/settings')}">
				<c:if test="${!user.isOwnerMode()}">
					<a href='./owner' class='nav_btn'>Switch to Dog Owner</a>
				</c:if>
				<c:if test="${user.isOwnerMode()}">
					<a href='./walker' class='nav_btn'>Switch to Dog Walker</a>
				</c:if>
			</c:if>
			<a href="./profile" class="nav_btn">Profile</a>
			<a href="./settings" class="nav_btn">Settings</a>
			<a href="../logout" class="nav_btn">Logout</a>
		</nav>
	</header>