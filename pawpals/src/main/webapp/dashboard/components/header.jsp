<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

	<header class="dashboard_header">
		<h2><a href="./profile">PawPals</a></h2>
		<p class="welcome_msg">Welcome ${user.getFirstName()}!</p>
		<div class="notification_container">
		<label class="notification_label"><input type="checkbox" class="notification_btn" hidden><img src="../assets/images/notification_24.svg" alt="user notifications"></label>
			<div class="notification_list">
				<h3 class="notification_title">Your Notifications</h3>
				<c:if test="${user.getNotifications().size() > 0}">
					<c:forEach var="notification" items="${user.getNotifications()}">
						<a href="${notification.getUrl()}" class="notification">
							<p>${notification.getTitle()}</p>
							<p class="notification_details">${notification.getDescription()}</p>
							<p class="right">${notification.getDateTime()}</p>
						</a>
						
					</c:forEach>
				</c:if>
				<c:if test="${user.getNotifications().size() == 0}">
					<li class="notification_details center my-2">No new notifications</li>
				</c:if>				
			</div>
		</div>

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