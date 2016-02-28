<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_admin.jsp" />

<%@ page import="finalproject.models.*" %>
<%@ page import="finalproject.data.*" %>
<%@ page import="java.util.*" %>
<%@ page import="javafx.util.Pair" %>

<!-- start the middle column -->

<section>
<%Category[] allCategories = categoryDB.getAllCategories();%>
	<h1>Bestseller List</h1>
	<span>
	Filter By Category
	<select>
		<option value="-1" id="cbo">All Categories</option>
<%for(int i = 0; i < allCategories.length; i++){%>
		<option value = <%=allCategories[i].getCategoryName()%>><%= allCategories[i].getCategoryName()%></option>
<%}%>
	</select>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Filter Quantity (Default 10) <input type="text" width="10px" id="max"></input>
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<Button>Refresh</Button>
	</span>
<%
	ArrayList<Pair<InventoryItem, String>> items = inventoryDB.getBestSellers(10, -1);
	if(items != null) {
%>
<hr>
<br>
<table id="list">
	<tr>
		<th>Title</th>
		<th>Author</th>
		<th>Description</th>
		<th>Category</th>
		<th>Price</th>
		<th>QuantityInStock</th>
		<th>UserRating</th>
		<th>TotalSold</th>
		<th></th>
	</tr>
	<% for(int i = 0; i < items.size(); i++) { %>
	<tr>
		<td width="15%"><%=items.get(i).getKey().getTitle()%></td>
		<td width="15%"><%=items.get(i).getKey().getAuthor()%></td>
		<td width="25%"><%=items.get(i).getKey().getDescription()%></td>
		<td width="15%"><%=items.get(i).getKey().getCategory()%></td>
		<td width="10%">$<%=items.get(i).getKey().getPrice()%></td>
		<td width="10%"><%=items.get(i).getKey().getQuantityInStock()%></td>
		<td width="10%"><%=items.get(i).getKey().getAverageRating()%></td>
		<td width="10%"><%=items.get(i).getValue()%></td>
	</tr>
	<% } %>
</table>
<% } else { %>
	no items found	
<% } %>

<form name="editItem" method="post" action="/AdminController/editItem">
	<input type="hidden" name="itemID" id="editItemID">
</form>

</section>

<!-- end the middle column -->

<jsp:include page="/includes/footer.jsp" />