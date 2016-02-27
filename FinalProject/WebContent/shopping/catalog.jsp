<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_home.jsp" />

<%@ page import="finalproject.models.*" %>
<%@ page import="finalproject.data.*" %>

<!-- start the middle column -->
<jsp:include page="/includes/catalog_toolbar.jsp" />

<!-- Catalog view -->
<section>

<script>
document.getElementById("sourceID").value=1;

function viewItemDetails(id){
	document.getElementById("viewItemID").value=id;
	document.viewItemDetails.submit();
}
function addItemToCart(id){
	document.getElementById("addItemID").value=id;
	document.getElementById("itemQuantity").value=1;
	document.itemAddToCart.submit();
}
</script>

<%
	String searchText = (String)session.getAttribute("searchText");
	String categoryText = (String)session.getAttribute("categoryText");

	InventoryItem[] items = inventoryDB.getAllItemsForCategory(-1);
	if(items != null) {
%>
<table id="list">
	<tr>
		<th>Title</th>
		<th>Author</th>
		<th>Description</th>
		<th>Category</th>
		<th>Price</th>
		<th>Rating</th>
		<th>Order</th>
	</tr>
<% for(InventoryItem item: items) { %>
<tr>
	<td width="20%"><a href="javascript:viewItemDetails('<%=item.getId()%>');"><%=item.getTitle()%></a></td>
	<td width="10%"><%=item.getAuthor()%></td>
	<td width="25%"><%=item.getDescription()%></td>
	<td width="10%"><%=item.getCategory()%></td>
	<td width="10%">$<%=item.getPrice()%></td>
	<td width="10%"><%=item.getAverageRating()%></td>
	<td width="15%">
	<% if(item.getQuantityInStock() > 0) { %>	
	<a href="javascript:addItemToCart('<%=item.getId()%>');">[add to cart]</a>
	<% } else { %>	
	out of stock
	<% } } %>	
	</td>
</tr>
</table>
<% } else { %>
	no items found	
<% } %>

<form name="viewItemDetails" method="post" action="/UserController/viewItemDetails">
	<input type="hidden" name="itemID" id="viewItemID">
</form>
<form name="itemAddToCart" method="post" action="/UserController/addItemToCart">
	<input type="hidden" name="itemID" id="addItemID">
	<input type="hidden" name="itemQuantity" id="itemQuantity">
</form>

</section>

<!-- end the middle column -->

<jsp:include page="/includes/footer.jsp" />