<jsp:include page="/includes/header_home.jsp" />
<jsp:include page="/includes/column_left_home.jsp" />

<%@ page import="finalproject.models.*" %>
<%@ page import="finalproject.data.*" %>

<!-- start the middle column -->

<section>

<script>
	function addToCart(id){
		document.getElementById("itemID").value=id;
		document.userModify.submit();
	}
</script>

<form name="modify" method="post">
<table id="list">
	<tr>
		<th>Title</th>
		<th>Description</th>
		<th>In Stock</th>
		<th>Price</th>
		<th></th>
	</tr>
<%
	InventoryItem[] items = inventoryDB.getAllInventoryItems();
	for(InventoryItem item: items){
%>
<tr>
	<td width="20%"><%=item.getTitle()%></td>
	<td width="20%"><%=item.getDescription()%></td>
	<td width="20%"><%=item.getQuantityInStock()%></td>
	<td width="20%"><%=item.getPrice()%></td>
	<td width="20%">
		<a href="javascript:itemDetails('<%=item.getId()%>');">[view]</a>
		<a href="javascript:itemAddToCart('<%=item.getId()%>');">[add]</a>
	</td>
</tr>
<%
	}
%>
</table>
</form>

<form name="itemDetails" method="post" action="/UserController/viewItemDetails">
	<input type="hidden" name="id" id="itemID">
</form>
<form name="itemAddToCart" method="post" action="/UserController/addItemToCart">
	<input type="hidden" name="id" id="itemID">
</form>

</section>

<!-- end the middle column -->

<jsp:include page="/includes/footer.jsp" />