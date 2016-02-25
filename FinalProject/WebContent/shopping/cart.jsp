<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_home.jsp" />

<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="finalproject.models.*" %>
<%@ page import="finalproject.data.*" %>

<!-- start the middle column -->

<% 
	session.setAttribute("sourceID", 1); 
	User user = (User)session.getAttribute("currentUser");
%>

<section>

	<h1>Shopping Cart</h1>
	
	<script>
	function removeItemFromCart(id){
		document.getElementById("itemID").value=id;
		document.removeItemFromCart.submit();
	}
	function startCheckout(){
	   var username = "<%=user.getUserName()%>";
       if(username === 'Anonymous') {
    	   document.getElementById("sourceID").value=1;
    	   document.startCheckoutWithLogon.submit();
       }
       else {
    	   document.startCheckout.submit();
       }		
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

	<form name="startCheckout" method="post" action="/UserController/removeItemFromCart">
	</form>

	<form name="startCheckoutWithLogon" method="get" action="/UserController/logonManual">
		<input type="hidden" name="sourceID" id="sourceID">
	</form>

	<br>
	<p>Current total is: $<%=orderTotal%></p>
	<a href="javascript:startCheckout();">Checkout</a>

	<% } else { %>
	<p>Your shopping cart is empty.</p>
	<% } %>

	<br>

	<a href="/shopping/catalog.jsp">Continue Shopping</a>

</section>

<!-- end the middle column -->

<jsp:include page="/includes/footer.jsp" />