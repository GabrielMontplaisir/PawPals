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
<body class="dashboard">
	<jsp:include page="./components/header.jsp" />
	<main>
		<div class="flex">
			<section class="container">
				<header>
					<h1 class='subtitle'>Walk My Dog(s)</h1>
				</header>
				<form action="create-walk" method="POST" class="form">
					<label for="selectdogs" class="form_label">Select Dogs for
						this walk:</label> <select class="form_multiple_select border-2 rounded"
						id="selectdogs" name="selecteddogs" required multiple>
						<c:forEach var="dog" items="${dogs}">
							<option value="${dog.getDogId()}">${dog.getName()}</option>
						</c:forEach>
					</select>
					<div class="form_group">
						<label class="form_label mt-2">Location: <input
							type="text" id="location" name="location" class="form_input"
							required></label> <label class="form_label mt-2">Select
							start time: <input type="time" name="starttime"
							class="form_input" required>
						</label>
					</div>

					<label for="length" class="form_label mt-2">Length:</label> <select
						id="length" name="length"
						class="form_single_select border-2 rounded w-full" required>
						<option value="" disabled selected>Select Length</option>
						<option value="">30min</option>
						<option value="">60min</option>
						<option value="">90min</option>
						<option value="">120min</option>
						<option value="">150min</option>
						<option value="">180min</option>
					</select> <input type="submit" value="Submit" class="form_btn mt-2">

				</form>

			</section>

			<section class="container">
				<header>
					<h2 class="subtitle">Add a Dog</h2>
				</header>
				<form action="add-dog" method="POST">
					<label class="form_label">Name: <input type="text"
						id="name" name="name" class="form_input" required></label>

					<fieldset id="size" name="size" class="form_group mt-2">
						<legend class="form_label">Dog size</legend>
						<label><input type="radio" name="size" value="sm"
							class="mr-2" required>Small</label> <label><input
							type="radio" name="size" value="md" class="mr-2" required>Medium</label>
						<label><input type="radio" name="size" value="lg"
							class="mr-2" required>Large</label>
					</fieldset>

					<label for="specialneeds" class="form_label mt-2">Special
						Needs: </label>
					<textarea id="specialneeds" name="specialneeds"
						class="border-2 w-full rounded"></textarea>
					<label class="form_label mt-2">Immunized: <input
						type="checkbox" id="immunized" name="immunized" class="ml-2"></label>


					<input type="submit" value="Submit" class="form_btn mt-2">

				</form>
			</section>
		</div>
		<section class="mt-8">
			<h2 class="subtitle">Current Walks</h2>
			<ul class="walk_list">
				<c:if test="${walks != null}">
					<c:forEach var="walk" items="${walks}">
						<c:if test="${!walk.isFinished()}">
							<li><a href="./walkdetails?id=${walk.getWalkId()}" class="walk_card"><span>Walk in ${walk.getLocation()} at ${walk.getDate()}</span><span class="ml-auto">${walk.getStatus().toString()}</span></a></li>
						</c:if>
					</c:forEach>
				</c:if>
			</ul>

		</section>
		<section class="mt-8">
			<h2 class="subtitle">Past Walks</h2>
			<ul class="walk_list">
				<c:if test="${walks != null}">
					<c:forEach var="walk" items="${walks}">
						<c:if test="${walk.isFinished()}">
							<li><a href="./walkdetails?id=${walk.getWalkId()}" class="walk_card"><span>Walk in ${walk.getLocation()} at ${walk.getDate()}</span><span class="ml-auto">${walk.getStatus().toString()}</span></a></li>
						</c:if>
					</c:forEach>
				
				</c:if>
			</ul>

		</section>
	</main>
</body>
</html>
