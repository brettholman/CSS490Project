<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_admin.jsp" />

<%@ page import="finalproject.models.*" %>
<%@ page import="finalproject.data.*" %>

<!-- start the middle column -->
<!-- Catalog view -->
<section>

	<h1>User Management</h1>

<script>
function removeUser(id){
	document.getElementById("userID").value=id;
	document.removeUserForm.submit();
}
</script>

<%
	User currentUser = (User)session.getAttribute("currentUser");
	User[] users = userDB.getAllUsers();
	if(users != null) {
%>

<table id="list">
	<tr>
		<th>ID</th>
		<th>UserName</th>
		<th>Email</th>
		<th>LastLogin</th>
		<th>AccountCreated</th>
		<th>RemoveUser</th>
	</tr>
	<%for(User user: users) { %>
	<tr>
		<td width="5%"><%=user.getId()%></td>
		<td width="15%"><%=user.getUserName()%></td>
		<td width="15%"><%=user.getEmail()%></td>
		<td width="15%"><%=user.getLastLogin()%></td>
		<td width="15%"><%=user.getAccountCreated()%></td>
		
		<td width="15%">
		<% if(user.getId() != currentUser.getId()) { %>
		<a href="javascript:removeUser('<%=user.getId()%>');"><button>remove</button></a>
		<% } %>
		</td>
	</tr>
	<% } %>
</table>
<% } else { %>
	no items found	
<% } %>

<form name="removeUserForm" method="post" action="/AdminController/deleteUser">
	<input type="hidden" name="userID" id="userID">
</form>

</section>

<!-- end the middle column -->

<jsp:include page="/includes/footer.jsp" />