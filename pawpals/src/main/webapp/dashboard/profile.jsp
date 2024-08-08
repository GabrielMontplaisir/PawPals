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
<body class="background">
	<jsp:include page="./components/header.jsp" /> 
	<main>
		<header class="main_header">
			<h1 class="subtitle">Your Profile</h1>
			<div>
				<a href="./owner" class="btn mr-2">Create a Walk</a>
				<a href="./walker" class="btn">Find Walks</a>
			</div>
		</header>
		<div class="flex gap-8 w-72">
			<section class="container mt-2 flex-2">
				<h2 class="subtitle">Your information</h2>
				<form action="profile" method="POST">
					<input type="hidden" name="action_info" value="${action_info.isBlank() ? 'save' : 'update'}">
					<div class="flex my-2">
						<label for="fname" class="form_label flex-1">First Name:</label><input type="text" id="fname" name="firstname" class="form_input w-full flex-2" value="${user.getFirstName()}" ${action_info} >
						
					</div>
					<div class="flex my-2">
						<label for="lname" class="form_label flex-1">Last Name: </label><input type="text" id="lname" name="lastname" class="form_input w-full flex-2" value="${user.getLastName()}" ${action_info}>
					</div>
					<div class="flex my-2">
						<label for="email" class="form_label flex-1">Email address: </label><input type="email" id="email" name="email" class="form_input w-full flex-2" value="${user.getEmail()}" ${action_info}>
					</div>
					<div class="flex my-2">
						<label for="dob" class="form_label flex-1">Date of Birth: </label><input type="date" id="dob" name="dob" class="form_input w-full flex-2" value="${user.getDob()}" ${action_info}>
					</div>
					<c:if test="${action_info.isBlank()}">
						<input type="submit" name="submit" value="Save" class="btn">
						<input type="button" value="Cancel" onclick="window.location='${pageContext.request.contextPath}/dashboard/profile'" class="btn ml-2 cancel">
					</c:if>
					<c:if test="${action_info.equals('disabled')}">
						<input type="submit" name="submit" value="Update Info" class="btn">
					</c:if>
				</form>
				<form action="profile" method="POST">
					<input type="hidden" name="action_pass" value="${action_pass.isBlank() ? 'save' : 'update'}">
					<div class="flex my-2">
						<label for="password" class="form_label flex-1">Current Password: </label><input type="password" id="password" name="password" class="form_input w-full flex-2" value="placeholder" ${action_pass}>
					</div>
					<c:if test="${action_pass.isBlank()}">
						<div class="flex my-2">
							<label for="new_password" class="form_label flex-1">New Password: </label><input type="password" id="new_password" name="new_password" class="form_input w-full flex-2">
						</div>
						<div class="flex my-2">
							<label for="confirm_new" class="form_label flex-1">Confirm New Password: </label><input type="password" id="confirm_new" name="confirm_new" class="form_input w-full flex-2">
						</div>
						<input type="submit" name="submit" value="Save" class="btn">
						<input type="button" value="Cancel" onclick="window.location='${pageContext.request.contextPath}/dashboard/profile'" class="btn ml-2 cancel">
					</c:if>
					<c:if test="${action_pass.equals('disabled')}">
						<input type="submit" name="submit" value="Update Password" class="btn">
					</c:if>
				</form>
				<p class="message_error mt-2">${message}</p>
				<p class="message_success mt-2">${success}</p>
			</section>
			
			<c:if test="${update_dog.equals('enabled')}">
				<section class="container mt-2 flex-1">
					<header>
						<h2 class="subtitle">Update Dog</h2>
					</header>
					<!-- Include Add Dog Form -->
					<jsp:include page="./components/dogform.jsp" />
				</section>
			</c:if>

			
		</div>
		
		<h2 class="subtitle my-2">Your Dogs</h2>
		<ul class="card_list">
			<c:forEach var="dog" items="${dogs}">
				<li class="card">
				<header class="card_header">
					<h4 class="card_title">${dog.value.getName()}</h4>
					<a href="profile?id=${dog.key}" class="ml-auto" title="Update dog"><img src="${pageContext.request.contextPath}/assets/images/edit_24.png" alt="Update dog"></a>
					<a href="remove-dog?id=${dog.key}" class="error" title="Remove dog"><img src="${pageContext.request.contextPath}/assets/images/delete_24.svg" alt="Remove dog"></a>
				</header>
					
					<p>Size: ${dog.value.getSize()}</p>
					<p>Immunized: ${dog.value.isImmunized()}</p>
					<p>Special Needs: ${dog.value.getSpecialNeeds()}</p>
				</li>					
			</c:forEach>
		</ul>
	</main>
</body>
</html>