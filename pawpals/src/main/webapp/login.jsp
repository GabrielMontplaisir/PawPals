<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0" >
	<title>PawPals | Login</title>
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Nunito:ital,wght@0,200..1000;1,200..1000&display=swap" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css2?family=PT+Sans:ital,wght@0,400;0,700;1,400;1,700&display=swap" rel="stylesheet">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/root.css" type="text/css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css" type="text/css">
</head>
<body class="background">
	<main>
		<header>
			<h1 class="title center">Log In to PawPals</h1>
		</header>
		<form class="login_form" method="POST" action="login">
			<label for="email" class="login_form_label">Email:</label>
			<input id="email" type="email" name="email" placeholder="Enter your email" pattern="\S+@\S+\.\S+" class="login_form_input" required />
			<label for="password" class="login_form_label mt-2">Password:</label>
			<input id="password" type="password" name="password" placeholder="Enter your password" class="login_form_input" required />
			<input id="submit" type="submit" name="submit" value="Log in" class="login_form_btn mt-2"/>
		</form>
		<p class="mt-2 center">Don't have an account? <a href="./register" class="login_link">Register Now</a></p>
		<p class="message_error mt-2">${message}</p>
	</main>
	
	<footer class="center">
		<p>Created by Nadia, Jeffrey &amp; Gabriel</p>
		<p>for CST8288 -- Algonquin College (2024)</p>
	</footer>
</body>
</html>