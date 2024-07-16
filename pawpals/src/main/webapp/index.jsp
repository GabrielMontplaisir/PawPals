<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0" >
	<title>Welcome to PawPals</title>
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Nunito:ital,wght@0,200..1000;1,200..1000&display=swap" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css2?family=PT+Sans:ital,wght@0,400;0,700;1,400;1,700&display=swap" rel="stylesheet">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/root.css" type="text/css">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css" type="text/css">
</head>
<body class="login">
	<main class="center">
		<h1 class="title">Welcome to PawPals!</h1>
		<section class="flex">
			<a href="./login" class="btn">Login</a>
			<a href="./register" class="btn success">Register</a>
		</section>
	</main>
</body>
</html>