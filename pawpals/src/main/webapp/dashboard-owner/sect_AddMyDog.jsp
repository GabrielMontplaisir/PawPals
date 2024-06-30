		<section class="container">
		
			<header>
				<h1 class="subtitle">Add My Doggy</h1>
				
				
			</header>
							<!--  It just seems weird to use the word "Create" when people are adding their dogs -->
			<form id="dogForm" action="AddMyDog" method="post">
			
                <div class="form-group, border1">
                    <label for="name">Name:</label> 
                    <input type="text" id="name" name="name" class="small-input"required>
                </div>
                
                <div class="form-group">
                <fieldset id="size" name="size" class="border1 hRadioGroup">
                	<legend>Doggy size</legend>
					<label><input type="radio" name="size" value="Small" required>Small</label>
					<label><input type="radio" name="size" value="Medium" required>Medium</label>
					<label><input type="radio" name="size" value="Large" required>Large</label>
                </fieldset>
                </div>      
                  
                <div class="form-group, border1">
                    <label for="special_needs">Special Needs:</label>
                    <input type="text" id="special_needs" name="special_needs" class="small-input">
                </div>
                
                <div class="form-group, border1">
                    <label for="immunized">Immunized:</label>
                    <input type="checkbox" id="immunized" name="immunized">
                </div>
                
                <input type="submit" value="Submit">
				
			</form>
		</section>