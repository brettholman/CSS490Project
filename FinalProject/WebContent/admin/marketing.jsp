<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_admin.jsp" />

<%@ page import="finalproject.models.*" %>
<%@ page import="finalproject.data.*" %>

<!-- start the middle column -->

<section>
<!-- Catalog view -->

<script>
document.getElementById("sourceID").value=1;

function viewItemMarketingDetails(id){
	document.getElementById("viewItemID").value=id;
	document.viewItemMarketingDetails.submit();
}
function viewCategoryMarketingDetails(id){
	document.getElementById("viewItemID").value=id;
	document.viewCategoryMarketingDetails.submit();
}
</script>

<%
	String searchText = (String)session.getAttribute("searchText");
	int categoryID = (int)session.getAttribute("categoryID");

	InventoryItem[] items = inventoryDB.getAllItems(searchText, categoryID);
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
	</tr>
<% for(InventoryItem item: items) { %>
<tr>
	<td width="20%"><a href="javascript:viewItemMarketingDetails('<%=item.getId()%>');"><%=item.getTitle()%></a></td>
	<td width="10%"><%=item.getAuthor()%></td>
	<td width="25%"><%=item.getDescription()%></td>
	<td width="10%"><a href="javascript:viewCategoryMarketingDetails('<%=item.getCategoryID()%>');"><%=item.getCategory()%></a></td>
	<td width="10%">$<%=item.getPrice()%></td>
	<td width="10%"><%=item.getAverageRating()%></td>
	<td width="15%">
	<% } %>	
	</td>
</tr>
</table>
<% } else { %>
	no items found	
<% } %>

<form name="viewItemMarketingDetails" method="post" action="/AdminController/viewItemMarketingDetails">
	<input type="hidden" name="itemID" id="viewItemID">
</form>

<form name="viewCategoryMarketingDetails" method="post" action="/AdminController/viewCategoryMarketingDetails">
	<input type="hidden" name="itemID" id="viewItemID">
</form>

</section>

<!-- end the middle column -->

</section>

<!-- end the middle column -->

<jsp:include page="/includes/footer.jsp" />