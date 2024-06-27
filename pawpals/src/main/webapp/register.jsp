<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0" >
	<title>Welcome to PawPals</title>
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Nunito:ital,wght@0,200..1000;1,200..1000&display=swap" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css2?family=PT+Sans:ital,wght@0,400;0,700;1,400;1,700&display=swap" rel="stylesheet">
	<link rel="stylesheet" href="./css/root.css">
	<link rel="stylesheet" href="./css/login.css">
</head>
<body class="login">
	<main>
		<header>
			<h1 class="title center">Register to PawPals</h1>
		</header>
		<form class="login_form" method="POST" action="register">
			<label for="email" class="login_form_label">Email:</label>
			<input id="email" type="email" name="email" placeholder="johnsmith@email.com" class="login_form_input" pattern="\S+@\S+\.\S+" required />
			<fieldset class="mt-2">
				<label for="firstname" class="login_form_label">First Name: 
					<input id="firstname" type="text" name="firstname" placeholder="John" class="login_form_input" required />
				</label>
				<label for="lastname" class="login_form_label">Last Name:
					<input id="lastname" type="text" name="lastname" placeholder="Smith" class="login_form_input" required />
				</label>
			</fieldset>
			<label for="dob" class="login_form_label mt-2">Date of Birth:</label>
			<input id="dob" type="date" name="dob" class="login_form_input" required />
			<label for="password" class="login_form_label mt-2">Password:</label>
			<input id="password" type="password" name="password" placeholder="Enter your password" class="login_form_input" required />
			<input id="submit" type="submit" name="submit" value="Register" class="login_form_btn mt-2"/>
		</form>
		<p class="mt-2 center">Already have an account? <a href="./index.jsp" class="login_link">Login Now</a></p>
		<p class="login_error mt-2">${requestScope.message}</p>
		<p></p>
	</main>
	
	<footer class="center">
		<p>Created by Nadia, Jeffrey & Gabriel</p>
		<p>for CST8288 -- Algonquin College (2024)</p>
	</footer>
</body>
</html>