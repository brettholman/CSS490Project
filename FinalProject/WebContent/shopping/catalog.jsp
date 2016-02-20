<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_home.jsp" />

<%@ page import="finalproject.models.*" %>
<%@ page import="finalproject.data.*" %>

<!-- start the middle column -->

<!-- Catalog toolbar -->
<section id="catalogToolbar" class="pagePart">

    <form method="post" name="form1" id="form1" action="/UserController/filter">

      Search:<input type="text" id="searchText" name="searchText"/>

      Category:<select id="category" name="category">
		  <option value="cat1">All</option>
		  <option value="cat2">Computers & Technology</option>
		  <option value="cat3">History</option>
		  <option value="cat4">Mystery, Thriller, Suspense</option>
		  <option value="cat5">Romance</option>
		  <option value="cat6">Science Fiction, Fantasy</option>
  	  </select>

      <input type="submit" id="submit" value="Go"/>

    </form>

</section>

<!-- Catalog view -->
<section>

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
		<th>Price</th>
		<th></th>
	</tr>
<%
	InventoryItem[] items = inventoryDB.getAllInventoryItems();
	for(InventoryItem item: items){
%>
<tr>
	<td><a href="javascript:viewItemDetails('<%=item.getId()%>');"><%=item.getTitle()%></a></td>
	<td><%=item.getDescription()%></td>
	<td>$<%=item.getPrice()%></td>
	<td width="20%">
	
<% if(item.getQuantityInStock() > 0) { %>	
	<a href="javascript:addItemToCart('<%=item.getId()%>');">[add to cart]</a>
<% } else { %>	
	out of stock
<% } %>	
	
	</td>
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