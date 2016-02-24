<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_home.jsp" />

<%@ page import="finalproject.models.*" %>
<%@ page import="finalproject.data.*" %>

<!-- start the middle column -->
<jsp:include page="/includes/catalog_toolbar.jsp" />

<!-- Catalog view -->
<section>

	<h1>Inventory Management</h1>

<script>
function viewItemDetails(id){
	document.getElementById("viewItemID").value=id;
	document.viewItemDetails.submit();
}
function addItemToCart(id){
	document.getElementById("addItemID").value=id;
	document.itemAddToCart.submit();
}
</script>

<form name="modify" method="post">
<table id="list">
	<tr>
		<th>Title</th>
		<th>Description</th>
		<th>Category</th>
		<th>Price</th>
		<th>QuantityInStock</th>
		<th>UserRating</th>
		<th></th>
	</tr>
<%
	InventoryItem[] items = inventoryDB.getAllItemsForCategory(-1);
	for(InventoryItem item: items){
%>
<tr>
	<td width="15%"><a href="javascript:viewItemDetails('<%=item.getId()%>');"><%=item.getTitle()%></a></td>
	<td width="15%"><%=item.getDescription()%></td>
	<td width="15%"><%=item.getCategory()%></td>
	<td width="15%">$<%=item.getPrice()%></td>
	<td width="15%"><%=item.getQuantityInStock()%></td>
	<td width="15%"><%=item.getAverageRating()%></td>
</tr>
<%
	}
%>
</table>
</form>

<form name="viewItemDetails" method="post" action="/UserController/viewItemDetails">
	<input type="hidden" name="itemID" id="viewItemID">
</form>
<form name="itemAddToCart" method="post" action="/UserController/addItemToCart">
	<input type="hidden" name="itemID" id="addItemID">
</form>

</section>

<!-- end the middle column -->

<jsp:include page="/includes/footer.jsp" />