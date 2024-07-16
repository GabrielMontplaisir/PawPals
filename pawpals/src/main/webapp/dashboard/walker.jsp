<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0" >
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Nunito:ital,wght@0,200..1000;1,200..1000&display=swap" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css2?family=PT+Sans:ital,wght@0,400;0,700;1,400;1,700&display=swap" rel="stylesheet">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/root.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
	<title>PawPals | Walker Dashboard</title>
</head>
<body class="dashboard">
	<jsp:include page="./components/header.jsp" /> 
	<main>
		<h1 class="subtitle">Walker Dashboard</h1>
		<section>
			<h2 class="subtitle">Find Walks</h2>
			<c:if test="${walks != null}">
				<ul class="walk_list">
					<c:forEach var="walk" items="${walks}">
						<li><a href="./walkdetails?id=${walk.getWalkId()}" class="walk_card"><span>Walk in ${walk.getLocation()} at ${walk.getDate()}</span><span class="ml-auto">${walk.getStatus().toString()}</span></a></li>
					</c:forEach>
				</ul>
			</c:if>
		</section>

		<section>
			<h2 class="subtitle">Current Walks</h2>
			<c:if test="${userWalks != null}">
				<ul class="walk_list">
					<c:forEach var="walk" items="${userWalks}">
						<c:if test="${!walk.isFinished()}">
							<li><a href="./walkdetails?id=${walk.getWalkId()}" class="walk_card"><span>Walk in ${walk.getLocation()} at ${walk.getDate()}</span><span class="ml-auto">${walk.getStatus().toString()}</span></a></li>
						</c:if>
					</c:forEach>
				</ul>
			</c:if>
		</section>

		<section>
			<h2 class="subtitle">Past Walks</h2>
			<c:if test="${userWalks != null}">
				<ul class="walk_list">
					<c:forEach var="walk" items="${userWalks}">
						<c:if test="${walk.isFinished()}">
							<li><a href="./walkdetails?id=${walk.getWalkId()}" class="walk_card"><span>Walk in ${walk.getLocation()} at ${walk.getDate()}</span><span class="ml-auto">${walk.getStatus().toString()}</span></a></li>
						</c:if>
					</c:forEach>
				</ul>
			</c:if>
		</section>



	</main>
</body>
</html>