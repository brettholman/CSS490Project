<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_admin.jsp" />

<%@ page import="finalproject.models.*" %>
<%@ page import="finalproject.data.*" %>

<!-- start the middle column -->
<jsp:include page="/includes/catalog_toolbar.jsp" />

<!-- Catalog view -->
<section>

	<h1>Inventory Management</h1>

<script>
function editItem(id){
	document.getElementById("editItemID").value=id;
	document.editItem.submit();
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
		<th>Author</th>
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
	<td width="20%"><a href="javascript:editItem('<%=item.getId()%>');"><%=item.getTitle()%></a></td>
	<td width="15%"><%=item.getAuthor()%></td>
	<td width="25%"><%=item.getDescription()%></td>
	<td width="15%"><%=item.getCategory()%></td>
	<td width="10%">$<%=item.getPrice()%></td>
	<td width="10%"><%=item.getQuantityInStock()%></td>
	<td width="10%"><%=item.getAverageRating()%></td>
</tr>
<%
	}
%>
</table>
</form>

<form name="editItem" method="post" action="/AdminController/editItem">
	<input type="hidden" name="itemID" id="editItemID">
</form>

</section>

<!-- end the middle column -->

<jsp:include page="/includes/footer.jsp" />