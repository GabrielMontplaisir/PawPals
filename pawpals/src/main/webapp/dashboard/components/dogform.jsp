<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<form action="${dog.getDogId() != null ? 'profile?action=update-dog' : 'add-dog'}" method="POST">
	<input type="hidden" id="dog_id" name="dog_id" value="${dog.getDogId()}">
	<label class="form_label">Name: <input type="text"
		id="name" name="name" class="form_input" value="${dog.getName()}" required></label>

	<fieldset id="size" name="size" class="form_group mt-2">
		<legend class="form_label">Dog size</legend>
			<label><input type="radio" name="size" value="sm"
			class="mr-2" ${dog.getSize().equals('sm') ? 'checked' : ''} required>Small</label> 
			<label><input
			type="radio" name="size" value="md" class="mr-2" ${dog.getSize().equals('md') ? 'checked' : ''} required>Medium</label>
			<label><input type="radio" name="size" value="lg"
			class="mr-2" ${dog.getSize().equals('lg') ? 'checked' : ''} required>Large</label>
	</fieldset>

	<label for="specialneeds" class="form_label mt-2">Special
		Needs: </label>
	<textarea id="specialneeds" name="specialneeds"
		class="border-2 w-full rounded">${dog.getSpecialNeeds()}</textarea>
	<label class="form_label mt-2">Immunized: <input
		type="checkbox" id="immunized" name="immunized" class="ml-2" ${dog.isImmunized() ? 'checked' : ''} ></label>
	<div class="flex">
		<input type="submit" value="Submit" class="form_btn mt-2 flex-1">
		<c:if test="${dog.getDogId() != null}">
			<input type="button" value="Cancel" onclick="window.location='${pageContext.request.contextPath}/dashboard/profile'" class="btn ml-2 mt-2 cancel flex-1">
		</c:if>
		
	</div>
	
</form>