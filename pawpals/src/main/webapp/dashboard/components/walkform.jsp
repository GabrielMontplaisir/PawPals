<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<form action="create-walk" method="POST" class="form">
	<input type="hidden" id="id" name="id" value="${walk.getWalkId()}">
	<label for="selectdogs" class="form_label">Select Dogs for
		this walk:</label> <select class="form_multiple_select border-2 rounded"
		id="selectdogs" name="selecteddogs" required multiple>
		<c:forEach var="dog" items="${userDogs}">
			<option value="${dog.key}" ${dogIds.contains(dog.key) ? 'selected' : ''}>${dog.value.getName()}</option>
		</c:forEach>
	</select>
	<div class="form_group">
		<label class="form_label mt-2">Location: <input
			type="text" id="location" name="location" class="form_input" value="${walk.getLocation()}"
			required></label> 
		<label class="form_label mt-2">Select Date &amp; Time: 
			<input type="datetime-local" name="starttime" class="form_input" value="${date}" required>
		</label>
	</div>

	<label for="length" class="form_label mt-2">Length:</label> 
	<select
		id="length" name="length"
		class="form_single_select border-2 rounded w-full" required>
		<option value="" disabled selected>Select Length</option>
		<option value="30" ${walk.getLength().equals('30') ? 'selected' : ''}>30min</option>
		<option value="60" ${walk.getLength().equals('60') ? 'selected' : ''}>60min</option>
		<option value="90" ${walk.getLength().equals('90') ? 'selected' : ''}>90min</option>
		<option value="120" ${walk.getLength().equals('120') ? 'selected' : ''}>120min</option>
		<option value="150" ${walk.getLength().equals('150') ? 'selected' : ''}>150min</option>
		<option value="180" ${walk.getLength().equals('180') ? 'selected' : ''}>180min</option>
	</select>
	<div class="flex">
		<input type="submit" value="Submit" class="form_btn mt-2 flex-1">
		<c:if test="${walk.getWalkId() != null}">
			<input type="button" value="Cancel" onclick="window.location='${pageContext.request.contextPath}/dashboard/walkdetails?id=${walk.getWalkId()}'" class="btn ml-2 mt-2 cancel flex-1">
		</c:if>
	</div>

</form>