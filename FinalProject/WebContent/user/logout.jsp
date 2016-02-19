<%@ page import="finalproject.models.*" %>
<%@ page import="finalproject.data.*" %>

<jsp:include page="/includes/header.jsp" />
<jsp:include page="/includes/column_left_home.jsp" />

<!-- start the middle column -->

<section>

	<% 
		User user = userDB.getUser(""); 
		session.setAttribute("currentUser", user);
	%>
	<p>You have been successfully logged out</p>
	
	<a href="/index.jsp">Back</a>

</section>

<!-- end the middle column -->

<jsp:include page="/includes/footer.jsp" />