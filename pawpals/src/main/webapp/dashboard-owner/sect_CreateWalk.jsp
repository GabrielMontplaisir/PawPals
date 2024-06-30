<%@ page import="com.pawpals.beans.Dog" %>
<%@ page import="com.pawpals.beans.User" %>

	<section class="container">
		
			<header>
			<Form action="CreateWalk" method="POST">
				<%
				User user = (User) session.getAttribute("user");
				if ( user.getDogs_as_DogOwner().size() > 1 ){
					out.write("<h1 class='subtitle'>Walk My Doggie(s)</h1>");
				} else {
					out.write("<h1 class='subtitle'>Walk My Doggy</h1>");
				}
				 %>
				<!-- 
				<a href="javascript:void(0);" class="btn" onclick="showDogForm()">Add dog</a>
				 -->
				 
				<div class="form-group">
                    <label for="size">Select Dogs for this walk:</label> <br />
                    <select class='selectDoggies' id="dogIds" name="dogIds[]" required multiple>
                    	<%
                    	
           	            	for (Dog dog: user.getDogs_as_DogOwner() ){
                	    		out.write("<option value=" + dog.getDogId() +">" + dog.getName() + "</option>");
                	    	}
                    	%>
                    </select>
                </div>
                
                <div class="form-group">
                    <label for="location">Location:</label>
                    <input type="text" id="location" name="location" class="small-input"required>
                </div>
                
                <label for="startTime">Select start time: </label>
                <input type="time" name="startTime" required>
                
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