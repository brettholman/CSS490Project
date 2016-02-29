<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_admin.jsp" />

<%@ page import="finalproject.models.*" %>
<%@ page import="finalproject.data.*" %>
<%@ page import="java.util.*" %>
<%@ page import="javafx.util.Pair" %>

<!-- start the middle column -->
<jsp:include page="/includes/bestseller_toolbar.jsp" />

<script>
document.getElementById("sourceID").value=3;
</script>

<section>
	<h1>Best Sellers</h1>
<%
	String searchText = (String)session.getAttribute("searchText");
	int max = 10;
	if(searchText != null && searchText.length() > 0) {
		max = Integer.parseInt(searchText);
	}
	int categoryID = (int)session.getAttribute("categoryID");
	if(categoryID == 0)
	{
		categoryID = -1;
	}
	
	ArrayList<Pair<InventoryItem, String>> items = inventoryDB.getBestSellers(max, categoryID);
	if(items != null) {
%>

<table id="list">
	<tr>
		<th>Title</th>
		<th>Author</th>
		<th>Description</th>
		<th>Category</th>
		<th>Price</th>
		<th>QuantityInStock</th>
		<th>UserRating</th>
		<th></th>
	</tr>
	<% for(int i = 0; i < items.size(); i++) { %>
	<tr>
		<td width="20%"><%=items.get(i).getKey().getTitle()%></a></td>
		<td width="15%"><%=items.get(i).getKey().getAuthor()%></td>
		<td width="25%"><%=items.get(i).getKey().getDescription()%></td>
		<td width="15%"><%=items.get(i).getKey().getCategory()%></td>
		<td width="10%">$<%=items.get(i).getKey().getPrice()%></td>
		<td width="10%"><%=items.get(i).getKey().getQuantityInStock()%></td>
		<td width="10%"><%=items.get(i).getKey().getAverageRating()%></td>
	</tr>
	<% } %>
</table>
<% } else { %>
	no items found	
<% } %>

</section>

<!-- end the middle column -->

<jsp:include page="/includes/footer.jsp" />