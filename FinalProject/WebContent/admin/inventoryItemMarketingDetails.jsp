<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_admin.jsp" />

<%@ page import="finalproject.models.*" %>
<%@ page import="finalproject.data.*" %>

<!-- start the middle column -->

<%
	InventoryItem item = (InventoryItem)session.getAttribute("currentItem");
	User[] users = (User[])session.getAttribute("listOfUsers");
%>

<section>
	<h1>Inventory Item Marketing Details</h1>
	<h2>Customers who have purchased the book: <strong>"<%=item.getTitle()%>"</strong> more than two times this past month.</h2>
	<hr>
	<br>
	<table>
		<tr>
			<th width="25%">User Name</th>
			<th width="25%">First Name</th>
			<th width="25%">Last Name</th>
			<th width="25%">Quantity Purchased</th>
		</tr>
	<%if(users != null) {
		for(User user:users) {%>
		<tr>
			<td><%=user.getUserName() %></td>
			<td><%=user.getfName() %></td>
			<td><%=user.getlName() %></td>
			<td><%=user.getTotalPurchased() %></th>
		</tr>
	<%	}
	} else {
		%> No history of purchases for this item
	<%} %>
	</table>
</section>

<!-- end the middle column -->

<jsp:include page="/includes/footer.jsp" />