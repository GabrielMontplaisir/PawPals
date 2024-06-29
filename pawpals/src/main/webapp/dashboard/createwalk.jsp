<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.pawpals.beans.Dog" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1.0" >
	<title>PawPals | Create a Walk</title>
	<link rel="preconnect" href="https://fonts.googleapis.com">
	<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
	<link href="https://fonts.googleapis.com/css2?family=Nunito:ital,wght@0,200..1000;1,200..1000&display=swap" rel="stylesheet">
	<link href="https://fonts.googleapis.com/css2?family=PT+Sans:ital,wght@0,400;0,700;1,400;1,700&display=swap" rel="stylesheet">
	<link rel="stylesheet" href="../css/root.css">
	<link rel="stylesheet" href="../css/dashboard.css">
</head>
<%
	if (session.getAttribute("user") == null) {
		response.sendRedirect("../index.jsp");
		return;
	}
	
	

%>
<body class="dashboard">
	<header class="dashboard_header">
		<h2>PawPals</h2>
		<p class="welcome_msg">Welcome ${user.getFirstName()}!</p>
		<nav>
		<a href="./walker.jsp" class="nav_btn">Switch to Walker</a>
			<a href="./settings.jsp" class="nav_btn">Settings</a>
			<a href="logout" class="nav_btn">Logout</a>
		</nav>
	</header>
	<main>
		<section class="container">
		
			<header>
				<h1 class="subtitle">Add a Dog</h1>
				
				
			</header>
			<form id="dogForm" action=createwalk method="post">
			
                <div class="form-group">
                    <label for="name">Name:</label>
                    <input type="text" id="name" name="name" class="small-input"required>
                </div>
                
                <div class="form-group">
                    <label for="size">Size:</label>
                    <select id="size" name="size" required>
                    	<option value="" disabled selected>Select size</option>
                        <option value="small">Small</option>
                        <option value="medium">Medium</option>
                        <option value="large">Large</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <label for="special_needs">Special Needs:</label>
                    <input type="text" id="special_needs" name="special_needs" class="small-input">
                </div>
                
                <div class="form-group">
                    <label for="immunized">Immunized:</label>
                    <input type="checkbox" id="immunized" name="immunized">
                </div>
                
                <input type="submit" value="Submit">
				
			</form>
		</section>
		<section class="container">
		
			<header>
			<Form>
				<h1 class="subtitle">Create a Walk</h1>
				<!-- 
				<a href="javascript:void(0);" class="btn" onclick="showDogForm()">Add dog</a>
				 -->
				 
				<div class="form-group">
                    <label for="size">Dog:</label>
                    <select id="size" name="size" required>
                    	<option value="" disabled selected>Select dog</option>
                    	<% 
	                    	if (  request.getAttribute("dogs") != null) {
		                    	List<Dog> dogList = (List<Dog>) request.getAttribute("dogs");
		                    	for (Dog dog: dogList){
		                    		out.write("<option value=" + dog.getDogId() +">" + dog.getName() + "</option>");
		                    	}
	                    	} else {
	                    		System.out.println("Dawgs list is teh null mmkay");
	                    	}
                    	%>
                    </select>
                </div>
                
                <div class="form-group">
                    <label for="name">Location:</label>
                    <input type="text" id="name" name="name" class="small-input"required>
                </div>
                
                <label for="startTime">Select start time: </label>
                <input type="time" required>
                
                <div class="form-group">
                    <label for="length">Length:</label>
                    <select id="length" name="length" required>
                    	<option value="" disabled selected>Select Length</option>
                    	<option value="">30min</option>
                    	<option value="">60min</option>
                    	<option value="">90min</option>
                    	<option value="">120min</option>
                    	<option value="">150min</option>
                    	<option value="">180min</option>
                        
                    </select>
                </div>
                
                 <input type="submit" value="Submit">
				
			</Form>
				
			</header>
			
		</section>
	</main>
	
</body>
</html>