<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_admin.jsp" />

<%@ page import="finalproject.models.*" %>
<%@ page import="finalproject.data.*" %>

<!-- start the middle column -->

<%
	InventoryItem item = (InventoryItem)session.getAttribute("currentItem");
%>

<section>

	<h1>Inventory Item</h1>

	<form action="/AdminController/saveItem" method="post">
		<fieldset>
		
			<label for="title">Title</label>
  			<input type="text" name="title" id="title" value="<%=item.getTitle()%>"><br>
		
			<label for="author">Title</label>
  			<input type="text" name="author" id="author" value="<%=item.getAuthor()%>"><br>
		
			<label for="description">Description</label>
  			<textarea rows=10 cols=80 name="description" id="description"><%=item.getDescription()%></textarea><br>
		
			<label for="category">Category</label>
  			<select name="category" id="category">
				<%
					Category[] categories = categoryDB.getAllCategories();
					for(Category cat: categories){
				%>
				<option <% if(item.getCategory().equalsIgnoreCase(cat.getCategoryName())) { %>selected<% } %>
			  		value=<%=cat.getCategoryName()%>><%=cat.getCategoryName()%></option>
				<%
					}
				%>   			
  			</select><br>
		
			<label for="price">Price</label>
  			<input type="number" name="price" id="price" min="0" max="999999.99" step="0.01" value=<%=item.getPrice()%>><br>
  			
  			<label for="cost">Cost</label>
  			<%if(item.getTitle().equals("NewItem")) {%>
  				<input type="number" name="cost" id="cost" min="0" max="999999.99" step="0.01" value=<%=item.getCost()%>><br>
			<%} else {%>
				<input type="number" name="cost" id="cost" min="0" max="999999.99" step="0.01" value=<%=item.getCost()%> readonly><br>
			<%} %>	
			<label for="quantity">Quantity</label>
  			<input type="number" name="quantity" id="quantity" min="0" max="9999" value=<%=item.getQuantityInStock()%>><br>

			<br>
			<div id="warning" class="feedback"></div>
			<input type="submit" value="Save" id="submit"/>
		</fieldset>

		<br/>
	</form>

	<script>
	 	var elMsg = document.getElementById('warning');
	 	
   		function checkTitle() { 
	  		if (this.value.length <= 0) { // If title too short
	  			elMsg.textContent = 'Title must be 1 or more characters.'; // Set msg
	  		} 
	  		else { elMsg.textContent = ''; }
	  	}
	 	
		function saveItemCheck() {

			// Check the title
			if(document.getElementById('title').value.length <= 0) {
				return false;
			}
		}
		
		var elTitle = document.getElementById('title');
		elTitle.onblur = checkTitle;
		
		var elSubmit = document.getElementById('submit');
		elSubmit.onclick = saveItemCheck;
	</script>

</section>

<!-- end the middle column -->

<jsp:include page="/includes/footer.jsp" />