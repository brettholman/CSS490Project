<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_admin.jsp" />

<%@ page import="finalproject.models.*" %>
<%@ page import="finalproject.data.*" %>

<!-- start the middle column -->

<section>

	<h1>User Management</h1>
<%
	User[] users = userDB.getAllUsers();
	if(users != null) {
%>
	<ul>
		<%for(User user: users) { %>
		<li><%= user.getUserName() %>    <button onclick="userDB.deleteUser(<%user.getId();%>)">remove</button></li>
		<%} %>
	</ul>
<%} %>
</section>

<!-- end the middle column -->

<jsp:include page="/includes/footer.jsp" />
