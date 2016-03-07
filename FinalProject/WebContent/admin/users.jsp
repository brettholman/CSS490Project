<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_admin.jsp" />

<%@ page import="finalproject.models.*" %>
<%@ page import="finalproject.data.*" %>

<!-- start the middle column -->

<%
	User[] users = userDB.getAllUsers();
	if(users != null) {
%>
	<ul>
		<%for(User user: users) { %>
		<li><%= user.getUserName() %>    <a href="/UserController/removeUser?id=<%user.getId();%>">remove</a></li>
		<%} %>
	</ul>
<%} %>


<!-- end the middle column -->

<jsp:include page="/includes/footer.jsp" />
