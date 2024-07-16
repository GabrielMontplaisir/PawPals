<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0" >
	<title>PawPals | Profile</title>
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Nunito:ital,wght@0,200..1000;1,200..1000&display=swap" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css2?family=PT+Sans:ital,wght@0,400;0,700;1,400;1,700&display=swap" rel="stylesheet">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/root.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
</head>
<body class="dashboard">
	<jsp:include page="./components/header.jsp" /> 
	<main>
		<header>
			<h1 class="subtitle">Your Profile</h1>
			<div class="ml-auto">
				<a href="./owner" class="btn mr-2">Create a Walk</a>
				<a href="./walker" class="btn">Find Walks</a>
			</div>
		</header>
		
		<section class="container mt-2">
			<h2 class="subtitle">${user.getFirstName()} ${user.getLastName()}</h2>
		</section>
		<h2 class="subtitle center my-2">Your Dogs</h2>
		<ul class="card_list">
			<c:forEach var="dog" items="${dogs}">
				<li class="card">
				<header class="card_header">
					<h4 class="card_title">${dog.getName()}</h4>
					<a href="remove-dog?id=${dog.getDogId()}" class="error" title="Remove dog"><img src="${pageContext.request.contextPath}/assets/images/delete_24.svg" alt="Remove dog"></a>
				</header>
					
					<p>Size: ${dog.getSize()}</p>
					<p>Immunized: ${dog.isImmunized()}</p>
					<p>Special Needs: ${dog.getSpecialNeeds()}</p>
				</li>					
			</c:forEach>
		</ul>
	</main>
</body>
</html>