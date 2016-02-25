<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_home.jsp" />

<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="finalproject.models.*" %>
<%@ page import="finalproject.data.*" %>

<!-- start the middle column -->

<section>

	<h1>Shopping Cart</h1>
	
	<script>
	function removeItemFromCart(id){
		document.getElementById("itemID").value=id;
		document.removeItemFromCart.submit();
	}
	</script>
	
	<%
		Map<Integer, Integer> shoppingCart = (Map<Integer, Integer>) session.getAttribute("shoppingCart");
		double orderTotal = 0;
		if(shoppingCart != null && shoppingCart.size() > 0) {
	%>
	
	<form name="modify" method="post">
		<table id="list">
			<tr>
				<th>Title</th>
				<th>Author</th>
				<th>PricePerItem</th>
				<th>QuantityOrdered</th>
				<th>RemoveFromCart</th>
			</tr>
		<%
			for (Map.Entry<Integer, Integer> entry : shoppingCart.entrySet()) {
				InventoryItem item = inventoryDB.getInventoryItem(entry.getKey());
				orderTotal += (item.getPrice() * entry.getValue());
		%>
		<tr>
			<td width="20%"><%=item.getTitle()%></td>
			<td width="20%"><%=item.getAuthor()%></td>
			<td width="20%">$<%=item.getPrice()%></td>
			<td width="20%"><%=entry.getValue()%></td>
			<td width="20%"><a href="javascript:removeItemFromCart('<%=item.getId()%>');">[remove]</a></td>
		</tr>
		<%
			}
		%>
		</table>
	</form>
	
	<form name="removeItemFromCart" method="post" action="/UserController/removeItemFromCart">
		<input type="hidden" name="itemID" id="itemID">
	</form>

	<br>
	<p>Current total is: $<%=orderTotal%></p>

	<% } else { %>
	<p>Your shopping cart is empty.</p>
	<% } %>

	<br>

	<a href="/shopping/catalog.jsp">Continue Shopping</a>

</section>

<!-- end the middle column -->

<jsp:include page="/includes/footer.jsp" />