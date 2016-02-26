<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_home.jsp" />

<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="finalproject.models.*" %>
<%@ page import="finalproject.data.*" %>

<!-- start the middle column -->

<% 
	User user = (User)session.getAttribute("currentUser");
%>

<section>

	<h1>Checkout</h1>
	
	<%
		Map<Integer, Integer> shoppingCart = (Map<Integer, Integer>) session.getAttribute("shoppingCart");
		double orderTotal = 0;
		if(shoppingCart != null && shoppingCart.size() > 0) {
	%>

	<script>
		function placeOrder(){
		   document.placeOrder.submit();
		}	
	</script>
	
	<table id="list">
		<tr>
			<th>Title</th>
			<th>Author</th>
			<th>PricePerItem</th>
			<th>QuantityOrdered</th>
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
	</tr>
	<%
		}
	%>
	</table>
	<br>

	<% double shippingCharge = 5.99; double totalCharge = shippingCharge + orderTotal; %>
	<p>Subtotal: $<%=orderTotal%></p>
	<p>Shipping: $<%=shippingCharge%></p>
	<p>Total: $<%=totalCharge%></p>
	
	<a href="javascript:placeOrder();">Place Order</a>

	<form name="placeOrder" method="post" action="/UserController/processOrder">
	</form>	

	<% } else { %>
	<p>Your shopping cart is empty.</p>
	<% } %>

	<br>

	<a href="/shopping/catalog.jsp">Continue Shopping</a>

</section>

<!-- end the middle column -->

<jsp:include page="/includes/footer.jsp" />