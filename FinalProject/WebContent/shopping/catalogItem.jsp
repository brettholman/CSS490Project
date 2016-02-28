<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_home.jsp" />

<%@ page import="finalproject.models.*" %>
<%@ page import="finalproject.data.*" %>

<!-- start the middle column -->

<%
	InventoryItem item = (InventoryItem)session.getAttribute("currentItem");
	User user = (User)session.getAttribute("currentUser");
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
	document.getElementById("itemID").value=id;
	document.AddRating.submit();	
}
</script>

<section>

	<h1>Catalog Item</h1>

	<p>Title: <%=item.getTitle()%></p>
	<p>Author: <%=item.getAuthor()%></p>
	<p>Category: <%=item.getCategory()%></p>
	<br>
	<p>Description:
	<br><%=item.getDescription()%></p>
	<br>
	<p>Price: $<%=item.getPrice()%></p>
	<p>QuantityInStock: <%=item.getQuantityInStock()%></p>
	<br>
	<h2>Ratings</h2>
	
	<%
		Rating[] ratings = ratingDB.getAllRatingsForABook(item.getId());
		if(ratings != null) {
	%>
	
	<table id="list">
		<tr>
			<th>User</th>
			<th>Rating<button>Test!</button></th>
			<th>Description</th>
		</tr>

	<% for(Rating r: ratings){ %>
		<tr>
			<td width="20%"><%=r.getuUserName()%></td>
			<td width="20%"><%=r.getRating()%></td>
			<td width="40%"><%=r.getDescription()%></td>
		</tr>
	<% } %>
	</table>	
	<% } else { %>
	<p>No ratings found</p>
	<% } %>
	<a href = "javascript:addRating(<%=item.getId()%>)">Add a rating</a> 
	<select id="cbo"> 
		<option value="0"></option>
		<option value="1">1</option>
		<option value="2">2</option>
		<option value="3">3</option>
		<option value="4">4</option>
		<option value="5">5</option>
	</select>
	<br>
	<a href="/shopping/catalog.jsp">Back</a>
	<form name="AddRating" method="post" action="/UserController/addRating">
		<input type="hidden" name="itemID" id="itemID">
		<input type="hidden" name="rating" id="rating">
	</form>
	
</section>

<!-- end the middle column -->

<jsp:include page="/includes/footer.jsp" />