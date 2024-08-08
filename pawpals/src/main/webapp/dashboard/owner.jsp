<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!--  For reference to enumeration (i.e. Status 2 = Posted) -->


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
<title>PawPals | Create a Walk</title>
</head>
<body class="background">
	<jsp:include page="./components/header.jsp" />
	<main>
		<div class="flex gap-8">
			<section class="container flex-1">
				<header>
					<h1 class='subtitle'>Walk My Dog(s)</h1>
				</header>
				<form action="create-walk" method="POST" class="form">
					<label for="selectdogs" class="form_label">Select Dogs for
						this walk:</label> <select class="form_multiple_select border-2 rounded"
						id="selectdogs" name="selecteddogs" required multiple>
						<c:forEach var="dog" items="${dogs}">
							<option value="${dog.key}">${dog.value.getName()}</option>
						</c:forEach>
					</select>
					<div class="form_group">
						<label class="form_label mt-2">Location: <input
							type="text" id="location" name="location" class="form_input"
							required></label> 
						<label class="form_label mt-2">Select Date &amp; Time: 
							<input type="datetime-local" name="starttime" class="form_input" value="${date}" required>
						</label>
					</div>

					<label for="length" class="form_label mt-2">Length:</label> <select
						id="length" name="length"
						class="form_single_select border-2 rounded w-full" required>
						<option value="" disabled selected>Select Length</option>
						<option value="30">30min</option>
						<option value="60">60min</option>
						<option value="90">90min</option>
						<option value="120">120min</option>
						<option value="150">150min</option>
						<option value="180">180min</option>
					</select> <input type="submit" value="Submit" class="form_btn mt-2">

				</form>
				<p class="message_error mt-2">${message}</p>
			</section>

			<section class="container flex-1">
				<header>
					<h2 class="subtitle">Add a Dog</h2>
				</header>
				<!-- Include Add Dog Form -->
				<jsp:include page="./components/dogform.jsp" />
			</section>
		</div>
		<section class="mt-8">
			<h2 class="subtitle">Current Walks</h2>
			<ul class="card_list">
				<c:if test="${walks != null}">
					<c:forEach var="walk" items="${walks}">
						<c:if test="${!walk.value.isFinished()}">
							<li class="card">
								<header class="card_header">
									<h3 class="card_title">Walk in ${walk.value.getLocation()}</h3>
									<p>${walk.value.getStatus().toString()}</p>
								</header>
								<p class="card_details">${walk.value.getFullDate()}</p>
								 <p>Dog(s): ${walk.value.dogNames}</p>
								 <div class="card_footer">
			                        <a href="./walkdetails?id=${walk.value.getWalkId()}" class="btn">View</a>
			                        <p class="offers">Offers: ${walk.value.getOfferCount()}</p>
			                    </div>
							</li>
						</c:if>
					</c:forEach>
				</c:if>
			</ul>
		</section>
		<section class="mt-8">
			<h2 class="subtitle">Past Walks</h2>
			<ul class="card_list">
				<c:if test="${walks != null}">
					<c:forEach var="walk" items="${walks}">
						<c:if test="${walk.value.isFinished()}">
							<li class="card">
								<header class="card_header">
									<h3 class="card_title">Walk in ${walk.value.getLocation()}</h3>
									<p>${walk.value.getStatus().toString()}</p>
								</header>
								<p class="card_details">${walk.value.getFullDate()}</p>
								<p>Dog(s): ${walk.value.dogNames}</p>
								<a href="./walkdetails?id=${walk.value.getWalkId()}" class="btn">View</a>
							</li>
						</c:if>
					</c:forEach>
				
				</c:if>
			</ul>

		</section>
	</main>
</body>
</html>
