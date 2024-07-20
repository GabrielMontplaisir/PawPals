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
<body class="background">
	<jsp:include page="./components/header.jsp" /> 
	<main>
		<h1 class="subtitle">Walker Dashboard</h1>
		<section>
		    <h2 class="subtitle">Find Walks</h2>
		    <c:if test="${walks != null}">
		        <ul class="card_list">
		            <c:forEach var="walk" items="${walks}">
		                <li class="card">
		                    <header class="card_header">
		                        <h3 class="card_title">Walk in ${walk.getLocation()}</h3>
		                        <p>${walk.getStatus().toString()}</p>
		                    </header>
		                    <p class="card_details">${walk.getFullDate()}</p>
		                    <a href="./walkdetails?id=${walk.getWalkId()}" class="btn mt-2">View</a>
		                </li>
		            </c:forEach>
		        </ul>
		    </c:if>
		</section>


		<section>
		    <h2 class="subtitle">Current Walks</h2>
		    <c:if test="${userWalks != null}">
		        <ul class="card_list">
		            <c:forEach var="walk" items="${userWalks}">
		                <c:if test="${!walk.isFinished()}">
		                    <li class="card">
		                        <header class="card_header">
		                            <h3 class="card_title">Walk in ${walk.getLocation()}</h3>
		                            <p>${walk.getStatus().toString()}</p>
		                        </header>
		                        <p class="card_details">${walk.getFullDate()}</p>
		                        <a href="./walkdetails?id=${walk.getWalkId()}" class="btn mt-2">View</a>
		                    </li>
		                </c:if>
		            </c:forEach>
		        </ul>
		    </c:if>
		</section>

		<section>
		    <h2 class="subtitle">Past Walks</h2>
		    <c:if test="${userWalks != null}">
		        <ul class="card_list">
		            <c:forEach var="walk" items="${userWalks}">
		                <c:if test="${walk.isFinished()}">
		                    <li class="card">
		                        <header class="card_header">
		                            <h3 class="card_title">Walk in ${walk.getLocation()}</h3>
		                            <p>${walk.getStatus().toString()}</p>
		                        </header>
		                        <p class="card_details">${walk.getFullDate()}</p>
		                        <a href="./walkdetails?id=${walk.getWalkId()}" class="btn mt-2">View</a>
		                    </li>
		                </c:if>
		            </c:forEach>
		        </ul>
		    </c:if>
		</section>

	</main>
</body>
</html>