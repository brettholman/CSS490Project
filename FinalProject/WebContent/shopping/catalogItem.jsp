<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_home.jsp" />

<%@ page import="finalproject.models.*" %>
<%@ page import="finalproject.data.*" %>

<!-- start the middle column -->

<%
	InventoryItem item = (InventoryItem)session.getAttribute("currentItem");
%>

<section>

	<h1>Inventory Item</h1>

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
			<th>Description</th>
			<th>Rating</th>
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
	<br>
	<p>No ratings found</p>
	<% } %>
	
	<br>
	<a href="/shopping/catalog.jsp">Back</a>
	
</section>

<!-- end the middle column -->

<jsp:include page="/includes/footer.jsp" />