<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_home.jsp" />

<%@ page import="finalproject.models.*" %>
<%@ page import="finalproject.data.*" %>

<!-- start the middle column -->

<%
	InventoryItem item = (InventoryItem)session.getAttribute("currentItem");
	User user = (User)session.getAttribute("currentUser");

	boolean hasUserRating = false;
%>

<script>
function addRating(id) {
	var cboValue = document.getElementById("cbo").value
	if('<%=user.getUserName()%>' == 'Anonymous') {
		alert("You must be logged on to submit a rating!");
		return;
	}
	if(cboValue == 0) {
		alert("You must select a value for your rating!")
		return;
	}
	document.getElementById("rating").value = cboValue;
	document.getElementById("itemID").value = id;
	document.getElementById("description").value = document.getElementById("descriptionInput").value
	alert(document.getElementById("description").value);
	document.AddRating.submit();	
}

function addItemToCart(id){
	document.getElementById("addItemID").value=id;
	document.getElementById("itemQuantity").value=1;
	document.itemAddToCart.submit();
}
</script>

<section>

	<div class="catalogItem">
		<label for="title">Title</label>
		<input type="text" name="title" id="title" value="<%=item.getTitle()%>" readonly><br>
	
		<label for="author">Title</label>
		<input type="text" name="author" id="author" value="<%=item.getAuthor()%>" readonly><br>
	
		<label for="description">Description</label>
		<textarea rows=10 cols=80 name="description" id="description" readonly><%=item.getDescription()%></textarea><br>
	
		<label for="category">Category</label>
		<input type="text" name="category" id="category" value="<%=item.getCategory()%>" readonly><br>
		
		<label for="price">Price</label>
		<input type="number" name="price" id="price" min="0" max="999999.99" step="0.01" value=<%=item.getPrice()%> readonly><br>
	
		<label for="quantity">Quantity</label>
		<input type="number" name="quantity" id="quantity" min="0" max="9999" value=<%=item.getQuantityInStock()%> readonly><br>
	
		<br/>
		<a href="javascript:addItemToCart('<%=item.getId()%>');"><button>add to cart</button></a></span>
		<br/>
	</div>
	
	<br>
	<h2>Ratings</h2>
	
	<%
		Rating[] ratings = ratingDB.getAllRatingsForABook(item.getId());
		if(ratings != null) {
	%>
	
	<table id="list">
		<tr>
			<th>User</th>
			<th>Rating</th>
			<th>Description</th>
		</tr>

		<% for(Rating r: ratings){ %>
		<tr>
			<td width="20%"><%=r.getuUserName()%></td>
			<td width="20%"><%=r.getRating()%></td>
			<td width="40%"><%=r.getDescription()%></td>
		</tr>
		<% if(r.getuUserName().equals(user.getUserName())) {
				hasUserRating = true;
		   }%>
		<% } %>
	</table>	
	<% } else { %>
	<p>No ratings found</p>
	<% } %>
	
	<%if(!hasUserRating) {%> <!--  Decided that the link just shouldn't be there if the user has a rating already.  -->
	<form name="AddRating" method="post" action="/UserController/addRating">
		<input type="hidden" name="itemID" id="itemID">
		<input type="hidden" name="rating" id="rating">
		<input type="hidden" name="description" id="description">
		<div>Description:</div>
	</form>
	<textarea name="descriptionInput" id="descriptionInput" rows="4" cols=""></textarea>
	<br>
	<select id="cbo"> 
			<option value="0"></option>
			<option value="1">1</option>
			<option value="2">2</option>
			<option value="3">3</option>
			<option value="4">4</option>
			<option value="5">5</option>
	</select>
	<br>
	<br>
		<a href = "javascript:addRating(<%=item.getId()%>)">Add a rating</a> 
		<br>
		<br>
	<%} else {%>
	<br>
	<%} %>
	<a href="/shopping/catalog.jsp">Back</a>

	<form name="itemAddToCart" method="post" action="/UserController/addItemToCart">
		<input type="hidden" name="itemID" id="addItemID">
		<input type="hidden" name="itemQuantity" id="itemQuantity">
	</form>	

</section>

<!-- end the middle column -->

<jsp:include page="/includes/footer.jsp" />




