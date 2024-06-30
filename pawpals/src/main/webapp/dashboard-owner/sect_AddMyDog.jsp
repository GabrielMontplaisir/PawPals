		<section class="container">
		
			<header>
				<h1 class="subtitle">Add My Dog</h1>
				
				
			</header>
							<!--  It just seems weird to use the word "Create" when people are adding their dogs -->
			<form id="dogForm" action="AddMyDog" method="post">
			
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