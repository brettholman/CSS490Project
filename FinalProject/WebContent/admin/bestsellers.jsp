<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_admin.jsp" />

<%@ page import="finalproject.models.*" %>
<%@ page import="finalproject.data.*" %>
<%@ page import="java.util.*" %>
<%@ page import="javafx.util.Pair" %>

<!-- start the middle column -->
<jsp:include page="/includes/bestseller_toolbar.jsp" />

<script>
document.getElementById("sourceID").value=3;
</script>

<section>
	<h1>Best Sellers</h1>
<%
	int topCount = 5;
	if(session.getAttribute("topCount")	!= null) { topCount = (int)session.getAttribute("topCount"); }
	
	int categoryID = -1;
	if(session.getAttribute("categoryID") != null) {
		categoryID = (int)session.getAttribute("categoryID");
		if(categoryID == 0) { categoryID = -1; }
	}
	
	int timeframe = 0;
	if(session.getAttribute("timeframe") != null) {
		timeframe = (int)session.getAttribute("timeframe");
	}
	
	SalesStatItem[] items = marketingDB.getSalesStatItems(topCount, categoryID, timeframe);
	if(items != null) {	
%>

<table id="list">
	<tr>
		<th>Title</th>
		<th>Author</th>
		<th>Category</th>
		<th>QuantitySold</th>
	</tr>
	<% for(SalesStatItem item: items) { %>
	<tr>
		<td width="20%"><%=item.getTitle()%></td>
		<td width="15%"><%=item.getAuthor()%></td>
		<td width="15%"><%=item.getCategory()%></td>
		<td width="10%"><%=item.getQuantitySold()%></td>
	</tr>
	<% } %>
</table>
<% } else { %>
	<br>
	no items found	
<% } %>

</section>

<!-- end the middle column -->

<jsp:include page="/includes/footer.jsp" />