<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link
		href="https://fonts.googleapis.com/css2?family=Nunito:ital,wght@0,200..1000;1,200..1000&display=swap"
		rel="stylesheet">
	<link
		href="https://fonts.googleapis.com/css2?family=PT+Sans:ital,wght@0,400;0,700;1,400;1,700&display=swap"
		rel="stylesheet">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/root.css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/dashboard.css">
	<title>PawPals | Walk Details</title>
</head>
<body class="dashboard">
	<jsp:include page="./components/header.jsp" />
	<main>
		<Button onclick="history.back()" class="back_btn"> < Back to Dashboard</Button>
		<section class="container flex">
			<div>
				<header>
					<h1 class="subtitle">Walk Details</h1>
					<p>Status: ${walk.getStatus().toString()}</p>
				</header>
				<p>Location: ${walk.getLocation()} at ${walk.getDate()}</p>
				<table class="temptable">
					<c:if test="${walk.getWalker() != null}">
						<c:if test="${walk.getWalkerId() != user.getId()}">
							<tr>
								<th>Walker</th>
								<td>${walk.getWalker().getFirstName()} ${walk.getWalker().getLastName()}</td>
							</tr>
						</c:if>
					</c:if>
				</table>
				<p>Dogs</p>
				<table class="temptable">
					<tr>
						<th>Dog Name</th>
						<th>Size</th>
						<th>Special Needs</th>
					</tr>
					<c:forEach var="dog" items="${dogs}">
						<tr>
							<td>${dog.getName()}</td>
							<td>${dog.getSize()}</td>
							<td>${dog.getSpecialNeeds()}</td>
						</tr>
					</c:forEach>
				</table>
				<c:if test="${user.getId() == walk.getOwnerId() && !walk.isFinished()}">
					<a href="cancel-walk?id=${walk.getWalkId()}" class="btn cancel mt-2">Cancel Walk</a>
				</c:if>
			</div>
			<c:choose>
				<c:when test="${user.getId() == walk.getOwnerId()}">
					<c:if test="${walk.getStatus() == 'OWNER_POSTED'}">
						<div>
							<h2 class="subtitle">Active Offers</h2>
							<c:choose>
								<c:when test="${offers.size() > 0}">
									<ul>
										<c:forEach var="walkOffer" items="${offers}">
											<li class="mt-2">${walkOffer.getWalkOfferUser().getEmail()} 
												<a href="accept-offer?id=${walk.getWalkId()}&walker=${walkOffer.getWalkOfferUser().getId()}" class='btn ml-2'>Select</a>
												<a href="reject-offer?id=${walk.getWalkId()}&walker=${walkOffer.getWalkOfferUser().getId()}" class='btn ml-2'>Reject</a>
											</li>
										</c:forEach>
									</ul>
								</c:when>
								<c:otherwise>
									<p>No offers yet!</p>
								</c:otherwise>
							</c:choose>
						</div>
					</c:if>
				</c:when>
				<c:otherwise>
					<div>
						<c:choose>
							<c:when test="${walk.getStatus() == 'OWNER_POSTED'}">
								<c:if test="${!offer}">
									<a href="create-offer?id=${walk.getWalkId()}" class="btn">Offer Service</a>
								</c:if>
								<c:if test="${offer}">
									<a href="cancel-offer?id=${walk.getWalkId()}" class="btn cancel">Cancel Offer</a>
								</c:if>
							</c:when>
							<c:otherwise>
								<c:if test="${walk.getWalkerId() == user.getId()}">
									<c:if test="${!walk.isFinished()}">
										<a href="cancel-walk-walker?id=${walk.getWalkId()}" class="btn cancel mt-2">Cancel Walk</a>
									</c:if>
									
									<c:if test="${walk.getStatus() == 'WALKER_CHOSEN'}">
										<a href="start-walk-walker?id=${walk.getWalkId()}" class="btn cancel">Start Walk</a>
									</c:if>
									<c:if test="${walk.getStatus() == 'WALKER_STARTED'}">
										<a href="complete-walk-walker?id=${walk.getWalkId()}" class="btn cancel">Complete Walk</a>
									</c:if>
								</c:if>
							</c:otherwise>
						</c:choose>
					</div>
				</c:otherwise>
			</c:choose>
		</section>
	</main>
</body>
</html>



