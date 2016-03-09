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

function changeRole(userName, newRoleIsAdmin) {
	document.getElementById("userIDUR").value = userName;
	document.getElementById("newRoleIsAdmin").value = newRoleIsAdmin;
	document.changeUserRole.submit();
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
		<th>Current Role</th>
		<th>Modify User</th>
	</tr>
	<%for(User user: users) { %>
	<%String role = userDB.getRole(user); %>
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
		<td width="10%">
		<%=(role == null ? "user" : role)%>
		</td>
		<td width="10%">
		<% if(role == null || role.equals("user") && user.getId() != currentUser.getId()){ %>
		<a href="javascript:changeRole('<%=user.getUserName()%>', true)">Change to Admin</a>
		<% } else if(user.getId() != currentUser.getId()){%>
		<a href="javascript:changeRole('<%=user.getUserName()%>', false)">Change to User</a>
		<% }%>
		</td>
	</tr>
	<% } %>
</table>
<% } else { %>
	no items found	
<% } %>

<form name="changeUserRole" method="post" action="/AdminController/changeUserRole">
	<input type="hidden" name="userIDUR" id="userIDUR">
	<input type="hidden" name="newRoleIsAdmin" id="newRoleIsAdmin">
</form>

<form name="removeUserForm" method="post" action="/AdminController/deleteUser">
	<input type="hidden" name="userID" id="userID">
</form>

</section>

<!-- end the middle column -->

<jsp:include page="/includes/footer.jsp" />